package com.example.talkcipher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mcurrentUser;

    //layout
    private CircleImageView mImage;
    private TextView mUserName,mUserPhoneNumber;
    private Button mImageBtn;
    private Button mUsernameBtn;
    //storage firebase
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDialog;
    private UploadTask uploadTask;
    private  String displayimageUrl;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mImage = findViewById(R.id.circleImageView);
        mUserName = findViewById(R.id.account_display_name);
        mImageBtn = findViewById(R.id.btn_changeimg);
        mUsernameBtn= findViewById(R.id.btn_change_username);
        mUserPhoneNumber= findViewById(R.id.userPhoneNumber);
        mImageStorage = FirebaseStorage.getInstance().getReference();

        //retrieving data from firebase


        mcurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = mcurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        mUserDatabase.keepSynced(true); //offline capability only work for string int
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue().toString();
                String userPhoneNumber ="+91 " + snapshot.child("phoneNumber").getValue().toString();
                final String image = snapshot.child("profilePic").getValue().toString();
                mUserName.setText(name);
                mUserPhoneNumber.setText(userPhoneNumber);
                if (!image.equals("default")) {
                    Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE) //offline image
                            .placeholder(R.drawable.default_imgh).into(mImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image).placeholder(R.drawable.default_imgh).into(mImage);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current_username = mUserName.getText().toString();
                Intent usernameIntent = new Intent(SettingsActivity.this,UsernameActivity.class);
                usernameIntent.putExtra("current_username",current_username);
                startActivity(usernameIntent);

            }
        });


        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mProgressDialog = new ProgressDialog(SettingsActivity.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we uplaod and process the image.");
                mProgressDialog.show();
                Uri resultUri = result.getUri();
                File thumb_filepath = new File(resultUri.getPath());
                Bitmap thumb_bitmap = new Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(75)
                        .compressToBitmap(thumb_filepath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                final byte[] thumb_byte = baos.toByteArray();



                String currentUID = mcurrentUser.getUid();
                final StorageReference thumb_pathInFirebae =mImageStorage.child("profile_images").child("thumbs").child(currentUID + ".jpg");
                final StorageReference filepath = mImageStorage.child("profile_images").child(currentUID+ ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            mProgressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();
                            //https://stackoverflow.com/questions/50554548/error-cannot-find-symbol-method-getdownloadurl-of-type-com-google-firebase-st
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String download_url = uri.toString();
                                    UploadTask uploadTask = thumb_pathInFirebae.putBytes(thumb_byte);
                                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                thumb_pathInFirebae.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String thumb_downloadUrl = uri.toString();
                                                        Map update_hashMap = new HashMap<>();
                                                        update_hashMap.put("profilePic",thumb_downloadUrl);
//                                                        update_hashMap.put("thumb_image",thumb_downloadUrl);
                                                        mUserDatabase.updateChildren(update_hashMap);


                                                    }
                                                });




                                            }

                                        }
                                    });



                                }
                            });

                        }
                        else {

                            Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();

                        }

                    }
                });




            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}