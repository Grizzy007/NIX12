package ua.nure.tkp.trainingday.service;

import ua.nure.tkp.trainingday.entity.User;

public interface UserService {
    public void register(User user) throws Exception;

    public boolean checkIfUserExist(String login);
}
