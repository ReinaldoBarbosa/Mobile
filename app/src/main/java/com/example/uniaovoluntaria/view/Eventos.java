package com.example.uniaovoluntaria.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.EventoAdapter;
import com.example.uniaovoluntaria.dao.EventoDao;
import com.example.uniaovoluntaria.model.EventoModel;

import java.util.List;

public class Eventos extends AppCompatActivity {

    TextView txtPesquisa, lbtext;
    Button btnPesquisar, btnNovo;
    ListView listView;
    EventoAdapter adapter;
    List<EventoModel> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtPesquisa = findViewById(R.id.txtConsultaEventoPesquisa);
        btnPesquisar = findViewById(R.id.btnConsultaEvento);
        btnNovo = findViewById(R.id.btnConsultaEventoNovo);
        listView = findViewById(R.id.listViewConsultaEvento);
        lbtext = findViewById(R.id.lbtext);


        preenche("");

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preenche(txtPesquisa.getText().toString());
            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventos.this, ActivityEvento.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Eventos.this, Editar.class);
                EventoModel e = (EventoModel) adapterView.getItemAtPosition(i);
                intent.putExtra("evento",e.getId());
                startActivity(intent);
            }
        });
    }

    private void preenche(String busca) {
        EventoDao dao = new EventoDao();

    }



    }



