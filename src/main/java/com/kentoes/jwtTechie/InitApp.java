package com.kentoes.jwtTechie;

import com.kentoes.jwtTechie.entities.enums.ERole;
import com.kentoes.jwtTechie.entities.role.Role;
import com.kentoes.jwtTechie.entities.user.User;
import com.kentoes.jwtTechie.repositories.RoleRepository;
import com.kentoes.jwtTechie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InitApp implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = Stream.of(
                new Role(ERole.ADMIN),
                new Role(ERole.CATER),
                new Role(ERole.CHECKER)
        ).collect(Collectors.toList());
//        log.info("roles: {}", roles);
        roleRepository.saveAll(roles);

        User userAdmin = new User(
                "kentoes",
                "kent-os",
                passwordEncoder.encode("kentoes"));
        userAdmin.addRole(roleRepository.findByRoleName(ERole.ADMIN).get());
        User userCater = new User(
                "cater",
                "kent-cater",
                passwordEncoder.encode("cater"));
        userCater.addRole(roleRepository.findByRoleName(ERole.CATER).get());
        User userChecker = new User(
                "checker",
                "kent-checker",
                passwordEncoder.encode("checker"));
        userChecker.addRole(roleRepository.findByRoleName(ERole.CHECKER).get());

        List<User> users = Stream.of(userAdmin, userCater, userChecker).collect(Collectors.toList());
        userRepository.saveAll(users);
    }
}
