package com.example.uniaovoluntaria.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.ListaOngAdapter;
import com.example.uniaovoluntaria.dao.OngDao;
import com.example.uniaovoluntaria.model.UsuarioModel;

import java.util.List;
import java.util.concurrent.Executors;

public class OngsFragment extends Fragment {

    private ProgressBar loading;
    private RecyclerView listOng;
    private ListaOngAdapter ongAdapter;

    private List<UsuarioModel> listaOngs;
    private ImageView voltar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ongs, container, false);

        // Inicializa a ProgressBar e o RecyclerView
        loading = rootView.findViewById(R.id.CarregaOng);
        listOng = rootView.findViewById(R.id.listOngs);
        voltar = rootView.findViewById(R.id.btnVoltar);

        // Define o LayoutManager para eventos (vertical)
        listOng.setLayoutManager(new LinearLayoutManager(getContext()));

        // Carrega as ONGs
        carregarOngs();

        // Configura o botão de voltar para remover o fragmento atual e voltar para o anterior
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    private void carregarOngs() {
        loading.setVisibility(View.VISIBLE);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                OngDao ongDao = new OngDao();
                try {
                    listaOngs = ongDao.getALl();
                } catch (Exception e) {
                    // Handle exceptions (e.g., log the error)
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Erro ao carregar ONGs", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    });
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listaOngs != null && !listaOngs.isEmpty()) {
                            ongAdapter = new ListaOngAdapter(OngsFragment.this, listaOngs);
                            listOng.setAdapter(ongAdapter);
                            listOng.setVisibility(View.VISIBLE);
                        } else {
                            listOng.setVisibility(View.GONE);
                            // Aqui você pode adicionar uma mensagem de que não há ONGs disponíveis
                        }

                        loading.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
