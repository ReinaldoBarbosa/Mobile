package com.example.uniaovoluntaria.controller;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.LoginModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    public LoginModel validarLogin(Context context, String email, String senha) {
        Connection conn = ConexaoSqlServer.conectar();
        LoginModel loginModel = null;

        try {
            String query = "SELECT id, email, senha, statusUsuario FROM Usuario WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String senhaArmazenada = rs.getString("senha");
                String statusUsuario = rs.getString("statusUsuario");

                // Decodifica a senha armazenada
                byte[] decodePass = Base64.decode(senhaArmazenada, Base64.DEFAULT);
                String senhaDecodificada = new String(decodePass);

                // Verifica o status do usuário e compara as senhas
                if (statusUsuario.equals("ATIVO") && senhaDecodificada.equals(senha)) {
                    loginModel = new LoginModel();
                    loginModel.setIdUsuario(rs.getInt("id"));
                    loginModel.setEmail(rs.getString("email"));
                } else {
                    Log.d("LoginController", "Senha incorreta ou usuário inativo.");
                }
            } else {
                Log.d("LoginController", "Usuário não encontrado.");
            }
        } catch (Exception e) {
            Log.e("LoginController", "Erro ao validar login: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                Log.e("DB_ERROR", "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return loginModel;
    }
}
