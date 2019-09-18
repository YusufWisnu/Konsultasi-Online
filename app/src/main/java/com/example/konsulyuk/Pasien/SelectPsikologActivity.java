package com.example.konsulyuk.Pasien;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.konsulyuk.Adapters.ListSelectPsikologAdapter;
import com.example.konsulyuk.Models.Psikolog;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectPsikologActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Psikolog> mListPsikolog;
    ListSelectPsikologAdapter adapter;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_psikolog);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Pilih Psikolog");
        recyclerView = findViewById(R.id.recyclerview_select_psikolog);
        mListPsikolog = new ArrayList<Psikolog>();
        reference = FirebaseDatabase.getInstance().getReference().child("Psikolog");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListPsikolog.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Psikolog psi = ds.getValue(Psikolog.class);
                    mListPsikolog.add(psi);
                }
                adapter = new ListSelectPsikologAdapter(SelectPsikologActivity.this, mListPsikolog);
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(SelectPsikologActivity.this, DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
