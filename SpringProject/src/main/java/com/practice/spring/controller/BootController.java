package com.practice.spring.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Optional;

import com.practice.spring.repositories.BootRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.spring.entities.Boot;
import com.practice.spring.enums.BootType;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {

    final private BootRepository bootRepository;

    public BootController(final BootRepository bootRepository){
        this.bootRepository = bootRepository;
    }

    @GetMapping("/")
    public Iterable<Boot> getAllBoots() {
        return bootRepository.findAll();
    }

    @GetMapping("/types")
    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }

    @PostMapping("/")
    public Boot addBoot(@RequestBody Boot boot) {
        Boot bootToAdd = boot;
        bootRepository.save(bootToAdd);
        return bootToAdd;
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = bootRepository.findById(id);
        if(!bootOptional.isPresent()){
            return null;
        }
        Boot boot = bootOptional.get();
        bootRepository.delete(boot);
        return boot;
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = bootRepository.findById(id);
        if(!bootOptional.isPresent()){
            return null;
        }
        Boot boot = bootOptional.get();
        Integer x = boot.getQuantity();
        x++;
        boot.setQuantity(x);
        bootRepository.save(boot);
        return boot;
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = bootRepository.findById(id);
        if(!bootOptional.isPresent()){
            return null;
        }
        Boot boot = bootOptional.get();
        Integer x = boot.getQuantity();
        x--;
        boot.setQuantity(x);
        bootRepository.save(boot);
        return boot;
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(@RequestParam(required = false) String material,
                                  @RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
                                  @RequestParam(required = false, name = "quantity") Integer minQuantity) {
        if (Objects.nonNull(material)) {
            if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                return bootRepository.findBySizeAndMaterialAndTypeAndQuantityGreaterThan(size, material, type, minQuantity);
            } else if (Objects.nonNull(type) && Objects.nonNull(size)) {
                return bootRepository.findBySizeAndMaterialAndType(size, material, type);
            } else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
                return bootRepository.findByMaterialAndTypeAndQuantityGreaterThan(material, type, minQuantity);
            } else if (Objects.nonNull(type)) {
                return bootRepository.findByMaterialAndType(material, type);
            } else {
                bootRepository.findByMaterial(material);
            }
        } else if (Objects.nonNull(type)) {
            if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                return bootRepository.findBySizeAndTypeAndQuantityGreaterThan(size, type, minQuantity);
            } else if (Objects.nonNull(size)) {
                return bootRepository.findBySizeAndType(size, type);
            } else if (Objects.nonNull(minQuantity)) {
                return bootRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
            } else {
                return bootRepository.findByType(type);
            }
        } else if (Objects.nonNull(size)) {
            if (Objects.nonNull(minQuantity)) {
                return bootRepository.findBySizeAndQuantityGreaterThan(size, minQuantity);
            } else {
                return bootRepository.findBySize(size);
            }
        } else if (Objects.nonNull(minQuantity)) {
            return bootRepository.findByQuantityGreaterThan(minQuantity);
        }
            return new ArrayList<>();
    }

}


