package com.kentoes.jwtTechie.services;

import com.kentoes.jwtTechie.entities.user.User;
import com.kentoes.jwtTechie.entities.user.UserDetailsImp;
import com.kentoes.jwtTechie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("username not found!"));
        return UserDetailsImp.build(user);
    }
}
