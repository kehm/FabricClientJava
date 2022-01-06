/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.example.fabricclientjava.model;

/**
 * Class to represent a request to enroll/register Fabric identity
 */
public class IdentityRequest {

    private String name;
    private String secret;
    private IdentityRole role;
    private String organization;
    private String affiliation;
    private String registrar;

    /**
     * Constructor for IdentityRequest
     */
    public IdentityRequest() {
    }

    /**
     * Constructor for IdentityRequest
     *
     * @param name Identity name/enrollment ID
     * @param secret Enrollment secret
     * @param organization Organization
     */
    public IdentityRequest(String name, String secret, String organization) {
        this.name = name;
        this.secret = secret;
        this.organization = organization;
    }

    /**
     * Constructor for IdentityRequest
     *
     * @param name Identity name/enrollment ID
     * @param secret Enrollment secret
     * @param role Identity role
     * @param organization Organization
     * @param affiliation Affiliation
     * @param registrar Registrar
     */
    public IdentityRequest(String name, String secret, IdentityRole role, String organization, String affiliation, String registrar) {
        this.name = name;
        this.secret = secret;
        this.role = role;
        this.organization = organization;
        this.affiliation = affiliation;
        this.registrar = registrar;
    }

    /**
     * Get identity name/enrollment ID
     *
     * @return Identity name/enrollment ID
     */
    public String getName() {
        return name;
    }

    /**
     * Set identity name/enrollment ID
     *
     * @param name Identity name/enrollment ID
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get enrollment secret
     *
     * @return Enrollment secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Set enrollment secret
     *
     * @param secret Enrollment secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Get identity role
     *
     * @return Identity role
     */
    public IdentityRole getRole() {
        return role;
    }

    /**
     * Set identity role
     *
     * @param role Identity role
     */
    public void setRole(IdentityRole role) {
        this.role = role;
    }

    /**
     * Get organization
     *
     * @return Organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Set organization
     *
     * @param organization Organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Get affiliation
     *
     * @return Affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Set affiliation
     *
     * @param affiliation Affiliation
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * Get registrar
     *
     * @return Registrar
     */
    public String getRegistrar() {
        return registrar;
    }

    /**
     * Set registrar
     *
     * @param registrar Registrar
     */
    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }
}
