package Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.talkcipher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.security.auth.callback.Callback;


public class cipherFragment extends Fragment {

    public ToggleButton btnAES,btnDES,btnRSA;
    public int EncryptionType=0;
    Callback mCallback;
    Activity mActivity;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mcurrentUser;
    private String mEncode;






    public cipherFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cipher, container, false);
//        return inflater.inflate(R.layout.fragment_cipher, container, false);

        btnAES = view.findViewById(R.id.aes);
        btnRSA = view.findViewById(R.id.rsa);
        btnDES = view.findViewById(R.id.des);
        mcurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = mcurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
//        mUserDatabase.keepSynced(true);

////        HashMap<String, String> userMap = new HashMap<>();
//        mUserDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mEncode = snapshot.child("encryptionCode").getValue().toString();
//                if(mEncode == "aes")
//                {
//                    ToggleButton simpleToggleButton = (ToggleButton) view.findViewById(R.id.aes); // initiate a toggle button
//                    simpleToggleButton.setChecked(true);
//                }
//                if(mEncode == "rsa")
//                {
//                    ToggleButton simpleToggleButton = (ToggleButton) view.findViewById(R.id.rsa); // initiate a toggle button
//                    simpleToggleButton.setChecked(true);                }
//                if(mEncode == "des")
//                {
//                    ToggleButton simpleToggleButton = (ToggleButton) view.findViewById(R.id.des); // initiate a toggle button
//                    simpleToggleButton.setChecked(true);
//                    btnDES.setChecked(true);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });





        btnAES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnDES.setChecked(false);
                    btnRSA.setChecked(false);
                   mUserDatabase.child("encryptionCode").setValue("aes");

                }
            }
        });
        btnRSA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnAES.setChecked(false);
                    btnDES.setChecked(false);

                    mUserDatabase.child("encryptionCode").setValue("rsa");
                }
            }
        });
        btnDES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnRSA.setChecked(false);
                    btnAES.setChecked(false);
                    mUserDatabase.child("encryptionCode").setValue("des");
                }
            }
        });
        return view;
    }



}





