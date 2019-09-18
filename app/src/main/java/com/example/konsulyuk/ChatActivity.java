package com.example.konsulyuk;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konsulyuk.Adapters.ListChatAdapter;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.Psikolog;
import com.example.konsulyuk.Models.User;
import com.example.konsulyuk.Pasien.HomeActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ActionBar actionBar;
    ListChatAdapter adapter;
    private ArrayList<ChatMessage> mListChat;
    private ArrayList<String> mListReadCount;
    EditText et_chat;
    TextView pasien_name, read_chat_count, tv_user_online, tv_psikolog_online, tv_name_psikolog, tv_name_pasien, tv_call_psikolog_tempo;
    FirebaseAuth mAuth;
    ImageButton btn_send;
    DatabaseReference reference, to_reference, ref, latestMessageRef, latestMessageToRef, refUser, refUser1 , refOnlineFromRow, refOnlineToRow, lmFromRef, lmToRef;
    String pathReference;
    FirebaseAuth.AuthStateListener mAuthListener;
    Call call;
    Button btn_call;
    FloatingActionButton btn_hangup_user, btn_hangup_client, btn_pick_user;
    ArrayList<String> runningActivities = new ArrayList<>();
    AlertDialog.Builder alertBuilderPsikolog, alertBuilder;
    View viewPsikolog, view1;
    AlertDialog dialogPsikolog, dialog;
    Chronometer chronometer_user, chronometer_psikolog;
    private long pauseOffset, pauseOffset1;
    MediaPlayer mediaPlayer;

    //private static String to_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        actionBar = getSupportActionBar();


        //get Psikolog ID
        Bundle bundle = getIntent().getExtras();
            final String to_id = bundle.getString("PSI_ID");    //user id juga bisa
            final String psi_name = bundle.getString("PSI_NAME");   //user nama juga bisa
            actionBar.setTitle(psi_name);

        //Toast.makeText(this, psi_name, Toast.LENGTH_SHORT).show();
        mListChat = new ArrayList<>();
        mListReadCount = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        view1 = getLayoutInflater().inflate(R.layout.dialog_call_user, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChatActivity.this, R.style.MyAlertDialog);
        alertBuilder.setView(view1);
        final AlertDialog dialog = alertBuilder.create();

        tv_name_pasien = view1.findViewById(R.id.tv_name_call_user);
        chronometer_user = view1.findViewById(R.id.timer_dialog_call_user);
        btn_send = findViewById(R.id.chat_send_button);
        et_chat = findViewById(R.id.edit_text_chat);
        tv_user_online = findViewById(R.id.tv_user_online);
        tv_psikolog_online = findViewById(R.id.tv_psikolog_online);
        btn_call = findViewById(R.id.btn_call_chat);
        btn_hangup_user = view1.findViewById(R.id.btn_hangup_user);
        tv_call_psikolog_tempo = findViewById(R.id.tv_name_call_psikolog_tempo);

        tv_call_psikolog_tempo.setText(psi_name);
        chronometer_user.setVisibility(View.INVISIBLE);

        refOnlineFromRow = FirebaseDatabase.getInstance().getReference().child("ChatOnline").child(mAuth.getUid());
        refOnlineToRow = FirebaseDatabase.getInstance().getReference().child("ChatOnline").child(to_id);
        ref = FirebaseDatabase.getInstance().getReference("ChatMessage/"+to_id+"/"+mAuth.getUid());
        lmToRef = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(to_id);
        lmFromRef = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(mAuth.getUid());

        //SINCH
        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(mAuth.getUid())
                .applicationKey("5be163b1-82e4-4722-9499-a1c10963c8e5")
                .applicationSecret("KbLQCREGBUOYAntRfivTug==")
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);

        //for incoming call
        sinchClient.startListeningOnActiveConnection();
        //

        sinchClient.start();
        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());



        //check activity is running
        Context context = ChatActivity.this;

        if (! ((Activity) context).isFinishing()) {
            refOnlineFromRow.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String status = dataSnapshot.child("status").getValue(String.class);
                        if(status.equals("offline")){
                            refOnlineFromRow.child("status").setValue("online");
                            tv_user_online.setText(status);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            refOnlineToRow.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String online = dataSnapshot.child("status").getValue(String.class);
                        tv_psikolog_online.setText(online);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //  Activity has been finished
        }

        //buat save pasien name sementara
        pasien_name = findViewById(R.id.tv_pasien_nama_temporary);

        //buat save count chat sementara
        read_chat_count = findViewById(R.id.tv_count_chat_activity);
        recyclerView = findViewById(R.id.recyclerview_chat);

        if(!to_id.equals(mAuth.getUid())){
            pathReference = "Users/"+mAuth.getUid();
        }else{
            pathReference = "Users/"+to_id;
        }
        refUser1 = FirebaseDatabase.getInstance().getReference(pathReference);
        //refUser = FirebaseDatabase.getInstance().getReference("Users/"+mAuth.getUid());


//        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               String pasien_name_1 = dataSnapshot.child("name").getValue(String.class);
//               pasien_name.setText(pasien_name_1);
//                Toast.makeText(ChatActivity.this, pasien_name.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        refUser1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pasien_name_1 = dataSnapshot.child("name").getValue(String.class);
                pasien_name.setText(pasien_name_1);
//                Toast.makeText(ChatActivity.this, pasien_name.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Listen for message
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if(chatMessage.getFromId().equals(mAuth.getUid())){
                        chatMessage.setView_type(0);
                        pasien_name.setText(chatMessage.getName_pasien());
                        if("online".equals(tv_psikolog_online.getText().toString())){
                            //lmFromRef.child("read_count").setValue(0);
                        //    Log.d("NILAI REF", dataSnapshot.child("read_count").toString());
                        }else{
                            read_chat_count.setText(String.valueOf(chatMessage.getRead_count()));
                        }
                            //Toast.makeText(ChatActivity.this, "ONLINE", Toast.LENGTH_SHORT).show();

                        mListChat.add(chatMessage);
                        adapter = new ListChatAdapter(mListChat, ChatActivity.this);
                        recyclerView.setAdapter(adapter);
                    }else{
                        chatMessage.setView_type(1);
                        pasien_name.setText(chatMessage.getName_pasien());
                        mListChat.add(chatMessage);
                        adapter = new ListChatAdapter(mListChat, ChatActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                }
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Send Button
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(et_chat.getText().toString() == ""){

                }else{
                    int convert_to_num_read = Integer.parseInt(read_chat_count.getText().toString());
                    convert_to_num_read++;
                    reference = FirebaseDatabase.getInstance().getReference("ChatMessage").child(mAuth.getUid()).child(to_id).push();
                    to_reference = FirebaseDatabase.getInstance().getReference("ChatMessage").child(to_id).child(mAuth.getUid()).push();
                    ChatMessage chatMessage = new ChatMessage(to_id,mAuth.getUid(),reference.getKey(),et_chat.getText().toString(),psi_name, pasien_name.getText().toString(),System.currentTimeMillis()/1000,convert_to_num_read ,0);
                    ChatMessage chatMessage1 = new ChatMessage(to_id,mAuth.getUid(),reference.getKey(), et_chat.getText().toString(), psi_name,pasien_name.getText().toString(), System.currentTimeMillis()/1000,0 ,0);
                    //mListChat.add(new ChatMessage(to_id,mAuth.getUid(),"aa",et_chat.getText().toString(),psi_name, "Cupsky",System.currentTimeMillis()/1000,0));

                    reference.setValue(chatMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            et_chat.getText().clear();
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                            //close keyboard after enter the button
                            InputMethodManager imm = (InputMethodManager) getSystemService(ChatActivity.this.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    });
                    to_reference.setValue(chatMessage);
                    latestMessageRef = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(mAuth.getUid()).child(to_id);
                    latestMessageToRef = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(to_id).child(mAuth.getUid());
                    latestMessageRef.setValue(chatMessage);
                    latestMessageToRef.setValue(chatMessage1);
                }
            }
        });

        //btn buat panggil
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                tv_name_pasien.setText(psi_name);
                if(call == null){
                    call = sinchClient.getCallClient().callUser(to_id);
                    call.addCallListener(new SinchCallListener());
//                }else{
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            btn_hangup_user.performClick();
//                        }
//                    }, 0);
//                    call.hangup();
//                    call = null;
//                    onBackPressed();
                }
            }
        });

        btn_hangup_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBuilderPsikolog = new AlertDialog.Builder(ChatActivity.this, R.style.MyAlertDialog);
                viewPsikolog = getLayoutInflater().inflate(R.layout.dialog_call_psikolog, null);
                alertBuilderPsikolog.setView(viewPsikolog);
                dialogPsikolog = alertBuilderPsikolog.create();
                if(call != null){
                    call.hangup();
                    call = null;
                }
                dialog.dismiss();

                if(dialogPsikolog != null){
                    ChatActivity.this.dialogPsikolog.dismiss();
                }
            }
        });

//        btn_hangup_client.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogPsikolog.dismiss();
//            }
//        });

//        btn_pick_user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ChatActivity.this, "AKU DI CLICK", Toast.LENGTH_SHORT).show();
//                dialogPsikolog.show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_call.performClick();
            }
        }, 0);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //refOnlineFromRow.child("status").setValue("offline");
    }

    private class SinchCallListener implements CallListener{

        @Override
        public void onCallProgressing(Call progressingCall) {

        }

        @Override
        public void onCallEstablished(final Call establishedCall) {
            viewPsikolog = getLayoutInflater().inflate(R.layout.dialog_call_psikolog, null);
            chronometer_user.setVisibility(View.VISIBLE);
            chronometer_user.start();
            btn_hangup_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(chronometer_psikolog != null){
                        chronometer_psikolog.stop();
                    }

                   if(chronometer_user != null){
                        chronometer_user.stop();
                    }

                    if(dialogPsikolog != null){
                        ChatActivity.this.dialogPsikolog.dismiss();
                    }
                    if(dialog != null){
                        ChatActivity.this.dialog.dismiss();
                    }
                    establishedCall.hangup();
                }
            });
        }

        @Override
        public void onCallEnded(Call endedCall) {
            //endedCall = null;
            if(dialog != null){
                ChatActivity.this.dialog.dismiss();
            }
            if(dialogPsikolog != null){
                ChatActivity.this.dialogPsikolog.dismiss();
            }
            endedCall.hangup();

        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    private  class SinchCallClientListener implements CallClientListener{

        @Override
        public void onIncomingCall(CallClient callClient, final Call incomingCall) {
            mediaPlayer = MediaPlayer.create(ChatActivity.this, R.raw.susumurni_nasional_fix);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            alertBuilderPsikolog = new AlertDialog.Builder(ChatActivity.this, R.style.MyAlertDialog);
            viewPsikolog = getLayoutInflater().inflate(R.layout.dialog_call_psikolog, null);
            btn_hangup_client =viewPsikolog.findViewById(R.id.btn_hangup_psikolog);
            tv_name_psikolog = viewPsikolog.findViewById(R.id.tv_name_call_psikolog);
            chronometer_psikolog = viewPsikolog.findViewById(R.id.timer_dialog_call_psikolog);
            chronometer_psikolog.setVisibility(View.INVISIBLE);
            tv_name_psikolog.setText(pasien_name.getText().toString());
            btn_pick_user = viewPsikolog.findViewById(R.id.btn_pick_psikolog);
            alertBuilderPsikolog.setView(viewPsikolog);
            dialogPsikolog = alertBuilderPsikolog.create();
            dialogPsikolog.show();
                    btn_pick_user.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mediaPlayer.stop();
                            incomingCall.answer();
                            incomingCall.addCallListener(new SinchCallListener());
                            //dialogPsikolog.dismiss();
                            btn_pick_user.setVisibility(View.INVISIBLE);
                            btn_hangup_client.setX(100f);
                            chronometer_psikolog.setVisibility(View.VISIBLE);
                            chronometer_psikolog.start();

                        }
                    });

            btn_hangup_client.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chronometer_user != null){
                        chronometer_user.stop();
                    }
                    mediaPlayer.stop();
                    incomingCall.hangup();
                    incomingCall.addCallListener(new SinchCallListener());
                    if(dialog != null){
                        ChatActivity.this.dialog.dismiss();

                    }
                    if(dialogPsikolog != null){
                        ChatActivity.this.dialogPsikolog.dismiss();

                    }
                }
            });
        }
    }
}


