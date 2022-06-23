package ua.nure.tkp.trainingday.repository;

import org.springframework.data.repository.CrudRepository;
import ua.nure.tkp.trainingday.entity.Program;
import ua.nure.tkp.trainingday.entity.Status;

public interface ProgramRepo extends CrudRepository<Program, Integer> {
    public Iterable<Program> findProgramsByStatusName(String s);
}
