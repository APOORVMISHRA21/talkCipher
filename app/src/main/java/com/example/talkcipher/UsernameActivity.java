package com.example.talkcipher;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsernameActivity extends AppCompatActivity {

    private Button mChangeUserName;
    private EditText mUserNameChange;
    //fireabse
    private DatabaseReference mDatabaseRefrence;
    private FirebaseUser mCurrentUser;
    private Toolbar mToolbar;

    //progress
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        //firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = mCurrentUser.getUid();
        mDatabaseRefrence = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);

        String current = getIntent().getStringExtra("current_username");

        mChangeUserName = findViewById(R.id.btn_userName_account);
        mUserNameChange = findViewById(R.id.userNameUpdate);
        mUserNameChange.setText(current);

        mChangeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress = new ProgressDialog(UsernameActivity.this);
                mProgress.setTitle("Saving changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();

                String name = mUserNameChange.getText().toString();
                mDatabaseRefrence.child("userName").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error occurred",Toast.LENGTH_LONG).show();

                        }


                    }
                });

            }
        });






    }
}