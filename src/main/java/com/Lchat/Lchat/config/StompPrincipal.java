// src/main/java/com/Lchat/Lchat/config/StompPrincipal.java
package com.Lchat.Lchat.config;

import java.security.Principal;

public class StompPrincipal implements Principal {
    private final String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}