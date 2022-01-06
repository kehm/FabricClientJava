/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.exception;

/**
 * Class to represent an error while registering/enrolling identities
 */
public class IdentityException extends Exception {

    /**
     * Constructor for IdentityException
     *
     * @param message Exception message
     */
    public IdentityException(String message) {
        super(message);
    }
}
