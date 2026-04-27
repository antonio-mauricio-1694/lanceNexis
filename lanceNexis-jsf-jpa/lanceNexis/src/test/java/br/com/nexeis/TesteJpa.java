package br.com.nexeis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jakarta.persistence.Persistence;

/**
 * Unit test for simple App.
 */
public class TesteJpa {

    public static void main(String [] args) {
    	
    	
    	Persistence.createEntityManagerFactory("lanceNexis");
    }
}
