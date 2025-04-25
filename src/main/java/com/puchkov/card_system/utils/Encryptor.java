package com.puchkov.card_system.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class Encryptor {
    private final TextEncryptor encryptor;

    public Encryptor(@Value("${encryptor.secret-key}") String secretKey,
                     @Value("${encryptor.salt}") String salt) {
        this.encryptor = Encryptors.text(secretKey, salt);
    }

    public String encrypt(String s) {
        return encryptor.encrypt(s);
    }

    public String decrypt(String s) {
        return encryptor.decrypt(s);
    }
}
