package com.example.konsulyuk.Pasien;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.konsulyuk.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.manojbhadane.QButton;

import java.util.ArrayList;

public class HasilPenilaianActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    PieChart pieChart;
    TextView tv_hasil;
    QButton qb_detail, qb_close, qb_close_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_penilaian);
        mAuth = FirebaseAuth.getInstance();

        final AlertDialog.Builder builder = new AlertDialog.Builder(HasilPenilaianActivity.this);
        LayoutInflater inflater  = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_hasil_penilaian, null);
        builder.setView(dialogView);
        final AlertDialog builderNew = builder.create();

        reference = FirebaseDatabase.getInstance().getReference("Disc/"+mAuth.getUid());
        tv_hasil = findViewById(R.id.tv_chart_Penilaian);
        qb_detail = findViewById(R.id.btn_chart_detail);
        qb_close = findViewById(R.id.btn_chart_close);
        qb_close_dialog = dialogView.findViewById(R.id.btn_close_dialog);
        Animation anim = AnimationUtils.loadAnimation(HasilPenilaianActivity.this, R.anim.fadein);
        tv_hasil.startAnimation(anim);
        qb_detail.startAnimation(anim);
        qb_close.startAnimation(anim);

        //PIE CHART
        pieChart = findViewById(R.id.pie_chart);
        pieChart.startAnimation(anim);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10, 10,15, 20);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(10f);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int dom = dataSnapshot.child("d").getValue(int.class);
                    int inf = dataSnapshot.child("i").getValue(int.class);
                    int ste = dataSnapshot.child("s").getValue(int.class);
                    int com = dataSnapshot.child("c").getValue(int.class);

                    ArrayList<PieEntry> yValues = new ArrayList<>();


                    yValues.add(new PieEntry(dom, "Dominance"));
                    yValues.add(new PieEntry(inf, "Influence"));
                    yValues.add(new PieEntry(ste, "Steadiness"));
                    yValues.add(new PieEntry(com, "Compliance"));

                    Legend legend =  pieChart.getLegend();
                    legend.setTextSize(15f);
                    legend.setYOffset(50f);

                    PieDataSet dataSet = new PieDataSet(yValues, "");
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(5f);
                    dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(15f);
                    data.setValueTextColor(Color.BLUE);

                    pieChart.setData(data);
                    pieChart.invalidate();

                    if(dom > inf && dom > ste && dom > com){
                        tv_hasil.setText("You Are Dominance");
                    }else if(inf > dom && inf > ste && inf > com){
                        tv_hasil.setText("You Are Influence");
                    }else if(ste > dom && ste > inf && ste > com){
                        tv_hasil.setText("You Are Steadiness");
                    }else if(com > dom && com > inf && com > ste){
                        tv_hasil.setText("You Are Compliance");
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        qb_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        qb_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builderNew.show();

            }
        });

        qb_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderNew.dismiss();
            }
        });



    }
}
