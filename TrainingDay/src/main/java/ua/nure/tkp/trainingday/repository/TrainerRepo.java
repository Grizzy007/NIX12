package ua.nure.tkp.trainingday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.nure.tkp.trainingday.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer,Integer> {
    @Query(value = "select t.name,count(p.trainer_id) as aproved_programs from trainers t " +
            "join programs p on t.id=p.trainer_id group by trainer_id order by count(p.trainer_id) desc",
            nativeQuery = true)
    Iterable<Trainer> getTrainerByCountOfPrograms();
}
