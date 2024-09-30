package com.example.uniaovoluntaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uniaovoluntaria.databinding.ActivityCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    EditText edtNome, edtEmail, edtSenha, edtDate;
    Button btnInserirUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnInserirUsuario = (Button) findViewById(R.id.btnInserirUsuario);

        btnInserirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirUsuario();
            }
            public Connection conexaoBD() {
                Connection conexao = null;
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);



                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    conexao = DriverManager.getConnection("jdbc:jtds:sqlserver://uniaoVoluntaria.mssql.somee.com;databaseName=uniaoVoluntaria;user=uniao;password=01122006;");
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return conexao;

            }

            private void inserirUsuario() {
                try {
                    PreparedStatement pst = conexaoBD().prepareStatement("INSERT  INTO Usuario (nome, email, senha, dataNasc, nivelAcesso, statusUsuario,telefone,dataCadastro )VALUES (?, ?, ?,?,?,?,?,?)");

                    String nome = edtNome.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String senha = edtSenha.getText().toString().trim();
                    String date = edtDate.getText().toString().trim();

                    if(nome.isEmpty() || nome.equals("")){
                        Toast.makeText(getApplicationContext(), "INSIRA UM NOME", Toast.LENGTH_SHORT).show();
                        edtNome.setFocusable(true);
                    }else{
                        pst.setString(1, nome);
                    }


                    if(email.isEmpty() || email.equals("")){
                        Toast.makeText(getApplicationContext(), "INSIRA SEU EMAIL", Toast.LENGTH_SHORT).show();
                        edtEmail.setFocusable(true);
                    }else{
                        pst.setString(2, email);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pst.setString(4, LocalDate.now().toString());
                    }
                    if (senha.isEmpty() || senha.equals("")){
                        Toast.makeText(getApplicationContext(), "INSIRA SUA SENHA", Toast.LENGTH_SHORT).show();
                        edtSenha.setFocusable(true);
                    }
                    else{
                        pst.setString(3, senha);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pst.setString(8, LocalDate.now().toString());
                    }

                    pst.setString(5, "ADM");
                    pst.setString(6, "ATIVO");
                    pst.setString(7, "222222222");

                    pst.executeUpdate();
                    Toast.makeText(getApplicationContext(),"USUARIO INSERIDO COM SUCESSO", Toast.LENGTH_SHORT).show();
                }catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}