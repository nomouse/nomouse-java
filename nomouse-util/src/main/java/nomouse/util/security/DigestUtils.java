/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package nomouse.util.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;

/**
 * 支持sha-1/md5消息摘要的工具类。
 * <p/>
 * 返回byte[]，可进一步被编码为Hex, Base64或UrlSafeBase64
 *
 * @author nomouse
 */
public class DigestUtils {

    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";
    private static final String HMAC = "HmacSHA1";

    private static SecureRandom random = new SecureRandom();

    public static byte[] md5(byte[] input) {
        return digest(input, MD5, null, 1);
    }

    public static byte[] md5(byte[] input, byte[] salt) {
        return digest(input, MD5, salt, 1);
    }

    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }


    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }


    public static byte[] hmac(byte[] input, byte[] salt) {
        try {
            Mac mac = Mac.getInstance(HMAC);

            SecretKeySpec secretKey = new SecretKeySpec(
                    salt, mac.getAlgorithm());

            mac.init(secretKey);

            byte[] hash = mac.doFinal(input);

            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成随机的Byte[]作为salt.
     *
     * @param numBytes byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对文件进行md5散列.
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * 对文件进行sha1散列.
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
