package com.example.uniaovoluntaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uniaovoluntaria.databinding.ActivityLogin2Binding;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginActivity2 extends AppCompatActivity {

    private ActivityLogin2Binding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.btnInserirUsuario.setOnClickListener(view -> {
            startActivity(new Intent(this,CadastroActivity.class));
        });

        binding.textRecuperaConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnEntrar.setOnClickListener(v ->validaDados());

        binding.imgFundo.setImageResource(R.drawable.bg_wave);
        binding.imgFundo.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    private void validaDados() {
        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                loginFireBase(email, senha);
            } else {
                Toast.makeText(this, "Informe uma Senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu Email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginFireBase(String email, String senha){
        try {

            PreparedStatement pst = ConexaoSqlSever.conectar(context).prepareStatement(
                    "SELECT eMail, senha FROM tbUsuario WHERE eMail=? AND senha=?");

            //Os números abaixo são os indices da ordem dos campos da tabela
            //Deve seguir a ordem
            pst.setString(1,email);
            pst.setString(2,senha);

            ResultSet res = pst.executeQuery();

            while (res.next()) {

                LoginModel loginModel = new LoginModel();

                loginModel.setEmail(res.getString(1));
                loginModel.setSenha(res.getString(2));
                return loginModel;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    }
}