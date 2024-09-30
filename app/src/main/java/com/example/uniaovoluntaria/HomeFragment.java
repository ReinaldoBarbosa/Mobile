package com.example.uniaovoluntaria;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uniaovoluntaria.databinding.ActivityLogin2Binding;
import com.example.uniaovoluntaria.databinding.FragmentHomeBinding;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.BreakIterator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar o botão para navegar para o próximo fragmento
        binding.btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity2.class);
            startActivity(intent);
        });

        new Thread(() -> {
            Connection conn = ConexaoSqlServer.conectar();

            try {
                if (conn != null) {
                    if (!conn.isClosed()) {
                        // Verifica se a atividade ainda está disponível
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> binding.tvInfo.setText("CONEXÃO REALIZADA COM SUCESSO"));
                        }
                    } else {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> binding.tvInfo.setText("FALHA NA CONEXÃO"));
                        }
                    }
                } else {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> binding.tvInfo.setText("CONEXÃO NULA"));
                    }
                }
            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> binding.tvInfo.setText("CONEXÃO FALHOU!!!\n" + ex.getMessage()));
                }
            }
        }).start();

    }
}