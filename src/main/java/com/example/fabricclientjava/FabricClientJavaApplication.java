package com.example.fabricclientjava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FabricClientJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FabricClientJavaApplication.class, args);
    }

    /**
     * Set Fabric discovery asLocalhost to true if deployed locally
     *
     * @param asLocalhost True if localhost
     */
    @Value("${fabric.discovery.localhost}")
    private void setDiscoveryAsLocalhost(boolean asLocalhost) {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", String.valueOf(asLocalhost));
    }

}
