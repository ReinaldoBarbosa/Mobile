package com.example.uniaovoluntaria.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.Util;
import com.example.uniaovoluntaria.api.Auxiliares;
import com.example.uniaovoluntaria.api.EventoAdapter;
import com.example.uniaovoluntaria.api.OngAdapter;
import com.example.uniaovoluntaria.dao.EventoDao;
import com.example.uniaovoluntaria.dao.OngDao;
import com.example.uniaovoluntaria.dao.UsuarioDao;
import com.example.uniaovoluntaria.model.EventoModel;
import com.example.uniaovoluntaria.model.UsuarioModel;

import java.util.List;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private ProgressBar loading;
    private ProgressBar carregaOng;
    private RecyclerView listEvento;
    private RecyclerView listOng;
    private EventoAdapter eventoAdapter;
    private OngAdapter ongAdapter;
    private List<EventoModel> listaEventos;
    private List<UsuarioModel> listaOngs;

    private ImageView ftPerfil;

    private Button btnOng, btnEvento;

    UsuarioDao usuarioDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializa a ProgressBar e os RecyclerViews
        loading = rootView.findViewById(R.id.CerregaEvento);
        listEvento = rootView.findViewById(R.id.listEvento);
        carregaOng = rootView.findViewById(R.id.CarregaOng);
        listOng = rootView.findViewById(R.id.listOng);
        ftPerfil = rootView.findViewById(R.id.FtPerfil);

        // Define o LayoutManager para eventos (vertical)
        listEvento.setLayoutManager(new LinearLayoutManager(getContext()));

        // Define o LayoutManager para ONGs (horizontal)
        listOng.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Inicializa os botões
        btnEvento = rootView.findViewById(R.id.btnHomeEvento);
        btnOng = rootView.findViewById(R.id.btnHomeOng);

        // Chama os métodos para preencher as listas
        carregarOngs();
        carregarEventos("");

        // Configura o clique do botão de eventos para trocar o fragmento
        btnEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Troca para o EventosFragment diretamente, sem usar Intent
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new EventosFragment()); // Substitua pelo seu frame correto
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Troca para o EventosFragment diretamente, sem usar Intent
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new OngsFragment()); // Substitua pelo seu frame correto
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    private void carregaDadosUsuario() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // Obtém o ID do usuário

        if (userId != -1) {
            UsuarioModel usuario = usuarioDao.carregaPorId(userId); // Carrega o usuário com base no ID
            if (usuario != null) {
                mostrar(usuario); // Atualiza a UI com os dados do usuário
            } else {
                Auxiliares.alert(getContext(), "Usuário não encontrado");
            }
        } else {
            Auxiliares.alert(getContext(), "ID do usuário não encontrado");
        }
    }

    private void mostrar(UsuarioModel usuario) {
        if (usuario != null) {
            if (usuario.getFotoPerfil() != null) {
                // Converte o array de bytes para Bitmap e define no ImageView
                ftPerfil.setImageBitmap(Util.converterByteToBipmap(usuario.getFotoPerfil()));
            } else {
                // Se não houver foto de perfil, define uma imagem padrão
                ftPerfil.setImageResource(R.drawable.wave); // Imagem padrão
            }
        }
    }


    private void carregarOngs() {
        carregaOng.setVisibility(View.VISIBLE);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                OngDao ongDao = new OngDao();
                listaOngs = ongDao.getALl();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listaOngs != null && !listaOngs.isEmpty()) {
                            ongAdapter = new OngAdapter(HomeFragment.this, listaOngs);
                            listOng.setAdapter(ongAdapter);
                            listOng.setVisibility(View.VISIBLE);
                        } else {
                            listOng.setVisibility(View.GONE);
                            // Aqui você pode adicionar uma mensagem de que não há ONGs disponíveis
                        }

                        carregaOng.setVisibility(View.GONE);
                    }
                });
            }
        });
    }



    private void carregarEventos(String busca) {
        loading.setVisibility(View.VISIBLE);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                EventoDao eventoDao = new EventoDao();
                listaEventos = eventoDao.getALl();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listaEventos != null && !listaEventos.isEmpty()) {
                            eventoAdapter = new EventoAdapter(HomeFragment.this, listaEventos);
                            listEvento.setAdapter(eventoAdapter);
                            listEvento.setVisibility(View.VISIBLE);
                        } else {
                            listEvento.setVisibility(View.GONE);
                            // Aqui você pode adicionar uma mensagem de que não há eventos disponíveis
                        }

                        loading.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}