package com.example.talkcipher;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.talkcipher.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import Adapters.ChatAdapter;
import Model.Messages;

public class ChatDetailsActivity extends AppCompatActivity  {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public  static String pwdtext="qwerty";
    private String encrypt;
    private String decrypt;
    private FirebaseUser mcurrentUser;
//    private DatabaseReference mDatabase;
    private String encCode;
    private String chatMessage;
    private KeyPair keyPair = RSAKeyPairGenerator.getKeyPair();

    PublicKey publicKey = keyPair.getPublic();
    byte[] publicKeyBytes = publicKey.getEncoded();
    String publicKeyBytesBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3UwrbHMoglDJ0NgK6oF+P20VKc2ZpxiPxuNrRec9yYSluYmYJlBYPW3XBn1r2zkwuTBgEnOdvfNGUQ5J3MWDFSDRt9SY2xYpjSPTGZKbf65cn6Aum6/iQ1PASNIVesfcFamFctLnGXekvt2p7XHNsSF2Sxpwt5IVDU9wKLC4pzL/F2WuyL85saZlFrBQOyfZcChoCB+OsnmMjCR7dMjSPL4AfZqdHur06bzN78Jr4rUY7OgCDcalF0+NDCXULzOElTYbpIQOn0uVpCBWNBwTq80gkVTtNnDO1p3ptYC8VkqOQppXMdXHqv9Zm6tRaMKhWxrZmCnDWjIiEA8Zv06/QIDAQAB";

    PrivateKey privateKey = keyPair.getPrivate();
    byte[] privateKeyBytes = privateKey.getEncoded();
    String privateKeyBytesBase64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCrdTCtscyiCUMnQ2ArqgX4/bRUpzZmnGI/G42tF5z3JhKW5iZgmUFg9bdcGfWvbOTC5MGASc52980ZRDkncxYMVING31JjbFimNI9MZkpt/rlyfoC6br+JDU8BI0hV6x9wVqYVy0ucZd6S+3antcc2xIXZLGnC3khUNT3AosLinMv8XZa7IvzmxpmUWsFA7J9lwKGgIH46yeYyMJHt0yNI8vgB9mp0e6vTpvM3vwmvitRjs6AINxqUXT40MJdQvM4SVNhukhA6fS5WkIFY0HBOrzSCRVO02cM7Wnem1gLxWSo5Cmlcx1ceq/1mbq1FowqFbGtmYKcNaMiIQDxm/Tr9AgMBAAECggEAC/vxNtbMX5AHrFUigfLMn5J2Gguk+weXu2L85FoX69Nyra7xBEP6BKGwGwUqfcQ9+hxcpQ+jmX+pC4jP+OCrrBl1Io1F+DKQwK9AS94Dekw5YJYUf9NxWk4+lS0G55B7XlIEIlZXFtus8reWW9MSFVMnqt4Wl1sLka01/4Kw3f9r0oh3C67T9TbgzU+ls+CtL0YR0FjQH8gtDyt5DnpiqND9dxK50eXDzVdQZrPlbZCLLLXd1UwXwIvv1gpArWm0ZaXMtBBfzviD80OyrrQEfsZfBkfLeEt8rUUW7ZwMrsB48E6agoMg1INtbE3DexWe2wHh7QBkT5OerGEOdYOiuQKBgQDdZbQtKMDph5F7FLa4Wp9OEJblEAOZ7spNAj/T4WzrAPv4oqiTSPzQ5ePyKUvaRxgV77ExK3y4fhXPSzBmEZUOdfiRZJlwEwhisfBYtiBUPtBLvoY/ilcMTREf6ewpO6FBr9kottkrMNnAfyRZu1C7YFCAUeaTXqqosp/fakeeSQKBgQDGQV0U5WxAxuY1BilLVS/K3BJbesVyGjcS/EzGJ5dScEAUrOCPst/um9OIaViP6VKxpBxyEQcOWRSKpRggnIFqDrhi0mqHSyKlvJeqwfkSXQdlWqYZAlhMdBrOeH8JLqIWkgUgc4qXavpFE+7PnacFk+rzrc93n2C9IDUc7H9HFQKBgEQMEV/b6ccdKuLIkAyDlfkZcjd/P4wdb8JRkaqe26ozSD64R9XVFhdiwTfZ5310YnvDixOQySmvXyaydnK4rraeHcMaR2rB/s7O4A90EuWx1LWjHoIDFBQsz7meXT6jHEIjOTyhTohO4G6g5VxLDVo6FSQLiDH9Y7isBiVV9iM5AoGBALdFpW6zx6b6N8W8tF/XmB7H4pB+phgc89yxL6fe4Zxr+FEkn1M34JsLojjwadZfsvpU45bu9r7kyoCArhU3mMsaYIm6SpBXfOgJt4s7Va0MItInQnTMHiRPEJjd+1HZsi17PxIIvY3Mbx98lb+H1ebqHsC1ZJ0I0L0FERRz7BUhAoGADgtied/J9ef1yWDiYK5kz+bmTtti4hqy2M8Zkt4TdZReEG0STT18zJabst9mRIgm/SnFhzs+wzCRdFlvpPaTXc8B5hlRcLErPlhBDOvJ6NUKsDKLRDuMY3GzuS1YqMRD3zjuer0oUZhAP3pVnssQ3pLmf6TEIbzbcZTvxvL9was=";

    //FOR DES
    private static SecretKey key;
    static String myEncKey="This is Key";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        setContentView(view);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mcurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = mcurrentUser.getUid();

        final String senderId = auth.getUid();
        String recieverId = getIntent().getStringExtra("userId");

        String senderRoom = senderId + recieverId;
        String recieverRoom = recieverId + senderId;

        String profile = getIntent().getStringExtra("userProfilePic");
        String userName = getIntent().getStringExtra("userName");








       //to find which type of encryption
        database.getReference().child("Users").child(currentUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                encCode = snapshot.child("encryptionCode").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        binding.username.setText(userName);

        Picasso.get().load(profile).networkPolicy(NetworkPolicy.OFFLINE) //offline capability
                .placeholder(R.drawable.ic_user).into(binding.profilePic, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(profile).placeholder(R.drawable.ic_user).into(binding.profilePic);

            }
        });

        binding.leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatDetailsActivity.this.finish();
            }
        });

        final ArrayList<Messages> messageList = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageList, this);

        database.getReference().child("Chats").child(senderRoom).keepSynced(true); //offline capability
        database.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {

                     Messages message = snapshot1.getValue(Messages.class);
                     if(message.getEncType().equals("aes")){
                         try {
                             decrypt = AES.decrypt(message.getMessage(),pwdtext) ;
                             message.setMessage(decrypt);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
//                         message.setMessage(decrypt);
                     }
                     if(message.getEncType().equals("rsa")){
                        try {
                            decrypt = RSA.decrypt(message.getMessage(),privateKeyBytesBase64);
//                            Log.d("prem", "######################------------------------------------------decrypted msg:"+ decrypt);
//                            Log.d("prem", "######################------------------------------------------ private key:"+ privateKeyBytesBase64);
                            message.setMessage(decrypt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        message.setMessage(decrypt);
                    }
                     if(message.getEncType().equals("des")){
                        try {
                            Log.d("prem", "######################------------------------------------------ private key:"+ message.getMessage());
                            key = DES.keyGeneration(myEncKey);
                            decrypt = DES.decrypt(message.getMessage(),key);
                            Log.d("prem", "######################------------------------------------------ private key:"+ decrypt);

                            message.setMessage(decrypt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    messageList.add(message);
                }

                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });
        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String mssg = binding.messageTextBox.getText().toString();
//                Messages temp = new Messages(senderId,mssg);

                    if(encCode.equals("aes")){
                        try {
                            mssg = AES.encrypt(mssg,pwdtext);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                 if(encCode.equals("rsa")){
                    try {
                        mssg = RSA.encrypt(mssg,publicKeyBytesBase64);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                if(encCode.equals("des")){
                    try {
                        key = DES.keyGeneration(myEncKey);
                         mssg = DES.encrypt(mssg,key);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }



                final Messages Message = new Messages(senderId, mssg,encCode);



//                 Message = new Messages(senderId, mssg);
                Message.setTimeStamp(new Date().getTime());
                Message.setEncType(encCode);
                binding.messageTextBox.setText("");


                database.getReference().child("Chats").child(senderRoom).keepSynced(true); //offline capability
                database.getReference().child("Chats").child(senderRoom).push().setValue(Message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("Chats").child(recieverRoom).keepSynced(true);
                        database.getReference().child("Chats").child(recieverRoom).push().setValue(Message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });
            }
        });
        binding.chatrecyclerview.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecyclerview.setLayoutManager(layoutManager);



    }

    public static KeyPair getKeyPair() {
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            kp = kpg.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kp;
    }

}