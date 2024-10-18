package com.example.uniaovoluntaria.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    // Cache para armazenar os fragmentos já criados
    private Map<String, Fragment> fragmentCache = new HashMap<>();
    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        // Inicializa o mapa de fragmentos
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.navHome, new HomeFragment());
        fragmentMap.put(R.id.navSearch, new EventoDatalis());
        fragmentMap.put(R.id.navProfile, new ProfileFragment());
        fragmentMap.put(R.id.navEmail, new EventoDatalis());

        // Definir o HomeFragment como a tela inicial
        if (savedInstanceState == null) {
            loadFragment(fragmentMap.get(R.id.navHome));
            bottomNavigationView.setSelectedItemId(R.id.navHome);  // Define o item da barra como selecionado
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = fragmentMap.get(item.getItemId());
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;  // Evita vazamento de memória
    }

    private void loadFragment(Fragment fragment) {
        String fragmentTag = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Verifica se o fragmento já está no cache
        Fragment cachedFragment = fragmentCache.get(fragmentTag);
        if (cachedFragment != null) {
            // Se o fragmento já existe, só precisamos exibi-lo
            fragmentTransaction.replace(R.id.frameLayout, cachedFragment);
        } else {
            // Se o fragmento ainda não foi criado, criamos e adicionamos ao cache
            fragmentTransaction.replace(R.id.frameLayout, fragment, fragmentTag);
            fragmentCache.put(fragmentTag, fragment);
        }

        fragmentTransaction.commit();
    }
}
