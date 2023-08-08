/*
 * File:     UserRepository
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

import java.util.List;

public interface UserRepository {
    List<Authorities> getUserAuthorities(String user, String password);
}
