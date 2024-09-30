package com.example.uniaovoluntaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.uniaovoluntaria.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class      MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        // Definir o HomeFragment como a tela inicial
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.navHome);  // Define o item da barra como selecionado
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navHome){
                    loadFragment(new HomeFragment());
                } else if (itemId == R.id.navSearch) {
                    loadFragment(new SearchFragment());
                } else if (itemId == R.id.navNotification) {
                    loadFragment(new NotificationFragment());
                } else if (itemId == R.id.navEmail) {
                    loadFragment(new EmailFragment());
                }
                else {
                    loadFragment(new ProfileFragment());
                }


                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;  // Evita vazamento de memória
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment existingFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (existingFragment != null) {
            fragmentTransaction.replace(R.id.frameLayout, existingFragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment, fragment.getClass().getName());
        }

        fragmentTransaction.commit();
    }
    

}