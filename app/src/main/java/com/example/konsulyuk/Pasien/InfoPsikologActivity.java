package com.example.konsulyuk.Pasien;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konsulyuk.ChatActivity;
import com.example.konsulyuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InfoPsikologActivity extends AppCompatActivity {

    Spinner spinner1;
    DatabaseReference reference, refJadwal;
    List<String> jadwal_psi;
    TextView tv_judul, alamat, senin, senin1, senin2, selasa, selasa1, selasa2, rabu, rabu1, rabu2, kamis, kamis1, kamis2, jumat, jumat1, jumat2;
    Button btn_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_psikolog);

        Bundle bundle = getIntent().getExtras();
        final String to_id = bundle.getString("PSI ID");
        final String psi_name = bundle.getString("PSI NAME");

        //Toast.makeText(InfoPsikologActivity.this, to_id, Toast.LENGTH_SHORT).show();

        tv_judul = findViewById(R.id.tv_judul_nama_psikolog);
        alamat = findViewById(R.id.tv_alamat);
        senin = findViewById(R.id.senin);
        senin1 = findViewById(R.id.senin1);
        senin2 = findViewById(R.id.senin2);
        selasa = findViewById(R.id.selasa);
        selasa1 = findViewById(R.id.selasa1);
        selasa2 = findViewById(R.id.selasa2);
        rabu = findViewById(R.id.rabu);
        rabu1 = findViewById(R.id.rabu1);
        rabu2 = findViewById(R.id.rabu2);
        kamis = findViewById(R.id.kamis);
        kamis1 = findViewById(R.id.kamis1);
        kamis2 = findViewById(R.id.kamis2);
        jumat = findViewById(R.id.jumat);
        jumat1 = findViewById(R.id.jumat1);
        jumat2 = findViewById(R.id.jumat2);

        btn_chat = findViewById(R.id.btn_info_psikolog_ke_chat);

        final String alamat_psi [] = {"Gedung Bintaro Bussiness Centre, Jl. RC. Veteran Raya No.1-I, RT.1/RW.3, Bintaro, Kec. Pesanggrahan, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12330",
                "Kompleks Golden Plaza Blok J-6, Jl. RS. Fatmawati Raya No.15, RT.8/RW.6, Gandaria Sel., Kec. Cilandak, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12420"};
//
//        relative_selasa.setVisibility(View.GONE);
//        relative_rabu.setVisibility(View.GONE);
//        relative_kamis.setVisibility(View.GONE);
//        relative_jumat.setVisibility(View.GONE);


        reference = FirebaseDatabase.getInstance().getReference().child("Psikolog").child(to_id);
        //refJadwal = FirebaseDatabase.getInstance().getReference().child("Psikolog").child(to_id);
//
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name_psi = dataSnapshot.child("name").getValue(String.class);
                    tv_judul.setText(name_psi);

                    if(name_psi.equals("Bayu")){
                        senin.setVisibility(View.VISIBLE);
                        senin1.setVisibility(View.VISIBLE);
                        senin2.setVisibility(View.VISIBLE);
                        rabu.setVisibility(View.VISIBLE);
                        rabu1.setVisibility(View.VISIBLE);
                        rabu2.setVisibility(View.VISIBLE);
                        jumat.setVisibility(View.VISIBLE);
                        jumat1.setVisibility(View.VISIBLE);
                        jumat2.setVisibility(View.VISIBLE);
                        alamat.setText(alamat_psi[0]);
                    }else if(name_psi.equals("Erik")){
                        senin.setVisibility(View.VISIBLE);
                        senin1.setVisibility(View.VISIBLE);
                        senin2.setVisibility(View.VISIBLE);
                        selasa.setVisibility(View.VISIBLE);
                        selasa1.setVisibility(View.VISIBLE);
                        selasa2.setVisibility(View.VISIBLE);
                        kamis.setVisibility(View.VISIBLE);
                        kamis1.setVisibility(View.VISIBLE);
                        kamis2.setVisibility(View.VISIBLE);
                        alamat.setText(alamat_psi[1]);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoPsikologActivity.this, ChatActivity.class);
                i.putExtra("PSI_ID", to_id);
                i.putExtra("PSI_NAME", psi_name);
                startActivity(i);
                InfoPsikologActivity.this.finish();
            }
        });
//        jadwal_psi = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference().child("Jadwal").child(to_id);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    for(DataSnapshot ds: dataSnapshot.getChildren()){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        //spinner1 = findViewById(R.id.spinner1);
    }
}
