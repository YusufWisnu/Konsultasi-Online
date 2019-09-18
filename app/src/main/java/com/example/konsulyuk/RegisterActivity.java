package com.example.konsulyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.service.autofill.RegexValidator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.konsulyuk.Models.Saldo;
import com.example.konsulyuk.Models.User;
import com.example.konsulyuk.Pasien.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText ed_name, ed_email, ed_hp, ed_password, ed_conf_password;
    Button btn_register;
    FirebaseAuth auth;
    DatabaseReference reference, refSaldo;
    ProgressDialog progressDialog;
    Saldo saldo;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])" +
                                                                     "(?=.*[A-Z])" +
                                                                     "(?=.*[0-9])" +
                                                                        ".{8,}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ed_name = findViewById(R.id.et_name);
        ed_email = findViewById(R.id.et_email);
        ed_hp = findViewById(R.id.et_hp);
        ed_password = findViewById(R.id.et_password);
        ed_conf_password = findViewById(R.id.et_conf_password);
        btn_register = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child(("Users"));
        refSaldo = FirebaseDatabase.getInstance().getReference().child("Saldo");
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = ed_name.getText().toString().trim();
                final String email = ed_email.getText().toString().trim();
                final String hp = ed_hp.getText().toString().trim();
                final String password = ed_password.getText().toString().trim();
                final String conf_password = ed_conf_password.getText().toString().trim();
                boolean valid = PASSWORD_PATTERN.matcher(password).matches();
                Log.d("password", "onClick: "+password);
                Log.d("Conf_password", "onClick: "+conf_password);
                if(name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill the name", Toast.LENGTH_LONG).show();
                }else if(email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill the email", Toast.LENGTH_LONG).show();
                }else if(hp.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill the phone number", Toast.LENGTH_LONG).show();
                }else if(password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill the password", Toast.LENGTH_LONG).show();
                }else if(password.length() < 8 ){
                    Toast.makeText(RegisterActivity.this, "Password must contains at least 8 character", Toast.LENGTH_LONG).show();
                }else if(!valid){
                    Toast.makeText(RegisterActivity.this, "Password not valid", Toast.LENGTH_LONG).show();
                }else if(!conf_password.equals(password)){
                    Toast.makeText(RegisterActivity.this, "Password must be same with confirmation password", Toast.LENGTH_LONG).show();
                }else{
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        progressDialog = new ProgressDialog(RegisterActivity.this);
                                        progressDialog.setTitle("Konsul Yuk");
                                        progressDialog.setMessage("Loading...");
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        progressDialog.show();
                                        progressDialog.setCancelable(false);
                                        final FirebaseUser firebaseUser = auth.getCurrentUser();
                                        User user = new User(name, email, hp, password, "pasien", firebaseUser.getUid());
                                        saldo = new Saldo(0, name, firebaseUser.getUid());
                                        refSaldo.child(firebaseUser.getUid()).setValue(saldo);
                                        reference.child(firebaseUser.getUid()).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            progressDialog.dismiss();
                                                            finish();
                                                            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                                            i.putExtra("id_user",firebaseUser.getUid());
                                                            startActivity(i);
                                                        }else{
                                                            progressDialog.dismiss();
                                                            Toast.makeText(RegisterActivity.this, "User could not be registered", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Email already exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}
