/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.exception;

/**
 * Class to represent an error in chaincode execution
 */
public class ChaincodeException extends Exception {

    /**
     * Constructor for ChaincodeException
     *
     * @param message Exception message
     */
    public ChaincodeException(String message) {
        super(message);
    }
}
