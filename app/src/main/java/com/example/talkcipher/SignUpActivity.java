package com.example.talkcipher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talkcipher.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import Model.Users;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

   private  FirebaseAuth mAuth;
    FirebaseDatabase database;
    private Button generateOTPBtn,verifyOTPBtn;
    private EditText phoneNumber, enteredOTP,username;
    private ProgressDialog mSignUpProgress;
//    private String Currentusername;
    // string for storing our verification ID
    private String verificationId;
    private DatabaseReference mUserDatabase;
    private String userID;
    private FirebaseUser mcurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.signUppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mSignUpProgress = new ProgressDialog(this);

        phoneNumber = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        generateOTPBtn = findViewById(R.id.signUp);




        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(SignUpActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                    userRef
                            .orderByChild("phoneNumber")
                            .equalTo(phoneNumber.getText().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.getValue() != null){
                                        Toast.makeText(SignUpActivity.this, "Mobile number already registered. Please LogIn", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        String phone = "+91" + phoneNumber.getText().toString();

                                        mSignUpProgress.setTitle("Logging you in");
                                        mSignUpProgress.setMessage("Please wait while we automatically detect OTP");
                                        mSignUpProgress.setCanceledOnTouchOutside(false);
                                        mSignUpProgress.show();
                                        sendVerificationCode(phone);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }


            }
        });


    }

    //*ADDED* Methods For Phone Auth

    public void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            mSignUpProgress.dismiss();
                            String id = task.getResult().getUser().getUid();
                            Users user = new Users(binding.username.getText().toString(),binding.phone.getText().toString(), id,"default");
                            database.getReference().child("Users").child(id).setValue(user);
//                            database.getReference().child("USers").child(id).child("encryptionCode").setValue("none");


//                            //to add default encyption to none
                            mcurrentUser= FirebaseAuth.getInstance().getCurrentUser();
                            String currentUID = mcurrentUser.getUid();
                            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
                            mUserDatabase.child("encryptionCode").setValue("none");
//



//                            currentUser= FirebaseAuth.getInstance().getCurrentUser();
//                            userID = currentUser.getUid();
//                            Currentusername = username.getText().toString();
//                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
//                            HashMap<String,String> userMap = new HashMap<>();
//                            userMap.put("Name",Currentusername);
//                            mDatabase.setValue(userMap);

                            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            mSignUpProgress.hide();
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
//                enteredOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    public void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}






