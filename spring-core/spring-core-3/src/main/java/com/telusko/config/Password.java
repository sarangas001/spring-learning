package com.telusko.config;

public class Password {
    String algo;

    public Password(String algo) {
        this.algo = algo;
        System.out.println("Password bean created");
    }

    public String algoInfo() {
        return this.algo;
    }

}
