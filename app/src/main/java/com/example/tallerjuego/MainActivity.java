package com.example.tallerjuego;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnvMenu;
    private FrameLayout contenedorPrincipal;
    private PerfilFragment perfilFragment;
    private PrincipalFragment principalFragment;
    private RankingFragment rankingFragment;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        perfilFragment = new PerfilFragment();
        principalFragment = new PrincipalFragment();
        rankingFragment = new RankingFragment();
        contenedorPrincipal = findViewById(R.id.main_frame);
        bnvMenu = findViewById(R.id.main_mav);
        user = getIntent().getStringExtra("Usuario");
        final Bundle bundle = new Bundle();
        bundle.putString("user",user);
        perfilFragment.setArguments(bundle);
        setFragment(perfilFragment);
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_perfil:
                        perfilFragment.setArguments(bundle);
                        setFragment(perfilFragment);
                        return true;
                    case R.id.nav_home:
                        principalFragment.setArguments(bundle);
                        setFragment(principalFragment);
                        return true;
                    case R.id.nav_ranking:
                        rankingFragment.setArguments(bundle);
                        setFragment(rankingFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager() getChildFragmentManager()
//                .beginTransaction();
//        fragmentTransaction.replace(R.id.main_frame, fragment).commit();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
