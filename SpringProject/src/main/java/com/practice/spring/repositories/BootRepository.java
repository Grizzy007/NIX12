package com.practice.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.practice.spring.entities.Boot;
import com.practice.spring.enums.BootType;

public interface BootRepository extends CrudRepository<Boot, Integer> {
    public List<Boot> findBySize(Float size);

    public List<Boot> findBySizeAndMaterialAndTypeAndQuantityGreaterThan(Float size, String material, BootType type, Integer quantity);

    public List<Boot> findBySizeAndTypeAndQuantityGreaterThan(Float size, BootType type, Integer quantity);

    public List<Boot> findByTypeAndQuantityGreaterThan(BootType type, Integer quantity);

    public List<Boot> findBySizeAndType(Float size, BootType type);

    public List<Boot> findBySizeAndQuantityGreaterThan(Float size, Integer quantity);

    public List<Boot> findBySizeAndMaterialAndType(Float size, String material, BootType type);

    public List<Boot> findByMaterialAndType(String material, BootType type);

    public List<Boot> findByMaterialAndTypeAndQuantityGreaterThan(String material, BootType type, Integer quantity);

    public List<Boot> findByMaterial(String material);

    public List<Boot> findByType(BootType type);

    public List<Boot> findByQuantityGreaterThan(Integer quantity);
}
