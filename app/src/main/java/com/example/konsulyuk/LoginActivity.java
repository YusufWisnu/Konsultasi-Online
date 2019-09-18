package com.example.konsulyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.konsulyuk.Models.Psikolog;
import com.example.konsulyuk.Models.User;
import com.example.konsulyuk.Pasien.HomeActivity;
import com.example.konsulyuk.Psikolog.HomePsikologActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btn_lgn;
    EditText ed_email, ed_password;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference refUser, refPsikolog;
    List<User> list_user;
    List<Psikolog> list_psikolog;
    String email_msk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        btn_lgn = findViewById(R.id.btn_login);
        ed_email = findViewById(R.id.et_email);
        ed_password = findViewById(R.id.et_password);
        btn_lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = ed_email.getText().toString().trim();
                String password = ed_password.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Fill the Email", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Fill the Password", Toast.LENGTH_SHORT).show();
                }else{
                   final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Konsul Yuk","Please Wait");
                    progressDialog.setCancelable(true);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //loadUser();
                                        progressDialog.dismiss();
                                        user = mAuth.getCurrentUser();//masih uji coba
                                        refPsikolog = FirebaseDatabase.getInstance().getReference().child("Psikolog/"+mAuth.getUid());
                                        progressDialog.dismiss();
                                        refPsikolog.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String psi_lvl = dataSnapshot.child("user_level").getValue(String.class);
                                                if("psikolog".equals(psi_lvl)){
                                                    finish();
                                                    startActivity(new Intent(LoginActivity.this, HomePsikologActivity.class));
                                                }else{
                                                    Log.d("DARI PSIKOLOG", "DATA GA KEBACA");
                                                    finish();
                                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        //finish();
                                       // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "The User not Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void register(View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void loadUser(){
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    User user_msk = ds.getValue(User.class);
                    list_user.add(user_msk);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refPsikolog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Psikolog psi_msk = ds.getValue(Psikolog.class);
                    list_psikolog.add(psi_msk);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
