/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.controller;

import com.example.fabricclientjava.exception.IdentityException;
import com.example.fabricclientjava.model.IdentityRequest;
import com.example.fabricclientjava.service.IdentityService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for identity operations
 */
@CrossOrigin
@RestController
@RequestMapping(path = "identities")
public class IdentityController {

    private final static Logger LOG = Logger.getLogger(ChaincodeController.class.getName());

    private final IdentityService identityService;

    @Autowired
    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    /**
     * Get names of identities in the file system wallet
     *
     * @return Identity names
     */
    @GetMapping
    public List<String> getIdentities() {
        LOG.info("Getting wallet identities...");
        List<String> identities = identityService.getIdentities();
        LOG.info("Successfully retrieved list of identities in the wallet");
        return identities;
    }

    /**
     * Enroll an identity with the Fabric CA
     *
     * @param request Identity request
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or registering with CA fails
     */
    @PostMapping(value = "/enroll", consumes = {"application/json"})
    public void enroll(@RequestBody IdentityRequest request) throws IOException, NetworkConfigurationException, IdentityException {
        LOG.info("Enrolling identity...");
        identityService.enroll(request);
        LOG.log(Level.INFO, "Successfully enrolled {0} for {1}", new Object[]{request.getName(), request.getOrganization()});
    }

    /**
     * Register an identity with the Fabric CA
     *
     * @param request Identity request
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or registering with CA fails
     */
    @PostMapping(value = "/register", consumes = {"application/json"})
    public void register(@RequestBody IdentityRequest request) throws IOException, NetworkConfigurationException, IdentityException {
        LOG.info("Registering identity...");
        identityService.register(request);
        LOG.log(Level.INFO, "Successfully registered {0} for {1}", new Object[]{request.getName(), request.getOrganization()});
    }

    /**
     * Clear identity wallet
     */
    @PostMapping(value = "/clear")
    public void clearWallet() {
        LOG.info("Clearing identity wallet...");
        identityService.clearWallet();
        LOG.info("Successfully cleared identity wallet");
    }
}
