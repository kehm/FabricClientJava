/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.service;

import com.example.fabricclientjava.exception.ChaincodeException;
import com.example.fabricclientjava.model.TransactionRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for chaincode operations
 */
@Service
public class ChaincodeService {

    @Value("${fabric.config.path}")
    private String configPath;
    @Value("${fabric.wallet.path}")
    private String walletPath;
    @Value("${fabric.connection.profile}")
    private String connectionProfile;

    /**
     * Invoke a blockchain transaction
     *
     * @param request Data from the transaction request
     * @return Invoke response
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking chaincode function failed
     */
    public String invoke(TransactionRequest request) throws IOException, ChaincodeException {
        Path walletDir = Paths.get(walletPath);
        Wallet wallet = Wallets.newFileSystemWallet(walletDir);
        Path cpFile = Paths.get(configPath, connectionProfile);
        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, request.getIdentity()).networkConfig(cpFile);
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(request.getChannel());
            Contract contract = network.getContract(request.getChaincode());
            byte[] result;
            if (request.getTransientData() != null) {
                result = contract.createTransaction(request.getFunc()).setTransient(request.getTransientData()).submit(request.getArgs());
            } else {
                result = contract.submitTransaction(request.getFunc(), request.getArgs());
            }
            return new String(result, StandardCharsets.UTF_8);
        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
            throw new ChaincodeException("Could not invoke chaincode function");
        }
    }

    /**
     * Query the blockchain
     *
     * @param request Data from the transaction request
     * @return Query result
     * @throws IOException If cannot read wallet or connection profile
     * @throws ChaincodeException If invoking query failed
     */
    public String query(TransactionRequest request) throws IOException, ChaincodeException {
        Path walletDir = Paths.get(walletPath);
        Wallet wallet = Wallets.newFileSystemWallet(walletDir);
        Path cpFile = Paths.get(configPath, connectionProfile);
        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, request.getIdentity()).networkConfig(cpFile);
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(request.getChannel());
            Contract contract = network.getContract(request.getChaincode());
            byte[] result = contract.evaluateTransaction(request.getFunc(), request.getArgs());
            return new String(result, StandardCharsets.UTF_8);
        } catch (ContractException e) {
            e.printStackTrace();
            throw new ChaincodeException("Could not query the blockchain");
        }
    }
}
