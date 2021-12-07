package com.example.misael.serviceimpl;

public final class AuthenticationConstants {

    /**
     * Url authenticated
     */
    public static final String URL_PRIVATE_AUTHETICATION = "/voltux";

    /**
     * config Url access
     */
    public static final String URL_CONFIG_PRIVATE_AUTHETICATION = "/voltux/api/v1/**";

    private AuthenticationConstants() {
        throw new IllegalStateException("Constants class");
    }
}