package com.example.uniaovoluntaria.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.model.EventoModel;

public class EventoDatalis extends Fragment {
    private TextView nomeEvento;

    EventoModel evento;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View rootView = inflater.inflate(R.layout.fragment_email, container, false);

        // Recupera o evento passado pelo bundle
        if (getArguments() != null) {
            evento = (EventoModel) getArguments().getSerializable("eventoModel");

            // Exibe os dados do evento (exemplo)
            nomeEvento = rootView.findViewById(R.id.textNomeEvento);
            nomeEvento.setText(evento.getNome());

            // Adicione mais detalhes do evento conforme necessário
        }

        return rootView;
    }


}
