package com.example.uniaovoluntaria.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.EventoAdapter;

import java.util.List;

public class Candidatura extends AppCompatActivity {

    TextView txtPesquisa, lbtext;
    Button btnPesquisar, btnNovo;
    ListView listView;
    EventoAdapter adapter;
    List<Candidatura> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatura);
    }
}