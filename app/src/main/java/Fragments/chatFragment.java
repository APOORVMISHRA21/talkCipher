package Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.talkcipher.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

import Adapters.UserAdapter;
import Model.Users;

public class chatFragment extends Fragment {

    public chatFragment() {
        // Required empty public constructor
    }

    FragmentChatBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;
    private DatabaseReference reference;
    private UserAdapter adapter;


    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.e("DATA_CHANGE", snapshot.toString());
            list.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                Users users = dataSnapshot.getValue(Users.class);
                users.setUserId(dataSnapshot.getKey());
                if (!users.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    list.add(users);
                }
            }

            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("DATA_CHANGE", error.getMessage());
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();

        adapter = new UserAdapter(list, getContext());
        binding.charRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.charRecyclerView.setLayoutManager(layoutManager);

        reference = database.getReference().child("Users");

        adapter.notifyDataSetChanged();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        reference.removeEventListener(valueEventListener);
    }

}