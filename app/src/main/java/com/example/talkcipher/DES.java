package com.example.talkcipher;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES{
    private static final String UNICODE_FORMAT="UTF8";
    public static final String DES_ENCRYPTION_SCHEME="DES";
    private static KeySpec myKeySpec;
    private static SecretKeyFactory mySecretKeyFactory;
    private static Cipher cipher;
    static byte[] KeyAsBytes;
    private static String myEncryptionKey;
    private static String getMyEncryptionScheme;
    private static SecretKey mKey;
    private static SecretKey key;
    static String myEncKey="This is Key";

    public static SecretKey keyGeneration(String myEncKey){
        //initialising key generation part
        myEncryptionKey=myEncKey;
        getMyEncryptionScheme=DES_ENCRYPTION_SCHEME;
        try {
            KeyAsBytes=myEncryptionKey.getBytes(UNICODE_FORMAT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            myKeySpec=new DESKeySpec(KeyAsBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            mySecretKeyFactory=SecretKeyFactory.getInstance(getMyEncryptionScheme);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            cipher=Cipher.getInstance(getMyEncryptionScheme);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            mKey=mySecretKeyFactory.generateSecret(myKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
//        Log.d("prem", "######################------------------------------------------ private key:"+ mKey);

        return mKey;
    }


    public static String encrypt(String unencryptedString,SecretKey key)
    {        key = keyGeneration(myEncKey);
             SecretKey keyUsed = key;
        String encryptedString =null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyUsed);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);

            encryptedString=Base64.encodeToString(encryptedText, Base64.DEFAULT);
        }
        catch (InvalidKeyException| UnsupportedEncodingException |IllegalBlockSizeException |BadPaddingException e) {

        }

        return encryptedString;
    }

    public static String decrypt(String encrytedString,SecretKey key)
    { String decryptedText = null;
         SecretKey keyUsed = key;
        try {
            cipher.init(Cipher.DECRYPT_MODE, keyUsed);
            byte[] encryptedText = Base64.decode(encrytedString, Base64.DEFAULT);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(plainText);
        }catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {

        }
        return decryptedText;
    }

    //convert a byte[] into a string
    private static String bytes2String(byte[] bytes)
    {
        StringBuilder stringBuffer=new StringBuilder();
        for(int i=0;i<bytes.length;i++)
        {
            stringBuffer.append((char) bytes[i]);

        }
        return stringBuffer.toString();
    }

}