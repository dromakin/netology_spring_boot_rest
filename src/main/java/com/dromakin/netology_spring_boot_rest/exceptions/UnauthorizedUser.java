/*
 * File:     UnauthorizedUser
 * Package:  com.dromakin.netology_spring_boot_rest.exceptions
 * Project:  netology_spring_boot_rest
 *
 * Created by dromakin as 08.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.08
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_spring_boot_rest.exceptions;

public class UnauthorizedUser extends RuntimeException {
    public UnauthorizedUser(String msg) {
        super(msg);
    }
}
