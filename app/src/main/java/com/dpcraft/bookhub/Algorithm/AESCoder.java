package com.dpcraft.bookhub.Algorithm;

/**
 * Created by DPC on 2017/6/6.
 */
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    public static byte[] initSecretKey() {

        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        String password = "uestc*bookhub";
        kg.init(128, new SecureRandom(password.getBytes()));
        //生成一个密钥
        SecretKey  secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    private static Key toKey(byte[] key){
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data,Key key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte[] data,Key key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    private static String  showByteArray(byte[] data){
        if(null == data){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(byte b:data){
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }
    public static String encrypt(String data)throws Exception{
       // byte[] key = initSecretKey();
        byte[] key = {109,-69,-43,11,-56,84,-85,40,-27,-108,59,-119,-73,89,-115,75};
        System.out.println("key：" + showByteArray(key));
        Key k = toKey(key); //生成秘钥
        System.out.println("加密前数据: string:"+data);
        System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), k);//数据加密
        System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));
        String str = bytesToHexString(encryptData);
        System.out.println("转换成str： " + str);
        byte[] bytes = StringToBytes(str);
        System.out.println("转换回来byte[] " + showByteArray(bytes));
        return bytesToHexString(encryptData);
    }
    public static String decrypt(String data)throws Exception{
        //byte[] key = initSecretKey();
        byte[] key = {109,-69,-43,11,-56,84,-85,40,-27,-108,59,-119,-73,89,-115,75};
        System.out.println("key："+showByteArray(key));
        Key k = toKey(key); //生成秘钥
        byte[] encryptedData = StringToBytes(data);
        System.out.println("读取的加密数据: byte[]:"+showByteArray(encryptedData));
        byte[] decryptData = decrypt(encryptedData, k);//数据解密

        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));
        return new String(decryptData);
    }
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); //两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   // 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; // A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); //两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); // 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; // A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}


