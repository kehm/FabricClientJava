/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.identity;

import com.example.fabricclientjava.controller.IdentityController;
import com.example.fabricclientjava.exception.IdentityException;
import com.example.fabricclientjava.model.IdentityRequest;
import com.example.fabricclientjava.model.IdentityRole;
import com.example.fabricclientjava.service.IdentityService;
import java.io.IOException;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for IdentityController class
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class IdentityControllerTest {

    @Mock
    private IdentityService identityService;
    private IdentityController identitiyController;

    @BeforeEach
    void setUp() {
        identitiyController = new IdentityController(identityService);
    }

    /**
     * Registering an identity should call method in service class
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or registering with CA fails
     */
    @Test
    void canRegisterIdentity() throws IOException, NetworkConfigurationException, IdentityException {
        IdentityRequest request = new IdentityRequest(
                "test",
                "testpw",
                IdentityRole.CLIENT,
                "Org1",
                "org1.department1",
                "admin"
        );
        identitiyController.register(request);
        verify(identityService).register(request);
    }

    /**
     * Enrolling an identity should call method in service class
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or registering with CA fails
     */
    @Test
    void canEnrollIdentity() throws IOException, NetworkConfigurationException, IdentityException {
        IdentityRequest request = new IdentityRequest(
                "test",
                "testpw",
                "Org1"
        );
        identitiyController.enroll(request);
        verify(identityService).enroll(request);
    }
}
