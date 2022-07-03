package ua.nure.tkp.trainingday.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nure.tkp.trainingday.entity.User;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final String nickname;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUser(String nickname, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.nickname = nickname;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(),
                user.getNickname() != null,
                user.getNickname() != null, user.getNickname() != null,
                user.getNickname() != null, user.getRole().getAuthorities()
        );
    }
}
