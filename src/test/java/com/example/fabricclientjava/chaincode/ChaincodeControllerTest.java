/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.chaincode;

import com.example.fabricclientjava.controller.ChaincodeController;
import com.example.fabricclientjava.exception.ChaincodeException;
import com.example.fabricclientjava.model.TransactionRequest;
import com.example.fabricclientjava.service.ChaincodeService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for ChaincodeController class
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChaincodeControllerTest {

    @Mock
    private ChaincodeService chaincodeService;
    private ChaincodeController chaincodeController;

    @BeforeEach
    void setUp() {
        chaincodeController = new ChaincodeController(chaincodeService);
    }

    /**
     * Invoking chaincode should call method in service class
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking chaincode function failed
     */
    @Test
    void canInvokeChaincode() throws IOException, ChaincodeException {
        TransactionRequest request = new TransactionRequest(
                "admin",
                "mychannel",
                "basic",
                "TransferAsset",
                null,
                new String[]{"asset6", "Christopher"}
        );
        chaincodeController.invoke(request);
        verify(chaincodeService).invoke(request);
    }

    /**
     * Querying chaincode should call method in service class
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking chaincode function failed
     */
    @Test
    void canQueryChaincode() throws IOException, ChaincodeException {
        TransactionRequest request = new TransactionRequest(
                "admin",
                "mychannel",
                "basic",
                "GetAllAssets",
                null,
                new String[]{""}
        );
        chaincodeController.query(request);
        verify(chaincodeService).query(request);
    }
}
