package com.example.talkcipher;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private MD5(){}
    String password="";



    public static  String hashPassword(String password)
    {
        String passwordtohash=password;
        String generatedPassword=null;
        try{
            MessageDigest md=MessageDigest.getInstance("MD5");
            //Returns a MessageDigest object that implements the specified digest algorithm.
            md.update(passwordtohash.getBytes());
            //Updates the digest using the specified array of bytes.
            byte[] bytes=md.digest();
            //Completes the hash computation by performing final operations such as padding.
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<bytes.length;i++)
            {
                sb.append(Integer.toString(( bytes[i]&0xff)+0x100,32).substring(1));
                //pehle widen krra h and baad me 0x100 add krra nd hexadecimal me convert krde
                // //and pehla character utha leta and builder me add krde
            }
            generatedPassword=sb.toString();
            Log.d("BABBAR","generated password"+generatedPassword);
            return generatedPassword;

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
