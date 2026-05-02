package com.brunobs.feature.sharing.contract;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SharingHashUtil {

    public static String generateHash(Long destApplicationId, List<String> features) {
        try {

            String featuresSorted = features.stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.joining(","));


            String raw = "destApp=" + destApplicationId + "|features=" + featuresSorted;


            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(raw.getBytes(StandardCharsets.UTF_8));

            // 4. Converte para hexadecimal
            return bytesToHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash de compartilhamento", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) {
                hex.append('0');
            }
            hex.append(s);
        }
        return hex.toString();
    }
}