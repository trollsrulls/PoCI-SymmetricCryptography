package org.maxim.symmetriccryptography.service;

public interface Encoder {

    String encrypt(String msg);

    String decrypt(String msg);

}
