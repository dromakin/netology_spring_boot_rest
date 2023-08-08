/*
 * File:     User
 * Package:  com.dromakin.netology_spring_boot_rest.entities
 * Project:  netology_spring_boot_rest
 *
 * Created by dromakin as 08.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.08
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_spring_boot_rest.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class User {
    @NotBlank
    @Size(min = 2, max = 100)
    String login;
    @NotBlank
    @Size(min = 6, max = 100)
    String password;
    List<Authorities> userAuthorities;
}
