package com.example.konsulyuk.Pasien;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.konsulyuk.Adapters.ListCounselingOneAdapter;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.LatestMessage;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CounselingOneFragment extends Fragment {

    RecyclerView recyclerView;
    ListCounselingOneAdapter adapter;
    ArrayList<ChatMessage> list;
    ArrayList<String> mDataKey;
    DatabaseReference reference, referenceUser;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counseling_one_pasien, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    LatestMessage lm = ds.getValue(LatestMessage.class);
//                    list.add(lm);
//                }
//                adapter = new ListCounselingOneAdapter(getActivity(), list);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    ChatMessage lm = dataSnapshot.getValue(ChatMessage.class);
                    list.clear();
                    list.add(lm);
                    adapter = new ListCounselingOneAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.recyclerview_counseling_one);
        list = new ArrayList<ChatMessage>();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String id_chat = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(mAuth.getUid());
        //referenceUser = FirebaseDatabase.getInstance().getReference().child("Users");
    }
}
