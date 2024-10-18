package com.example.uniaovoluntaria.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.ConexaoSqlServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    Button btnInserirUsuario;
    EditText edtNome, edtEmail, edtSenha, edtDate, edtCpf, edtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializaComponentes();

        btnInserirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    inserirUsuario();
                }
            }
        });
    }

    private void inicializaComponentes() {
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtDate = findViewById(R.id.edtDate);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
        btnInserirUsuario = findViewById(R.id.btnInserirUsuario);

        setTitle("Novo Cadastro");
        edtNome.requestFocus();
    }

    private boolean validarCampos() {
        if (TextUtils.isEmpty(edtNome.getText().toString())) {
            edtNome.setError("Obrigatório *");
            edtNome.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Obrigatório *");
            edtEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(edtSenha.getText().toString())) {
            edtSenha.setError("Obrigatório *");
            edtSenha.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(edtDate.getText().toString())) {
            edtDate.setError("Obrigatório *");
            edtDate.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(edtCpf.getText().toString())) {
            edtCpf.setError("Obrigatório *");
            edtCpf.requestFocus();
            return false;
        }
        return true;
    }

    private void inserirUsuario() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();

        // Codifica a senha com Base64
        String senhaCodificada = Base64.encodeToString(senha.getBytes(), Base64.DEFAULT);

        Date dataAtual = new Date();
        String dataCadastro = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataAtual);

        try (Connection conn = ConexaoSqlServer.conectar();
             PreparedStatement pst = conn.prepareStatement(
                     "INSERT INTO Usuario (nome, email, senha, dataNasc, nivelAcesso, statusUsuario, telefone, dataCadastro, cpf_cnpj) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, senhaCodificada); // Armazenar a senha codificada
            pst.setString(4, date);
            pst.setString(5, "VOLUNTARIO");
            pst.setString(6, "ATIVO");
            pst.setString(7, telefone);
            pst.setString(8, dataCadastro);
            pst.setString(9, cpf);
            pst.executeUpdate();

            Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CadastroActivity.this, LoginActivity2.class);
            startActivity(intent);
            finish();
        } catch (SQLException e) {
            Toast.makeText(this, "Erro ao cadastrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarToast(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
