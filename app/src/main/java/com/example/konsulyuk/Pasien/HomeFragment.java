package com.example.konsulyuk.Pasien;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.konsulyuk.Models.User;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    DatabaseReference mReference, refSaldo, ref_nilai;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    TextView tv_name, tv_saldo;
    Button startCounseling_btn;
    FloatingActionButton btn_kuesioner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_pasien, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        fetchUser();

        startCounseling_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SelectPsikologActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //getActivity().finish();
                startActivity(i);
            }
        });

        btn_kuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref_nilai.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(mAuth.getUid()).exists()){
                            Intent i = new Intent(getActivity(), HasilPenilaianActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getActivity(), PenilaianDiriActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_name = getView().findViewById(R.id.tv_name_home_fragment);
        tv_saldo = getView().findViewById(R.id.tv_saldo_home_fragment);
        startCounseling_btn = getView().findViewById(R.id.start_counseling_button);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        btn_kuesioner = view.findViewById(R.id.btn_kuesioner_home);
        btn_kuesioner.startAnimation(animation);
        ref_nilai = FirebaseDatabase.getInstance().getReference("Disc/");
    }

    private void fetchUser(){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String id_pasien = firebaseUser.getUid();
        mReference = FirebaseDatabase.getInstance().getReference().child("Users/"+id_pasien);
        refSaldo = FirebaseDatabase.getInstance().getReference().child("Saldo/"+id_pasien);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("name").getValue(String.class);
                tv_name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refSaldo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //int saldo = dataSnapshot.child("saldo").getValue(int.class);
                //tv_saldo.setText(String.valueOf(saldo));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
