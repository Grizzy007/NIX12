package ua.nure.tkp.trainingday.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.tkp.trainingday.entity.Program;
import ua.nure.tkp.trainingday.entity.dto.ProgramDto;
import ua.nure.tkp.trainingday.repository.ProgramRepo;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    private ProgramRepo programRepo;

    @GetMapping(value = {"/", "/home"})
    public String main(Model model) {
        return "index";
    }

    @GetMapping(value = "/catalog")
    public String catalog(Model model) {
        Iterable<Program> all = programRepo.findProgramsByStatusName("ACCEPTED");
        model.addAttribute("programs", all);
        return "programs";
    }

    @GetMapping(value = "/suggest_program")
    @PreAuthorize("hasAuthority('read')")
    public String suggestProgram() {
        return "suggestProgram";
    }

    @PostMapping(value = "/suggest_program")
    @PreAuthorize("hasAuthority('read')")
    public String createProgram(@RequestParam String name, @RequestParam String description,
                                @RequestParam Integer duration, @RequestParam String muscleGroup, Model model) {
        ProgramDto program = new ProgramDto(name, duration, muscleGroup, description);
        Program pro = new Program(program.getName(), program.getDuration(), program.getGroup(), null, program.getDescription());
        programRepo.save(pro);
        return "redirect:/home";
    }

    @GetMapping(value = "/catalog/{id}")
    @PreAuthorize("hasAuthority('read')")
    public String programInfo(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Program> prog = programRepo.findById(id);
        List<Program> result;
        result = prog.stream().toList();
        prog.ifPresent(program -> model.addAttribute("name", program.getName()));
        model.addAttribute("program", result);
        return "details";
    }
}
