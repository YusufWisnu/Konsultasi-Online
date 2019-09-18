package com.example.konsulyuk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.konsulyuk.Pasien.HomeActivity;
import com.example.konsulyuk.Psikolog.HomePsikologActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    TextView tv;
    DatabaseReference reference;
    ImageView imageView;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_image);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users/"+mAuth.getUid());
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        imageView.startAnimation(animation);


        Thread th = new Thread (){
            public void run (){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String user_lvl = dataSnapshot.child("user_level").getValue(String.class);
                            if("pasien".equals(user_lvl)){
                                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Intent intent = new Intent(SplashActivity.this, HomePsikologActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
        th.start();
    }
}
