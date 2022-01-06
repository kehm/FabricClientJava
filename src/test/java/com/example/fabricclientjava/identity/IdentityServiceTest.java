/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.identity;

import com.example.fabricclientjava.exception.IdentityException;
import com.example.fabricclientjava.model.IdentityRequest;
import com.example.fabricclientjava.model.IdentityRole;
import com.example.fabricclientjava.service.IdentityService;
import java.io.IOException;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for IdentityService class
 */
@SpringBootTest
public class IdentityServiceTest {

    @Autowired
    private IdentityService identityService;

    /**
     * Registering an identity should not throw an error
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or enrolling with CA fails
     */
    @Test
    void registerIdentityShouldNotError() throws IOException, NetworkConfigurationException, IdentityException {
        IdentityRequest request = new IdentityRequest(
                "test",
                "testpw",
                IdentityRole.CLIENT,
                "Org1",
                "org1.department1",
                "admin"
        );
        identityService.register(request);
    }

    /**
     * Enrolling an identity should not throw an error
     *
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or enrolling with CA fails
     */
    @Test
    void enrollIdentityShouldNotError() throws IOException, NetworkConfigurationException, IdentityException {
        IdentityRequest request = new IdentityRequest(
                "test",
                "testpw",
                "Org1"
        );
        identityService.enroll(request);
    }
}
