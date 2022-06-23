package ua.nure.tkp.trainingday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.tkp.trainingday.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer,Integer> {
}
