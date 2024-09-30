package com.example.uniaovoluntaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.uniaovoluntaria.databinding.ActivityRecuperaContaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.btnRecupera.setOnClickListener(v -> validaDados());
    }



    private void validaDados() {
        String email = binding.edtEmail.getText().toString().trim();

        if (!email.isEmpty()) {
            recuperaContaFireBase(email);
        } else {
            Toast.makeText(this, "Informe seu Email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperaContaFireBase(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Já Pode Verificar o seu e-mail", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Ocorreu um Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}