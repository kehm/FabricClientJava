/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.controller;

import com.example.fabricclientjava.exception.ChaincodeException;
import com.example.fabricclientjava.exception.IdentityException;
import java.io.IOException;
import java.util.logging.Logger;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global controller exception handler class
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final static Logger LOG = Logger.getLogger(ChaincodeController.class.getName());

    @ExceptionHandler(value = ChaincodeException.class)
    public ResponseEntity<Object> handleChaincodeException(ChaincodeException exception) {
        LOG.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IdentityException.class)
    public ResponseEntity<Object> handleIdentityException(IdentityException exception) {
        LOG.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NetworkConfigurationException.class)
    public ResponseEntity<Object> handleNetworkConfigurationException(NetworkConfigurationException exception) {
        LOG.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<Object> handleIOException(IOException exception) {
        LOG.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
