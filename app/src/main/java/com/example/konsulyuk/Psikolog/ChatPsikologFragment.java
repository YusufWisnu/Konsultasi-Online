package com.example.konsulyuk.Psikolog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.konsulyuk.Adapters.ListChatPsikologOngoingAdapter;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.PagerAdapters.ChatPsikologTabPagerAdapter;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatPsikologFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    ArrayList<ChatMessage> mList;
    DatabaseReference reference;
    ListChatPsikologOngoingAdapter adapter;
    FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_psikolog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mList.clear();
                if(dataSnapshot.exists()){
                    //int count_chat_psi = 0;
                    ChatMessage cm = dataSnapshot.getValue(ChatMessage.class);
                    mList.add(cm);
                    adapter = new ListChatPsikologOngoingAdapter(getActivity(), mList);
                    recyclerView.setAdapter(adapter);
                   // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_chat_psikolog_2);
        mList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("LatestMessage").child(mAuth.getUid());
//        viewPager = view.findViewById(R.id.chat_psikolog_view_pager);
//        tabLayout = view.findViewById(R.id.chat_psikolog_tab_layout);
    }

//    private void setupViewPager(ViewPager viewPager){
//        ChatPsikologTabPagerAdapter adapter = new ChatPsikologTabPagerAdapter(getChildFragmentManager());
//        adapter.addFragment(new ChatPsikologOngoingFragment(),"Ongoing");
//        adapter.addFragment(new ChatPsikologFinishFragment(), "Finish");
//        viewPager.setAdapter(adapter);
//
//    }
}
