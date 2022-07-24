package ua.nure.tkp.trainingday.service;

import ua.nure.tkp.trainingday.entity.User;

public interface UserService {
    void register(User user) throws Exception;

    boolean checkIfUserExist(String login);
}
