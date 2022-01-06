/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.controller;

import com.example.fabricclientjava.exception.ChaincodeException;
import com.example.fabricclientjava.model.TransactionRequest;
import com.example.fabricclientjava.service.ChaincodeService;
import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for chaincode operations
 */
@CrossOrigin
@RestController
@RequestMapping(path = "chaincodes")
public class ChaincodeController {

    private final static Logger LOG = Logger.getLogger(ChaincodeController.class.getName());

    private final ChaincodeService chaincodeService;

    @Autowired
    public ChaincodeController(ChaincodeService chaincodeService) {
        this.chaincodeService = chaincodeService;
    }

    /**
     * Invoke chaincode function
     *
     * @param request Transaction request
     * @return Function response
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking chaincode function failed
     */
    @PostMapping(value = "/invoke", consumes = {"application/json"}, produces = {"application/json"})
    public String invoke(@RequestBody TransactionRequest request) throws IOException, ChaincodeException {
        LOG.info("Invoking chaincode function...");
        String response = chaincodeService.invoke(request);
        LOG.info("Successfully invoked chaincode function");
        return response;
    }

    /**
     * Query the blockchain
     *
     * @param request Transaction request
     * @return Query response
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking chaincode function failed
     */
    @PostMapping(value = "/query", consumes = {"application/json"}, produces = {"application/json"})
    public String query(@RequestBody TransactionRequest request) throws IOException, ChaincodeException {
        LOG.info("Executing chaincode query...");
        String response = chaincodeService.query(request);
        LOG.info("Successfully executed chaincode query");
        return response;
    }
}
