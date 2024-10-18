package com.example.uniaovoluntaria.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.ListaEventoAdapter;
import com.example.uniaovoluntaria.dao.EventoDao;
import com.example.uniaovoluntaria.model.EventoModel;

import java.util.List;

public class EventosFragment extends Fragment {

    private ProgressBar loading;
    private RecyclerView listEvento;
    private ListaEventoAdapter eventoAdapter;

    private List<EventoModel> listaEventos;

    private ImageView voltar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventosl, container, false);

        // Inicializa a ProgressBar e o RecyclerView
        loading = rootView.findViewById(R.id.CarregaEvento);
        listEvento = rootView.findViewById(R.id.listEvento);
        voltar = rootView.findViewById(R.id.btnVoltar);

        // Define o LayoutManager para eventos (vertical)
        listEvento.setLayoutManager(new LinearLayoutManager(getContext()));

        // Carrega os eventos
        carregarEventos("");

        // Configura o botão de voltar para remover o fragmento atual e voltar para o anterior
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Volta ao fragmento anterior no stack de fragmentos
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    private void carregarEventos(String busca) {
        loading.setVisibility(View.VISIBLE);
        listEvento.setVisibility(View.GONE); // Oculta o RecyclerView até que os eventos sejam carregados

        EventoDao eventoDao = new EventoDao();
        listaEventos = eventoDao.getALl();

        if (listaEventos != null && !listaEventos.isEmpty()) {
            eventoAdapter = new ListaEventoAdapter(this, listaEventos);
            listEvento.setAdapter(eventoAdapter);
            listEvento.setVisibility(View.VISIBLE);
        } else {
            listEvento.setVisibility(View.GONE);
            // Aqui você pode adicionar uma mensagem de que não há eventos disponíveis
        }

        loading.setVisibility(View.GONE);
    }
}
