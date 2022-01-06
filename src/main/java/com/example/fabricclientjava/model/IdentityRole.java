/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum class for identity roles
 */
public enum IdentityRole {

    CLIENT("client"),
    PEER("peer"),
    ORDERER("orderer"),
    ADMIN("admin");

    private final String identityRole;

    IdentityRole(String identityRole) {
        this.identityRole = identityRole;
    }

    /**
     * Get enum value from string
     *
     * @param text Role string
     * @return Enum value
     */
    @JsonCreator
    public static IdentityRole fromText(String text) {
        for (IdentityRole identityRole : IdentityRole.values()) {
            if (identityRole.toString().equals(text)) {
                return identityRole;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return this.identityRole;
    }
}
