package ua.nure.tkp.trainingday.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.tkp.trainingday.entity.Program;
import ua.nure.tkp.trainingday.repository.ProgramRepo;
import ua.nure.tkp.trainingday.repository.TrainerRepo;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private ProgramRepo programRepo;

    @Autowired
    private TrainerRepo trainerRepo;

    @GetMapping(value = "/catalog/{id}/edit")
    @PreAuthorize("hasAuthority('write')")
    public String programEdit(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Program> prog = programRepo.findById(id);
        List<Program> result;
        result = prog.stream().toList();
        prog.ifPresent(p -> model.addAttribute("name", p.getName()));
        model.addAttribute("program", result);
        return "edit";
    }

    @PostMapping(value = "/catalog/{id}/edit")
    @PreAuthorize("hasAuthority('write')")
    public String editProgram(@PathVariable(value = "id") Integer id, @RequestParam String name,
                              @RequestParam Integer duration, @RequestParam String description, Model model) {
        Program program = programRepo.findById(id).orElseThrow();
        program.setName(name);
        program.setDuration(duration);
        program.setDescription(description);
        programRepo.save(program);
        return "redirect:/home";
    }

    @PostMapping(value = "/catalog/{id}/remove")
    @PreAuthorize("hasAuthority('write')")
    public String removeProgram(@PathVariable(value = "id") Integer id, Model model) {
        Program program = programRepo.findById(id).orElseThrow();
        programRepo.delete(program);
        return "redirect:/catalog";
    }

    @GetMapping(value = "/verify")
    @PreAuthorize("hasAuthority('write')")
    public String programToVerify(Model model) {
        Iterable<Program> all = programRepo.findProgramsByStatusName("NEW");
        model.addAttribute("programs", all);
        return "verify";
    }

    @GetMapping(value = "/add_program")
    @PreAuthorize("hasAuthority('write')")
    public String addProgramPage(Model model) {
        model.addAttribute("trainers", trainerRepo.findAll());
        model.addAttribute("program", new Program());
        return "addProgram";
    }

    @PostMapping("/add_program")
    @PreAuthorize("hasAuthority('write')")
    public String processRegister(Program program) {
        Program entity = new Program(program.getName(), program.getDuration(), program.getGroup(), program.getTrainer(), program.getDescription());
        entity.setStatus("ACCEPTED");
        programRepo.save(entity);
        return "redirect:/catalog";
    }

    @GetMapping(value = "/catalog/{id}/accept")
    @PreAuthorize("hasAuthority('write')")
    public String programAccept(@PathVariable(value = "id") Integer id, Model model) {
        Program prog = programRepo.findById(id).orElseThrow();
        model.addAttribute("trainers", trainerRepo.findAll());
        model.addAttribute("program", prog);
        return "accept";
    }

    @PostMapping(value = "/catalog/{id}/accept")
    @PreAuthorize("hasAuthority('write')")
    public String acceptProgram(@PathVariable(value = "id") Integer id, Program program) {
        Program prog = programRepo.findById(id).orElseThrow();
        prog.setTrainer(program.getTrainer());
        prog.setStatus("ACCEPTED");
        programRepo.save(prog);
        return "redirect:/verify";
    }
}
