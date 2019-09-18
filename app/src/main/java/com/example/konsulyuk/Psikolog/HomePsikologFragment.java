package com.example.konsulyuk.Psikolog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePsikologFragment extends Fragment {

    TextView tv_change, tv_name;
    SwitchCompat mSwitchCompact;
    FirebaseAuth mAuth;
    DatabaseReference ref_status;
    RatingBar ratingBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_psikolog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwitchCompact = view.findViewById(R.id.switch1);
        tv_change = view.findViewById(R.id.txt_view_status);
        tv_name = view.findViewById(R.id.tv_name_home_fragment_psikolog);
        ratingBar = view.findViewById(R.id.ratingbar_home_psikolog);
        mAuth = FirebaseAuth.getInstance();
        ref_status = FirebaseDatabase.getInstance().getReference().child("Psikolog/"+mAuth.getUid());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadUser();

        mSwitchCompact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_change.setText("AKTIF");
                    tv_change.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_light));
                    ref_status.child("status_aktif").setValue(true);
                }else{
                    tv_change.setText("Off");
                    tv_change.setTextColor(getContext().getResources().getColor(android.R.color.tab_indicator_text));
                    ref_status.child("status_aktif").setValue(false);
                }
            }
        });

    }

    private void loadUser() {
        ref_status.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("status_aktif").exists()){
                        boolean aktif = dataSnapshot.child("status_aktif").getValue(boolean.class);
                        float rating = dataSnapshot.child("rating").getValue(float.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        if(aktif){
                            mSwitchCompact.setChecked(aktif);
                        }
                        ratingBar.setRating(rating);
                        tv_name.setText(name);
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(mSwitchCompact.isChecked()){
            tv_change.setText("AKTIF");
            tv_change.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_light));
        }else{
            tv_change.setText("Off");
            tv_change.setTextColor(getContext().getResources().getColor(android.R.color.tab_indicator_text));
        }
    }
}
