package com.example.uniaovoluntaria.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.ConexaoSqlServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SplasActivity extends AppCompatActivity {

    private Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas);

        inicializarConexao();

        new Handler(getMainLooper()).postDelayed(this::verificarLogin, 3000);
    }

    private void verificarLogin() {
        if (isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity2.class));
        }
        finish();
    }

    private boolean isUserLoggedIn() {
        if (conn == null) {
            return false; // Se a conexão for nula, não há como verificar
        }

        String query = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            String email = getEmailFromPreferences();
            String senha = getPasswordFromPreferences();

            pst.setString(1, email);
            pst.setString(2, senha);

            ResultSet rs = pst.executeQuery();
            return rs.next(); // Se existir um registro, o usuário está logado
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Em caso de erro, assume que não está logado
        }
    }

    private void inicializarConexao() {
        conn = ConexaoSqlServer.conectar();

        try {
            if (conn != null) {
                if (!conn.isClosed()) {
                    setTitle("CONEXÃO REALIZADA COM SUCESSO");
                } else {
                    setTitle("A CONEXÃO ESTÁ FECHADA");
                }
            } else {
                setTitle("CONEXÃO NULA, NÃO REALIZADA");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            setTitle("CONEXÃO FALHOU!!!\n" + ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fechar a conexão, se necessário
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getEmailFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("email", null); // Retorna null se não encontrado
    }

    private String getPasswordFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("senha", null); // Retorna null se não encontrado
    }
}
