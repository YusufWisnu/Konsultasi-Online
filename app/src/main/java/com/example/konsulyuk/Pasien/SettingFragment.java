package com.example.konsulyuk.Pasien;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.konsulyuk.LoginActivity;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.TestModel;
import com.example.konsulyuk.Models.User;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {

    Button logout_btn, change_pwd_btn, profil_change_pasien;
    FirebaseAuth mAuth;
    DatabaseReference reference, reference_ambil, refUser;
    CircleImageView circleImageView;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private Uri selectedphotouri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    for(DataSnapshot ds:dataSnapshot.getChildren()){
//                        //ChatMessage chatMessage = ds.getValue(ChatMessage.class);
//                        ds.child("read_count").getRef().setValue(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //User user = dataSnapshot.getValue(User.class);
                    //Uri uri = dataSnapshot.child("profilImageUri").getValue(Uri.class);
                    //Picasso.get().load(uri).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedphotouri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedphotouri);
                circleImageView.setImageBitmap(bitmap);
                uploadToFirebase();
                //circleImageView.setAlpha(0f);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void uploadToFirebase(){
        if(selectedphotouri == null){

        }

        storageReference.putFile(selectedphotouri);
        refUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   // User user  = dataSnapshot.getValue(User.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String hp = dataSnapshot.child("hp").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    String user_level = dataSnapshot.child("user_level").getValue(String.class);
                    String userid = dataSnapshot.child("userid").getValue(String.class);
                    String profilImageUri = selectedphotouri.toString();
                    User user = new User(name, email, hp, password, user_level, userid);
                    //refUser.setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logout_btn = view.findViewById(R.id.setting_btn_logout_pasien);
        change_pwd_btn = view.findViewById(R.id.setting_change_password_pasien);
        circleImageView = view.findViewById(R.id.circle_photo);
        firebaseStorage = FirebaseStorage.getInstance();
//        String filename = UUID.randomUUID().toString();
        storageReference = firebaseStorage.getReference("images/"+UUID.randomUUID().toString());

        mAuth = FirebaseAuth.getInstance();
        //reference = FirebaseDatabase.getInstance().getReference("ChatMessage/"+mAuth.getUid()+"/JnMMHZ2alhXhtuIXnBjD3eo2UKo2");
        reference = FirebaseDatabase.getInstance().getReference().child("test").push();
        reference_ambil = FirebaseDatabase.getInstance().getReference().child("test");
        refUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());


        //reference = FirebaseDatabase.getInstance().getReference("test").push();
        //reference_ambil = FirebaseDatabase.getInstance().getReference("test");

        change_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TestModel testModel = new TestModel("cupsky","A", System.currentTimeMillis() / 1000, reference.getKey());
               // Map<String, TestModel> map = new HashMap<>();
                //map.put("user_id",new TestModel("ucup","A", System.currentTimeMillis()/1000));
               // reference.setValue(testModel);
                //reference.setValue(testModel);
                reference_ambil.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                ds.child("name").getRef().setValue("WELWH");
                                Log.d("HASIL LOOPING", ds.child("name").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | (Intent.FLAG_ACTIVITY_NEW_TASK));
                startActivity(i);
            }
        });

    }
}
