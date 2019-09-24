package com.chaco.chao.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * author:zhaopeiyan001
 * Date:2019-03-15 10:59
 */
public class SignUtils {
    public SignUtils() {
    }

    public static String sign(String plainText, String key, Algorithm algorithm) {
        Objects.requireNonNull(plainText, "plainText is null");
        Objects.requireNonNull(key, "key is null");
        Objects.requireNonNull(algorithm, "algorithm is null");
        Object var3 = null;

        byte[] result;
        try {
            if (!algorithm.getName().toLowerCase().startsWith("md") && !algorithm.getName().toLowerCase().startsWith("sha")) {
                if (!algorithm.getName().toLowerCase().startsWith("hmac")) {
                    throw new RuntimeException(algorithm + " algorithm is not support");
                }

                SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), algorithm.getName());
                Mac mac = Mac.getInstance(algorithm.getName());
                mac.init(keySpec);
                result = mac.doFinal(plainText.getBytes("utf-8"));
            } else {
                plainText = plainText + "&key=" + key;
                result = MessageDigest.getInstance(algorithm.getName()).digest(plainText.getBytes("utf-8"));
            }
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }

        return Base64.getEncoder().encodeToString(result);
    }

    public static boolean verify(String plainText, String key, Algorithm algorithm, String sign) {
        Objects.requireNonNull(sign, "sign is null");
        return sign.equalsIgnoreCase(sign(plainText, key, algorithm));
    }

    public static void main(String[] args) {
        String plainText = "name=jack&age=23&name=jack&age=23&name=jack&age=23&name=jack&age=23";
        String key = "123456";
        System.out.println("plainText : " + plainText);
        String sign = sign(plainText, key, Algorithm.HMAC_SHA_256);
        System.out.println(sign);
        boolean verify = verify(plainText, key, Algorithm.HMAC_SHA_256, sign);
        System.out.println(verify);
    }
}
