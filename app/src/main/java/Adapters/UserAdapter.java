package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkcipher.AES;
import com.example.talkcipher.ChatDetailsActivity;
import com.example.talkcipher.DES;
import com.example.talkcipher.R;
import com.example.talkcipher.RSA;
import com.example.talkcipher.RSAKeyPairGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import Model.Users;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context context;
    public  static String pwdtext="qwerty";
    private String mssgLast;

    private KeyPair keyPair = RSAKeyPairGenerator.getKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    byte[] privateKeyBytes = privateKey.getEncoded();
    String privateKeyBytesBase64 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCrdTCtscyiCUMnQ2ArqgX4/bRUpzZmnGI/G42tF5z3JhKW5iZgmUFg9bdcGfWvbOTC5MGASc52980ZRDkncxYMVING31JjbFimNI9MZkpt/rlyfoC6br+JDU8BI0hV6x9wVqYVy0ucZd6S+3antcc2xIXZLGnC3khUNT3AosLinMv8XZa7IvzmxpmUWsFA7J9lwKGgIH46yeYyMJHt0yNI8vgB9mp0e6vTpvM3vwmvitRjs6AINxqUXT40MJdQvM4SVNhukhA6fS5WkIFY0HBOrzSCRVO02cM7Wnem1gLxWSo5Cmlcx1ceq/1mbq1FowqFbGtmYKcNaMiIQDxm/Tr9AgMBAAECggEAC/vxNtbMX5AHrFUigfLMn5J2Gguk+weXu2L85FoX69Nyra7xBEP6BKGwGwUqfcQ9+hxcpQ+jmX+pC4jP+OCrrBl1Io1F+DKQwK9AS94Dekw5YJYUf9NxWk4+lS0G55B7XlIEIlZXFtus8reWW9MSFVMnqt4Wl1sLka01/4Kw3f9r0oh3C67T9TbgzU+ls+CtL0YR0FjQH8gtDyt5DnpiqND9dxK50eXDzVdQZrPlbZCLLLXd1UwXwIvv1gpArWm0ZaXMtBBfzviD80OyrrQEfsZfBkfLeEt8rUUW7ZwMrsB48E6agoMg1INtbE3DexWe2wHh7QBkT5OerGEOdYOiuQKBgQDdZbQtKMDph5F7FLa4Wp9OEJblEAOZ7spNAj/T4WzrAPv4oqiTSPzQ5ePyKUvaRxgV77ExK3y4fhXPSzBmEZUOdfiRZJlwEwhisfBYtiBUPtBLvoY/ilcMTREf6ewpO6FBr9kottkrMNnAfyRZu1C7YFCAUeaTXqqosp/fakeeSQKBgQDGQV0U5WxAxuY1BilLVS/K3BJbesVyGjcS/EzGJ5dScEAUrOCPst/um9OIaViP6VKxpBxyEQcOWRSKpRggnIFqDrhi0mqHSyKlvJeqwfkSXQdlWqYZAlhMdBrOeH8JLqIWkgUgc4qXavpFE+7PnacFk+rzrc93n2C9IDUc7H9HFQKBgEQMEV/b6ccdKuLIkAyDlfkZcjd/P4wdb8JRkaqe26ozSD64R9XVFhdiwTfZ5310YnvDixOQySmvXyaydnK4rraeHcMaR2rB/s7O4A90EuWx1LWjHoIDFBQsz7meXT6jHEIjOTyhTohO4G6g5VxLDVo6FSQLiDH9Y7isBiVV9iM5AoGBALdFpW6zx6b6N8W8tF/XmB7H4pB+phgc89yxL6fe4Zxr+FEkn1M34JsLojjwadZfsvpU45bu9r7kyoCArhU3mMsaYIm6SpBXfOgJt4s7Va0MItInQnTMHiRPEJjd+1HZsi17PxIIvY3Mbx98lb+H1ebqHsC1ZJ0I0L0FERRz7BUhAoGADgtied/J9ef1yWDiYK5kz+bmTtti4hqy2M8Zkt4TdZReEG0STT18zJabst9mRIgm/SnFhzs+wzCRdFlvpPaTXc8B5hlRcLErPlhBDOvJ6NUKsDKLRDuMY3GzuS1YqMRD3zjuer0oUZhAP3pVnssQ3pLmf6TEIbzbcZTvxvL9was=";
    private static SecretKey key;
    static String myEncKey="This is Key";
    public UserAdapter(ArrayList<Users> list,Context context)
    {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_user,parent,false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Users user = list.get(position);

        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.ic_user).into(holder.image);
        holder.userName.setText(user.getUserName());

        FirebaseDatabase.getInstance().getReference().child("Chats").child(FirebaseAuth.getInstance().getUid() + user.getUserId()).orderByChild("timestamp").limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                        {
                            for(DataSnapshot snapshot1: snapshot.getChildren())
                            {
//                                holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                                String encryptedMessage = snapshot1.child("message").getValue().toString();
                                String encType = snapshot1.child("encType").getValue().toString();
                                if(encType.equals("aes"))
                                {
                                    try {
                                        mssgLast = AES.decrypt(encryptedMessage, pwdtext);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                 if (encType.equals("rsa"))
                                {
                                    try{
                                        mssgLast = RSA.decrypt(encryptedMessage, privateKeyBytesBase64);
                                    } catch(Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                 if(encType.equals("des"))
                                {
                                    try{
                                        key = DES.keyGeneration(myEncKey);
                                        mssgLast = DES.decrypt(encryptedMessage,key);
                                    } catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                 if(encType.equals("none"))
                                 {
                                     mssgLast = encryptedMessage;
                                 }
                                holder.lastMessage.setText(mssgLast);
                            }



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailsActivity.class);
                intent.putExtra("userId",user.getUserId());
                intent.putExtra("userName",user.getUserName());
                intent.putExtra("userProfilePic",user.getProfilePic());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView userName;
        TextView lastMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.sample_profile_image);
            userName = itemView.findViewById(R.id.sample_username);
            lastMessage = itemView.findViewById(R.id.sample_lastMessage);
        }
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
