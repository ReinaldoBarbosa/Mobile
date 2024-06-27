package com.example.uniaovoluntaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.uniaovoluntaria.databinding.ActivityCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();

        binding.btnRecuperaConta.setOnClickListener(v -> validaDados());
    }


    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();
        String nome =  binding.editName.getText().toString().trim();
        String dataNasc = binding.editDataNasc.toString().toString().trim();

        if (!nome.isEmpty()){
            if (!email.isEmpty()) {
                if (!dataNasc.isEmpty()){
                    if (!senha.isEmpty()) {
                        criarContaFireBase(email,senha);
                    }
                    else
                    {
                        Toast.makeText(this, "Informe uma Senha.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Informe sua Data de Nascimento.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Informe seu Email.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Informe seu Nome.", Toast.LENGTH_SHORT).show();
        }

    }

    private void criarContaFireBase(String email,String senha){
        mAuth.createUserWithEmailAndPassword(email,senha
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