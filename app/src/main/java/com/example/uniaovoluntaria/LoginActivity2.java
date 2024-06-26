package com.example.uniaovoluntaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uniaovoluntaria.databinding.ActivityLogin2Binding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity2 extends AppCompatActivity {

    private ActivityLogin2Binding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.btnRecuperaConta.setOnClickListener(view -> {
            startActivity(new Intent(this,CadastroActivity.class));
        });

        binding.textRecuperaConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnEntrar.setOnClickListener(v ->validaDados());

        binding.imgFundo.setImageResource(R.drawable.bg_wave);
        binding.imgFundo.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(
                email,senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
            else {
                Toast.makeText(this, "Ocorreu um Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}