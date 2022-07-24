package ua.nure.tkp.trainingday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.tkp.trainingday.entity.User;
import ua.nure.tkp.trainingday.repository.UserRepo;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void register(User user) throws IllegalArgumentException {
        if (checkIfUserExist(user.getLogin())) {
            throw new IllegalArgumentException("This login already exist! Choose another!");
        }
        encodePassword(user);
        userRepo.save(user);
    }

    @Override
    public boolean checkIfUserExist(String login) {
        return userRepo.findByLogin(login).isPresent();
    }

    private void encodePassword(User userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    }
}
