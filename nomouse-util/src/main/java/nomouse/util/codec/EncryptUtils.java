package nomouse.util.codec;


import nomouse.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * 加解密类，包含aes,des,3des对称加密算法，和md5非对称加密算法
 */
public class EncryptUtils {

    private static final String DES = "DES";
    private static final String AES = "AES";

    /**
     * MD5加密
     *
     * @param source
     * @return
     */
    public static String md5Encrypt(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte b[] = md.digest(source.getBytes());
            return EncodeUtils.byteToHex(b);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(
                    "Unsupported Algorithm Exception", e);
        }
    }


    /**
     * 加密
     *
     * @param source
     * @return
     */
    public static String desEncrypt(String source, String key, String iv) {
        String result = null;
        byte[] sourceBytes;
        try {
            sourceBytes = source.getBytes(StringUtils.UTF8);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec dks = new DESKeySpec(desRawKey(key));
            SecretKey secretKey = keyFactory.generateSecret(dks);
            IvParameterSpec param = new IvParameterSpec(desRawKey(iv));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);
            byte[] encryptBytes = cipher.doFinal(sourceBytes);
            if (encryptBytes != null) {
                result = Base64.encodeBytes(encryptBytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 解密
     *
     * @param source
     * @return
     */
    public static String desDecrypt(String source, String key, String iv) {
        String result = null;
        byte[] sourceBytes;
        try {
            sourceBytes = Base64.decode(source);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec dks = new DESKeySpec(desRawKey(key));
            SecretKey secretKey = keyFactory.generateSecret(dks);
            IvParameterSpec param = new IvParameterSpec(desRawKey(iv));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);
            byte[] decryptBytes = cipher.doFinal(sourceBytes);
            if (decryptBytes != null) {
                result = new String(decryptBytes, StringUtils.UTF8);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 根据KEY获取8位的byte[]
    private static byte[] desRawKey(String key) {
        byte[] result = new byte[8];
        StringBuilder sb = new StringBuilder();
        sb.append(key).append("12345678");

        try {
            result = sb.substring(0, 8).toString().getBytes(StringUtils.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * AES算法加密
     *
     * @return 十六进制编码
     */
    private static String aesEncrypt(String source, String seed) {
        String result;

        try {
            byte[] rawKey = aesRawKey(seed.getBytes());
            byte[] encryptKey = aesEncrypt(rawKey, source.getBytes());
            result = EncodeUtils.byteToHex(encryptKey);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }


    /**
     * AES算法解密
     */
    private static String aesDecrypt(String seed, String hex) {
        byte[] result;
        try {
            byte[] rawKey = aesRawKey(seed.getBytes());
            byte[] enc = EncodeUtils.hexToByte(hex);
            result = aesDecrypt(rawKey, enc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new String(result);
    }


    private static byte[] aesRawKey(byte[] seed) throws Exception {
        KeyGenerator aes = KeyGenerator.getInstance(AES);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        // 192 and 256 bits may not be available
        aes.init(128, sr);
        SecretKey key = aes.generateKey();
        byte[] raw = key.getEncoded();
        return raw;
    }

    private static byte[] aesEncrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec spec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] aesDecrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec spec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, spec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


}
