/*
 * File:     AuthorizationService
 * Package:  com.dromakin.netology_spring_boot_rest.services
 * Project:  netology_spring_boot_rest
 *
 * Created by dromakin as 08.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.08
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_spring_boot_rest.services;

import com.dromakin.netology_spring_boot_rest.entities.Authorities;
import com.dromakin.netology_spring_boot_rest.entities.User;
import com.dromakin.netology_spring_boot_rest.exceptions.InvalidCredentials;
import com.dromakin.netology_spring_boot_rest.exceptions.UnauthorizedUser;
import com.dromakin.netology_spring_boot_rest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Authorities> getAuthorities(String user, String password) {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user, password);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    public List<Authorities> getAuthorities(User user) {
        return getAuthorities(user.getLogin(), user.getPassword());
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}
