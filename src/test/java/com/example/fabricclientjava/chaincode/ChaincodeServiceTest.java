/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.chaincode;

import com.example.fabricclientjava.exception.ChaincodeException;
import com.example.fabricclientjava.model.TransactionRequest;
import com.example.fabricclientjava.service.ChaincodeService;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for ChaincodeService class
 */
@SpringBootTest
public class ChaincodeServiceTest {

    @Autowired
    private ChaincodeService chaincodeService;

    /**
     * Invoking chaincode should return a string
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking query failed
     */
    @Test
    void invokeChaincodeShouldReturnString() throws IOException, ChaincodeException {
        TransactionRequest request = new TransactionRequest(
                "admin",
                "mychannel",
                "basic",
                "TransferAsset",
                null,
                new String[]{"asset6", "Christopher"}
        );
        String response = chaincodeService.invoke(request);
        assertThat(response).isNotBlank();
    }

    /**
     * Querying chaincode should not return a string
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking query failed
     */
    @Test
    void queryChaincodeShouldReturnString() throws IOException, ChaincodeException {
        TransactionRequest request = new TransactionRequest(
                "admin",
                "mychannel",
                "basic",
                "GetAllAssets",
                null,
                new String[]{""}
        );
        String response = chaincodeService.query(request);
        assertThat(response).isNotBlank();
    }
}
