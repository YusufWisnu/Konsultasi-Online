package com.example.konsulyuk.Pasien;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konsulyuk.Adapters.ListCounselingOneAdapter;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.PagerAdapters.CounselingTabPagerAdapter;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class CounselingMainFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    ListCounselingOneAdapter adapter;
    ArrayList<ChatMessage> list;
    ArrayList<String> mDataKey;
    DatabaseReference reference, referenceUser, reference2;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView imageView;
    TextView tv_blm_ada_obrolan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_counseling_main_pasien, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar();
        recyclerView = view.findViewById(R.id.recyclerview_counseling_main);
        imageView = view.findViewById(R.id.imageview_blm_ada_obrolan);
        tv_blm_ada_obrolan = view.findViewById(R.id.tv_blm_ada_obrolan);
        list = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String id_chat = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(mAuth.getUid());
        reference2 = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = reference2.child("ChatMessage").child(mAuth.getUid()).orderByKey().limitToLast(1);

//        lastQuery.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if(dataSnapshot.exists()){
//                    Toast.makeText(getContext(), "BLOM KEBACA", Toast.LENGTH_SHORT).show();
//                    ChatMessage lm = dataSnapshot.getValue(ChatMessage.class);
//                    list.clear();
//                    list.add(lm);
//                    adapter = new ListCounselingOneAdapter(getActivity(), list);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    imageView.setVisibility(View.GONE);
                    tv_blm_ada_obrolan.setVisibility(View.GONE);
                    ChatMessage lm = dataSnapshot.getValue(ChatMessage.class);
                    list.clear();
                    list.add(lm);
                    adapter = new ListCounselingOneAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                    //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                }
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




//
//        viewPager = getView().findViewById(R.id.counseling_view_pager);
//        setupViewPager(viewPager);
//
//        tabLayout = getView().findViewById(R.id.counseling_tab_layout);
//        tabLayout.setupWithViewPager(viewPager);
    }

//    private void setupViewPager(ViewPager viewPager) {
//        CounselingTabPagerAdapter adapter = new CounselingTabPagerAdapter(getChildFragmentManager());
//        adapter.addFragment(new CounselingOneFragment(), "Ongoing");
//        adapter.addFragment(new CounselingTwoFragment(), "Finished");
//        viewPager.setAdapter(adapter);
//    }
}
