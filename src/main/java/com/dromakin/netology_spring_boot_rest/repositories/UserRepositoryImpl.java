/*
 * File:     UserRepositoryImpl
 * Package:  com.dromakin.netology_spring_boot_rest.repositories
 * Project:  netology_spring_boot_rest
 *
 * Created by dromakin as 08.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.08
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_spring_boot_rest.repositories;

import com.dromakin.netology_spring_boot_rest.entities.Authorities;
import com.dromakin.netology_spring_boot_rest.entities.User;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    protected void generateFakeData() {
        Random random = new Random();
        Faker faker = new Faker();

        for (int i = 0; i < 50; i++) {
            String name = faker.name().username();
            String password = faker.internet().password();
            int randomNum = random.nextInt(4 - 1) + 1;
            List<Authorities> userAuthorities = new ArrayList<>();

            if (randomNum == 1) {
                userAuthorities.add(Authorities.READ);
            } else if (randomNum == 2) {
                userAuthorities.add(Authorities.READ);
                userAuthorities.add(Authorities.WRITE);
            } else {
                userAuthorities.add(Authorities.READ);
                userAuthorities.add(Authorities.WRITE);
                userAuthorities.add(Authorities.DELETE);
            }

            User user = User.builder()
                    .login(name)
                    .password(password)
                    .userAuthorities(userAuthorities)
                    .build();

            System.out.printf("Name:password:userAuthorities %s:%s:%s\n", name, password, userAuthorities.size());
            users.put(name, user);
        }
    }

    public UserRepositoryImpl() {
        generateFakeData();
    }

    @Override
    public List<Authorities> getUserAuthorities(String user, String password) {
        User result = users.get(user);
        if ((result != null) && (password.equals(result.getPassword()))) {
            return result.getUserAuthorities();
        } else {
            return new ArrayList<>();
        }
    }
}
