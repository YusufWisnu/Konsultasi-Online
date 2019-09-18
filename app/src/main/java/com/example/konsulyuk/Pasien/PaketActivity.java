package com.example.konsulyuk.Pasien;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.konsulyuk.PagerAdapters.PaketTabPagerAdapter;
import com.example.konsulyuk.R;

public class PaketActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);

        viewPager = findViewById(R.id.paket_viewpager);
        setupWithPager(viewPager);

        tabLayout = findViewById(R.id.paket_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupWithPager(ViewPager viewPager) {
        PaketTabPagerAdapter adapter = new PaketTabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaketChatFragment(), "Chatting");
        adapter.addFragment(new PaketTelpFragment(), "Calling");
        adapter.addFragment(new PaketMeetFragment(), "Meet");
        viewPager.setAdapter(adapter);
    }
}
