/*
 * File:     AuthorizationController
 * Package:  com.dromakin.netology_spring_boot_rest.controllers
 * Project:  netology_spring_boot_rest
 *
 * Created by dromakin as 08.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.08
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_spring_boot_rest.controllers;

import com.dromakin.netology_spring_boot_rest.entities.Authorities;
import com.dromakin.netology_spring_boot_rest.entities.User;
import com.dromakin.netology_spring_boot_rest.services.AuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class AuthorizationController {
    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @GetMapping("/old/authorize")
    public List<Authorities> getAuthoritiesByUserAndPassword(
            @RequestParam("user") String user,
            @RequestParam("password") String password
    ) {
        return service.getAuthorities(user, password);
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@Valid User user) {
        return service.getAuthorities(user);
    }
}
