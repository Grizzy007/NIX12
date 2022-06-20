package ua.nure.tkp.trainingday.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.tkp.trainingday.entity.Program;
import ua.nure.tkp.trainingday.repository.ProgramRepo;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    ProgramRepo programRepo;

    @GetMapping(value = {"/","/home"})
    public String main(Model model){
        return "index";
    }

    @GetMapping(value="/catalog")
    public String catalog(Model model)
    {
        Iterable<Program> all = programRepo.findAll();
        model.addAttribute("programs",all);
        return "programs";
    }

    @GetMapping(value = "/add_program")
    @PreAuthorize("hasAuthority('read')")
    public String addProgram(){
        return "addProgram";
    }

    @PostMapping(value = "/add_program")
    @PreAuthorize("hasAuthority('read')")
    public String createProgram(@RequestParam String name, @RequestParam String description,
                                @RequestParam Integer duration, @RequestParam String muscleGroup, Model model){
        Program program = new Program(name,duration, muscleGroup,description);
        programRepo.save(program);
        return "redirect:/home";
    }

    @GetMapping(value = "/catalog/{id}")
    @PreAuthorize("hasAuthority('read')")
    public String programInfo(@PathVariable(value = "id") Integer id, Model model){
        Optional<Program> prog = programRepo.findById(id);
        List<Program> result;
        result = prog.stream().toList();
        model.addAttribute("name", prog.get().getName());
        model.addAttribute("program",result);
        return "details";
    }

    @GetMapping(value = "/catalog/{id}/edit")
    @PreAuthorize("hasAuthority('write')")
    public String programEdit(@PathVariable(value = "id") Integer id, Model model){
        Optional<Program> prog = programRepo.findById(id);
        List<Program> result;
        result = prog.stream().toList();
        model.addAttribute("name", prog.get().getName());
        model.addAttribute("program",result);
        return "edit";
    }

    @PostMapping(value = "/catalog/{id}/edit")
    @PreAuthorize("hasAuthority('write')")
    public String editProgram(@PathVariable(value = "id") Integer id, @RequestParam String name,
                                @RequestParam Integer duration, @RequestParam String description, Model model){
        Program program = programRepo.findById(id).orElseThrow();
        program.setName(name);
        program.setDuration(duration);
        program.setDescription(description);
        programRepo.save(program);
        return "redirect:/home";
    }

    @PostMapping(value = "/catalog/{id}/remove")
    @PreAuthorize("hasAuthority('write')")
    public String removeProgram(@PathVariable(value = "id") Integer id, Model model){
        Program program = programRepo.findById(id).orElseThrow();
        programRepo.delete(program);
        return "redirect:/catalog";
    }
}
