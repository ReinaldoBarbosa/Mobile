package com.example.uniaovoluntaria.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.Auxiliares;
import com.example.uniaovoluntaria.dao.EventoDao;
import com.example.uniaovoluntaria.model.EventoModel;

import java.sql.SQLException;

public class ActivityEvento extends AppCompatActivity {

    // Definindo os campos da interface
    EditText txtNome, txtCep, txtVaga, txtData, txtInfos;
    TextView lbStattus;
    Button btnSalvar, btnExcluir;

    EventoModel editar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);


        txtNome = findViewById(R.id.txtActivityEditarNome);
        txtCep = findViewById(R.id.txtActivityEditarCep);
        txtVaga = findViewById(R.id.txtActivityEditarVagas);
        txtData = findViewById(R.id.txtActivityEditarData);
        txtInfos = findViewById(R.id.txtActivityEditarInfos);

        lbStattus = findViewById(R.id.lbActivityEventoStatus);

        btnSalvar = findViewById(R.id.btnActivityEditarSalvar2);
        btnExcluir = findViewById(R.id.btnActivityEditarExcluir);


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    salvar();
                    Auxiliares.alert(getApplicationContext(), "Evento criado!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });
    }

    private void salvar() throws SQLException {
        EventoModel e = new EventoModel();
        e.setNome(txtNome.getText().toString());
        e.setCep(txtCep.getText().toString());
        e.setVagas(9); // Pode ajustar para pegar dinamicamente
        e.setInfos(txtInfos.getText().toString());
        e.setOng(4); // Assumindo que 4 é o ID da ONG
        e.setStatusEvento("ATIVO");

        e.setDataEvento("11/12/2024");

        e.setHoraInicio("11:30");
        e.setNumero(1);


        EventoDao dao = new EventoDao();
        dao.cadastrar(e);


        Intent intent = new Intent(ActivityEvento.this, Eventos.class);
        startActivity(intent);
    }


    private void excluir() {

        Auxiliares.alert(getApplicationContext(), "Função excluir ainda não implementada!");
    }


}
