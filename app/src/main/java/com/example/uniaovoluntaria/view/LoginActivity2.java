package com.example.uniaovoluntaria.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.Auxiliares;
import com.example.uniaovoluntaria.controller.LoginController;
import com.example.uniaovoluntaria.model.LoginModel;

import java.util.concurrent.Executor;

public class LoginActivity2 extends AppCompatActivity {

    Button btn_entrar, btn_cadastro;
    EditText edtEmail, edtSenha;
    String usuarioAtual, senha;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btn_entrar = findViewById(R.id.btn_entrar);
        btn_cadastro = findViewById(R.id.btn_cadastro);

        loginController = new LoginController();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isFirstLogin = sharedPreferences.getBoolean("isFirstLogin", true);

        if (!isFirstLogin) {
            String email = sharedPreferences.getString("email", null);
            if (email != null) {
                autenticarBiometria(email, sharedPreferences);
            }
        }

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaDados()) {
                    usuarioAtual = edtEmail.getText().toString();
                    senha = edtSenha.getText().toString();

                    // Validar login e obter o modelo de usuário
                    LoginModel loginModel = loginController.validarLogin(getApplicationContext(), usuarioAtual, senha);

                    if (loginModel != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_id", loginModel.getIdUsuario());
                        editor.putString("email", loginModel.getEmail());
                        editor.putBoolean("isFirstLogin", false);
                        editor.apply();

                        autenticarBiometria(loginModel.getEmail(), sharedPreferences);
                    } else {
                        Auxiliares.alert(getApplicationContext(), "Usuário ou Senha incorretos!");
                        edtEmail.setText("");
                        edtSenha.setText("");
                        edtEmail.requestFocus();
                    }
                }
            }
        });

        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity2.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validaDados() {
        boolean retorno = true;
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError("Obrigatório*");
            edtEmail.requestFocus();
            retorno = false;
        } else if (edtSenha.getText().toString().isEmpty()) {
            edtSenha.setError("Obrigatório*");
            edtSenha.requestFocus();
            retorno = false;
        }
        return retorno;
    }

    private void autenticarBiometria(String email, SharedPreferences sharedPreferences) {
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                mostrarToast("Erro de autenticação: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                iniciarMainActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                mostrarToast("Autenticação falhou, tente novamente.");
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticação Biométrica")
                .setSubtitle("Use sua impressão digital para autenticar")
                .setNegativeButtonText("Cancelar")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void iniciarMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void mostrarToast(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
