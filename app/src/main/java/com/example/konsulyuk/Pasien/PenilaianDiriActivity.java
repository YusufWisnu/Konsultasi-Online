package com.example.konsulyuk.Pasien;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.konsulyuk.Models.PasienDISC;
import com.example.konsulyuk.Models.StatementKuesioner;
import com.example.konsulyuk.R;
import com.example.myloadingbutton.MyLoadingButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manojbhadane.QButton;
import com.varunest.sparkbutton.SparkButton;

public class PenilaianDiriActivity extends AppCompatActivity {

    QButton btn_next, qb_1, qb_2, qb_3, qb_4;
    SparkButton sparkButton;
    StatementKuesioner sk;
    private int StatementChoice= 0; //buat urutan soal
    private int d = 0;
    private int i = 0;
    private int s = 0;
    private int c = 0;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian_diri);
        sk = new StatementKuesioner();
        qb_1 = findViewById(R.id.qb1);
        qb_2 = findViewById(R.id.qb2);
        qb_3 = findViewById(R.id.qb3);
        qb_4 = findViewById(R.id.qb4);
        btn_next = findViewById(R.id.btn_next);
        sparkButton = findViewById(R.id.spark_button);
        sparkButton.setVisibility(View.GONE);
        final Animation anim = AnimationUtils.loadAnimation(PenilaianDiriActivity.this, R.anim.fadein);
        qb_1.startAnimation(anim);
        qb_2.startAnimation(anim);
        qb_3.startAnimation(anim);
        qb_4.startAnimation(anim);
        updateStatement();

        //final ViewGroup.LayoutParams params = mlb.getLayoutParams();
        //Animation mAnimation = AnimationUtils.loadAnimation(PenilaianDiriActivity.this, R.anim.anim_translate);

        // D
        qb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qb_1.setVisibility(View.INVISIBLE);
                qb_2.setVisibility(View.INVISIBLE);
                qb_3.setVisibility(View.INVISIBLE);
                qb_4.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_next.startAnimation(anim);
                qb_1.clearAnimation();
                qb_2.clearAnimation();
                qb_3.clearAnimation();
                qb_4.clearAnimation();
                sparkButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sparkButton.performClick();
                    }
                },0);
                d++;
                if(StatementChoice == 10){
                    btn_next.setText("Finish");
                    btn_next.setBackgroundColor(getResources().getColor(R.color.warna_hijau));
                    StatementChoice = 11;
                }else{
                    updateStatement();
                }
            }
        });

        // I
        qb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qb_1.setVisibility(View.INVISIBLE);
                qb_2.setVisibility(View.INVISIBLE);
                qb_3.setVisibility(View.INVISIBLE);
                qb_4.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_next.startAnimation(anim);
                qb_1.clearAnimation();
                qb_2.clearAnimation();
                qb_3.clearAnimation();
                qb_4.clearAnimation();
                sparkButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sparkButton.performClick();
                    }
                },0);
                i++;
                if(StatementChoice == 10){
                    btn_next.setText("Finish");
                    btn_next.setBackgroundColor(getResources().getColor(R.color.warna_hijau));
                    StatementChoice = 11;
                }else{
                    updateStatement();
                }
            }
        });

        // S
        qb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qb_1.setVisibility(View.INVISIBLE);
                qb_2.setVisibility(View.INVISIBLE);
                qb_3.setVisibility(View.INVISIBLE);
                qb_4.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_next.startAnimation(anim);
                qb_1.clearAnimation();
                qb_2.clearAnimation();
                qb_3.clearAnimation();
                qb_4.clearAnimation();
                sparkButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sparkButton.performClick();
                    }
                },0);
                s++;
                if(StatementChoice == 10){
                    btn_next.setText("Finish");
                    btn_next.setBackgroundColor(getResources().getColor(R.color.warna_hijau));
                    StatementChoice = 11;
                }else{
                    updateStatement();
                }
            }
        });

        // C
        qb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qb_1.setVisibility(View.INVISIBLE);
                qb_2.setVisibility(View.INVISIBLE);
                qb_3.setVisibility(View.INVISIBLE);
                qb_4.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_next.startAnimation(anim);
                qb_1.clearAnimation();
                qb_2.clearAnimation();
                qb_3.clearAnimation();
                qb_4.clearAnimation();
                sparkButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sparkButton.performClick();
                    }
                },0);
                c++;

                if(StatementChoice == 10){
                    btn_next.setText("Finish");
                    btn_next.setBackgroundColor(getResources().getColor(R.color.warna_hijau));
                    StatementChoice = 11;
                }else{
                    updateStatement();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StatementChoice == 11){
                    //StatementChoice = 0;
                    mAuth = FirebaseAuth.getInstance();
                    reference = FirebaseDatabase.getInstance().getReference("Disc").child(mAuth.getUid());
                    PasienDISC disc = new PasienDISC(d,i,s,c);
                    reference.setValue(disc);
                    Intent intent = new Intent(PenilaianDiriActivity.this, HasilPenilaianActivity.class);
                    finish();
                    startActivity(intent);
                }else{
                    qb_1.setVisibility(View.VISIBLE);
                    qb_2.setVisibility(View.VISIBLE);
                    qb_3.setVisibility(View.VISIBLE);
                    qb_4.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.INVISIBLE);
                    btn_next.clearAnimation();
                    qb_1.startAnimation(anim);
                    qb_2.startAnimation(anim);
                    qb_3.startAnimation(anim);
                    qb_4.startAnimation(anim);
                    sparkButton.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkButton.performClick();
                        }
                    },0);
                }
            }
        });
    }

    private void updateStatement() {
            qb_1.setText(sk.getChoice(StatementChoice, 1));
            qb_2.setText(sk.getChoice(StatementChoice, 2));
            qb_3.setText(sk.getChoice(StatementChoice, 3));
            qb_4.setText(sk.getChoice(StatementChoice, 4));
        StatementChoice++;

    }
}
