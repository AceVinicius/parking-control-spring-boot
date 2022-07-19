package com.api.parkingcontrol.config.security;

import com.api.parkingcontrol.model.UserModel;
import com.api.parkingcontrol.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new User(
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.isEnabled(),
                userModel.isAccountNonExpired(),
                userModel.isCredentialsNonExpired(),
                userModel.isAccountNonLocked(),
                userModel.getAuthorities()
        );
    }
}
