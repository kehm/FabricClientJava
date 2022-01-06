/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.model;

import java.util.Map;

/**
 * Class to represent a Fabric chaincode invoke/query
 */
public class TransactionRequest {

    private String identity;
    private String channel;
    private String chaincode;
    private String func;
    private Map<String, byte[]> transientData;
    private String[] args;

    /**
     * Constructor for TransactionRequest
     */
    public TransactionRequest() {
    }

    /**
     * Constructor for TransactionRequest
     *
     * @param identity Identity name
     * @param channel Channel name
     * @param chaincode Chaincode name
     * @param func Function name
     * @param transientData Transient data
     * @param args Function arguments
     */
    public TransactionRequest(String identity, String channel, String chaincode, String func, Map<String, byte[]> transientData, String[] args) {
        this.identity = identity;
        this.channel = channel;
        this.chaincode = chaincode;
        this.func = func;
        this.transientData = transientData;
        this.args = args;
    }

    /**
     * Get identity name
     *
     * @return Identity name
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Set identity name
     *
     * @param identity Identity name
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }

    /**
     * Get channel name
     *
     * @return Channel name
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Set channel name
     *
     * @param channel Channel name
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Get chaincode name
     *
     * @return Chaincode name
     */
    public String getChaincode() {
        return chaincode;
    }

    /**
     * Set chaincode name
     *
     * @param chaincode Chaincode name
     */
    public void setChaincode(String chaincode) {
        this.chaincode = chaincode;
    }

    /**
     * Get function name
     *
     * @return Function name
     */
    public String getFunc() {
        return func;
    }

    /**
     * Set function name
     *
     * @param func Function name
     */
    public void setFunc(String func) {
        this.func = func;
    }

    /**
     * Get transient data
     *
     * @return Transient data
     */
    public Map<String, byte[]> getTransientData() {
        return transientData;
    }

    /**
     * Set transient data
     *
     * @param transientData Transient data
     */
    public void setTransientData(Map<String, byte[]> transientData) {
        this.transientData = transientData;
    }

    /**
     * Get function arguments
     *
     * @return Function arguments
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Set function arguments
     *
     * @param args Function arguments
     */
    public void setArgs(String[] args) {
        this.args = args;
    }
}
