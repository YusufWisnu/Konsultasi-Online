package com.example.konsulyuk.Psikolog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.konsulyuk.LoginActivity;
import com.example.konsulyuk.Pasien.CounselingMainFragment;
import com.example.konsulyuk.Pasien.HomeFragment;
import com.example.konsulyuk.Pasien.SettingFragment;
import com.example.konsulyuk.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomePsikologActivity extends AppCompatActivity {

    Button logout_btn;
    Fragment fragment;
    FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home_psikolog:
                    fragment = new HomePsikologFragment();
                    break;
                case R.id.navigation_konseling_psikolog_chat:
                    fragment = new ChatPsikologFragment();
                    break;
                case R.id.navigation_setting_psikolog:
                    fragment = new SettingPsikologFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_psikolog);

        mAuth = FirebaseAuth.getInstance();
        verifyUserIsLogin();
        BottomNavigationView navView = findViewById(R.id.nav_view_psikolog);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomePsikologFragment());
        //logout_btn = findViewById(R.id.btn_logout_psikolog);
//        logout_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent i = new Intent(HomePsikologActivity.this, LoginActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | (Intent.FLAG_ACTIVITY_NEW_TASK));
//                startActivity(i);
//            }
//        });
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_framelayout_psikolog, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    private void verifyUserIsLogin() {
        String uid = mAuth.getUid();
        if(uid == null){
            Intent i = new Intent(HomePsikologActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | (Intent.FLAG_ACTIVITY_NEW_TASK));
            startActivity(i);
        }
    }
}
