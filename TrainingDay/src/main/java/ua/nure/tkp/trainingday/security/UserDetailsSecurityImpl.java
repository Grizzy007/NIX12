package ua.nure.tkp.trainingday.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.tkp.trainingday.entity.User;
import ua.nure.tkp.trainingday.repository.UserRepo;

@Service("userDetailsServiceImpl")
public class UserDetailsSecurityImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserDetailsSecurityImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exist")
        );
        return SecurityUser.fromUser(user);
    }
}
