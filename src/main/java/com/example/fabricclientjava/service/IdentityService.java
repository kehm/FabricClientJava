/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.service;

import com.example.fabricclientjava.exception.IdentityException;
import com.example.fabricclientjava.model.IdentityRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for identity operations
 */
@Service
public class IdentityService {

    @Value("${fabric.config.path}")
    private String configPath;
    @Value("${fabric.wallet.path}")
    private String walletPath;
    @Value("${fabric.connection.profile}")
    private String connectionProfile;

    /**
     * Get names of identities in the file system wallet
     *
     * @return File names
     */
    public List<String> getIdentities() {
        File[] files = Paths.get(walletPath).toFile().listFiles();
        ArrayList<String> identities = new ArrayList<>();
        for (File file : files) {
            String[] parts = file.getName().split("\\.");
            if (parts.length > 0) {
                identities.add(parts[0]);
            }
        }
        return identities;
    }

    /**
     * Enroll an identity with the Fabric CA
     *
     * @param request Data from the identity request
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or enrolling with CA fails
     */
    public void enroll(IdentityRequest request) throws IOException, NetworkConfigurationException, IdentityException {
        Path walletDir = Paths.get(walletPath);
        Wallet wallet = Wallets.newFileSystemWallet(walletDir);
        Path cpFile = Paths.get(configPath, connectionProfile);
        NetworkConfig conf = NetworkConfig.fromYamlFile(cpFile.toFile());
        NetworkConfig.CAInfo caInfo = conf.getOrganizationInfo(request.getOrganization()).getCertificateAuthorities().get(0);
        Identity identity = wallet.get(request.getName());
        if (identity == null) {
            try {
                HFCAClient caClient = HFCAClient.createNewInstance(caInfo);
                CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
                caClient.setCryptoSuite(cryptoSuite);
                Enrollment enrollment = caClient.enroll(request.getName(), request.getSecret());
                Identity x509Identity = Identities.newX509Identity(conf.getOrganizationInfo(request.getOrganization()).getMspId(), enrollment);
                wallet.put(request.getName(), x509Identity);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IdentityException("Could not enroll identity");
            }
        }
    }

    /**
     * Register an identity with the Fabric CA
     *
     * @param request Data from the identity request
     * @throws IOException If cannot read wallet or connection profile
     * @throws NetworkConfigurationException If invalid connection profile
     * @throws IdentityException If initializing or registering with CA fails
     */
    public void register(IdentityRequest request) throws IOException, NetworkConfigurationException, IdentityException {
        Path walletDir = Paths.get(walletPath);
        Wallet wallet = Wallets.newFileSystemWallet(walletDir);
        Path cpFile = Paths.get(configPath, connectionProfile);
        NetworkConfig conf = NetworkConfig.fromYamlFile(cpFile.toFile());
        NetworkConfig.CAInfo caInfo = conf.getOrganizationInfo(request.getOrganization()).getCertificateAuthorities().get(0);
        Identity identity = wallet.get(request.getName());
        X509Identity admin = (X509Identity) wallet.get(request.getRegistrar());
        if (identity == null) {
            if (admin == null) {
                throw new IdentityException("Admin identity does not exist in wallet");
            }
            try {
                HFCAClient caClient = HFCAClient.createNewInstance(caInfo);
                CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
                caClient.setCryptoSuite(cryptoSuite);
                User adminUser = new User() {
                    @Override
                    public String getName() {
                        return request.getRegistrar();
                    }

                    @Override
                    public Set<String> getRoles() {
                        return null;
                    }

                    @Override
                    public String getAccount() {
                        return null;
                    }

                    @Override
                    public String getAffiliation() {
                        return request.getAffiliation();
                    }

                    @Override
                    public Enrollment getEnrollment() {
                        return new Enrollment() {

                            @Override
                            public PrivateKey getKey() {
                                return admin.getPrivateKey();
                            }

                            @Override
                            public String getCert() {
                                return Identities.toPemString(admin.getCertificate());
                            }
                        };
                    }

                    @Override
                    public String getMspId() {
                        return conf.getOrganizationInfo(request.getOrganization()).getMspId();
                    }
                };
                RegistrationRequest regRequest = new RegistrationRequest(request.getName(), request.getAffiliation());
                regRequest.setSecret(request.getSecret());
                caClient.register(regRequest, adminUser);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IdentityException("Could not register identity");
            }
        }
    }

    /**
     * Clear identity wallet
     */
    public void clearWallet() {
        File[] files = Paths.get(walletPath).toFile().listFiles();
        for (File file : files) {
            file.delete();
        }
    }
}
