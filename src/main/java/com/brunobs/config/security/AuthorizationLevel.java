package com.brunobs.config.security;

public enum AuthorizationLevel {

    OPEN,   // acesso livre
    DEV,    // apenas ambiente DEV
    TST,    // DEV e HOM
    ADM,  // DEV, HOM e PROD
    OWNER

}