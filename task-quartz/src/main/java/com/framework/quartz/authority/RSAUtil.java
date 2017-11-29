package com.framework.quartz.authority;

import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 *  RSA安全编码组件
 * Created by HR on 2017/6/29.
 */

public class RSAUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String KEY_ALGORITHM_TO_APP = "RSA/ECB/PKCS1Padding";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final int KEY_SIZE = 1024;

    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * 初始化公钥私钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /**
     * 获取公钥
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Util.encryptBASE64(key.getEncoded());
    }

    public static String getPrivateKey(Map<String, Object> keyMap){
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Util.encryptBASE64(key.getEncoded());
    }


    /**
     * 公钥加密
     * @param data
     * @param key
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        // 对公钥解密
        byte[] keyBytes = Base64Util.decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        //Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_TO_APP);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param data
     * @param key
     * @return
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        // 对密钥解密
        byte[] keyBytes = Base64Util.decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_TO_APP);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥对data生成数字签名
     * @param data 加密数据
     * @param privateKey
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(byte[] data, String privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        // 解密由base64编码的私钥
        byte[] keyBytes = Base64Util.decryptBASE64(privateKey);

        // 秘钥 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        /* KEY_ALGORITHM 指定的加密算法 */
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return Base64Util.encryptBASE64(signature.sign());
    }

    /**
     * 公钥 验证 数字证书
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 解密由base64编码的公钥
        byte[] keyBytes = Base64Util.decryptBASE64(publicKey);

        // 公钥 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(Base64Util.decryptBASE64(sign));
    }

    /**
     * 私钥 加密
     * @param data
     * @param key
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 对密钥解密
        byte[] keyBytes = Base64Util.decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_TO_APP);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 公钥 解密
     * @param data
     * @param key
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        // 对密钥解密
        byte[] keyBytes = Base64Util.decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_TO_APP);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    @Test
    public void testKeysCreate() throws NoSuchAlgorithmException, BadPaddingException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

//        Map<String, Object> initKeys = initKey();
//        System.out.println("publicKey:--->>>" + initKeys.get(PUBLIC_KEY));
//        System.out.println("privateKey:--->>>" + initKeys.get(PRIVATE_KEY));
//
//        String publicKey = getPublicKey(initKeys);
//        String privateKey = getPrivateKey(initKeys);

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDX42SuRa+YQTwA+a5E0EfMmJeDMxqg2XjjYdzyybhLPIm5nAeeiordl4mYcWnANFr0W4YsBa6rq0QB6zpbWMbJcrS8X47/XeCPtwISwaELXjNs6B9a1eg0gYTq2x7oO0M35kk5SXm5jg5/cLk352pbZkKXWcL5mMYI0SxIN/urcQIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANfjZK5Fr5hBPAD5rkTQR8yYl4MzGqDZeONh3PLJuEs8ibmcB56Kit2XiZhxacA0WvRbhiwFrqurRAHrOltYxslytLxfjv9d4I+3AhLBoQteM2zoH1rV6DSBhOrbHug7QzfmSTlJebmODn9wuTfnaltmQpdZwvmYxgjRLEg3+6txAgMBAAECgYAb9Reptd6KghAqNCtMQAi8Rxp/5BNTtzX/hEHfsx3JqHpRbhJShrB7B8/KzQ0b1evdbyjN9KJtGs3AzdyNzc5YNR4kMAouxzU801IT85wxlc9VejYT3CvIRjD736hX8VdJs0oxBI4vsHxI6EhlqFNM2swr3RhX28jRip/3rZCwwQJBAPqa2nGgdC7PE0n8EN5nG/2ySO3pVPLENDai+4ctVOgnoUtY1gFOtCq+rkBgtGdalq3SxhXiBk0OEdNt4NDUbYkCQQDciTU0xn3erm1beJdjgiviqeIVT8AkH8uBtmgVlqyFWony/YeqxG04uKeTbLHuQEvpXJVKoRCcclZD461d6XypAkEA6ddPWFGxt+/v/sxfbTfnbY5L8PwWEUW1tvnUHOnW3Z4FKsqlPqkTAwPUkyvpiruD+ITB469507L6PUC9U/+0iQJAYdlnCBGBy4ms78bjtL0O57TkoPwPjnek/dqG9/0wcsKnLm8bAMPCikYz5A94KvPQOqxfeQtZqXB5ogmk5GQycQJBAL1ww+SNxdYlqyAVUh6UAbJrBbLy5mLxrQOvSQQWT/z8DztQt8R7DPVQtdedG73aTUa6RzGsgJA3YsyTCROAqmk=";

//        System.out.println("publicKeyBase64:--->>>" + getPublicKey(initKeys));
//        System.out.println("privateKeyBase64:--->>>" + getPrivateKey(initKeys));

        String data = "username:password";

        byte[] encryptData = encryptByPublicKey(data.getBytes(), publicKey);
        System.out.println("EncryptData:--->>> " + new String(encryptData, "utf-8"));
        System.out.println("EncryptDataBase64:--->>> " + Base64Util.encryptBASE64(encryptData));

        byte[] decode = decryptByPrivateKey(encryptData, privateKey);
        System.out.println("DecodeData:--->>>" + new String(decode));
    }

}
