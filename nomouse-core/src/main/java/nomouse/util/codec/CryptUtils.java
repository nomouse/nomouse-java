package nomouse.util.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * 加密和解密工具类 AES/DES
 */
public class CryptUtils {

    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String UTF8 = "UTF-8";

    /**
     * AES加密
     */
    public static String encryptAES(String source, String seed) {
        String result;
        try {
            byte[] rawKey = getAesKey(seed.getBytes());
            byte[] encryptKey = encryptAES(rawKey, source.getBytes());
            result = Hex.encodeHexString(encryptKey);
        } catch (Exception e) {
            return null;
        }
        return result;
    }


    /**
     * AES解密
     */
    public static String decryptAES(String source, String seed) {
        byte[] result;
        try {
            byte[] rawKey = getAesKey(seed.getBytes());
            byte[] enc = Hex.decodeHex(source.toCharArray());
            result = decryptAES(rawKey, enc);
        } catch (Exception e) {
            return null;
        }
        return new String(result);
    }

    private static byte[] getAesKey(byte[] seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        keyGenerator.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = keyGenerator.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encryptAES(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decryptAES(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


    // 根据KEY获取8位的byte[]
    private static byte[] getDesKey(String key) {
        byte[] result = new byte[8];
        StringBuilder sb = new StringBuilder();
        sb.append(key).append("12345678");

        try {
            result = sb.substring(0, 8).toString().getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 加密
     *
     * @param source
     * @return
     */
    public static String encryptDES(String source, String key, String iv) {
        String result = null;
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec dks = new DESKeySpec(getDesKey(key));
            SecretKey secretKey = keyFactory.generateSecret(dks);
            IvParameterSpec param = new IvParameterSpec(getDesKey(iv));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);
            byte[] tmpEncypt = cipher.doFinal(source.getBytes(UTF8));
            if (tmpEncypt != null) {
                result = Base64.encodeBase64String(tmpEncypt);
            }

        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 解密
     *
     * @param source
     * @return
     */
    public static String decryptDES(String source, String key, String iv) {
        String result = null;
        try {
            byte[] temp = Base64.decodeBase64(source);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            DESKeySpec dks = new DESKeySpec(getDesKey(key));
            SecretKey secretKey = keyFactory.generateSecret(dks);
            IvParameterSpec param = new IvParameterSpec(getDesKey(iv));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, param);
            result = new String(cipher.doFinal(temp), UTF8);
        } catch (Exception e) {
        }
        return result;
    }


}
