package com.hrkj.scalp.util.rsa;

public class KeyManager {
    //公钥
    private static String public_key;
    //私钥
    private static String private_key;

    public static String getPublic_key() {
        return public_key;
    }
    public static void setPublic_key(String public_key) {
        KeyManager.public_key = public_key;
    }
    public static String getPrivate_key() {
        return private_key;
    }
    public static void setPrivate_key(String private_key) {
        KeyManager.private_key = private_key;
    }

}
