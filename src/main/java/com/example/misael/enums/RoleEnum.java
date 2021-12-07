package com.example.misael.enums;

import java.util.Optional;

public enum RoleEnum {
    EMPLOYE, FACILITY, ADMIN, MANAGER, USER, EMPTY;

    public static RoleEnum roleFromShort(Short input) {
        Optional<Short> optInput = Optional.ofNullable(input);

        if (!optInput.isPresent()) {
            return RoleEnum.EMPTY;
        }

        RoleEnum ouput = RoleEnum.EMPTY;

        switch (input) {

            case 1:
                ouput = RoleEnum.EMPLOYE;
                break;

            case 2:
                ouput = RoleEnum.FACILITY;
                break;

            case 3:
                ouput = RoleEnum.ADMIN;
                break;

            case 4:
                ouput = RoleEnum.USER;
                break;

            case 5:
                ouput = RoleEnum.MANAGER;
                break;

            default:
                ouput = RoleEnum.EMPTY;
                break;
        }

        return ouput;
    }
}