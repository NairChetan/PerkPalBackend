package com.perkpal.service;

import java.util.Base64;

public class HexToBase64 {

    public static void main(String[] args) {
        String hexString = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        byte[] bytes = hexStringToByteArray(hexString);
        String base64String = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base64 Encoded String: " + base64String);
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}