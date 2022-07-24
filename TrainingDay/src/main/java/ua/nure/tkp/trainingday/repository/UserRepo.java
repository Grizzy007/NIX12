package ua.nure.tkp.trainingday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.tkp.trainingday.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
