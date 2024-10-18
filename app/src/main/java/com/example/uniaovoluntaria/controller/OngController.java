package com.example.uniaovoluntaria.controller;

import android.content.Context;
import android.util.Log;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.UsuarioModel;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OngController {

    public ArrayList<UsuarioModel> consultarOng(Context context) {
        ArrayList<UsuarioModel> list = new ArrayList<>();
        try {
            Statement stm = ConexaoSqlServer.conectar().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Usuario WHERE nivelAcesso='ONG'");

            while (rs.next()) {
                UsuarioModel usuarioModel = new UsuarioModel();
                usuarioModel.setId(rs.getInt(1));
                usuarioModel.setNome(rs.getString(2));
                usuarioModel.setEmail(rs.getString(3));
                usuarioModel.setInfos(rs.getString(5));
                usuarioModel.setCpf_cnpj(rs.getString(6));
                usuarioModel.setTelefone(rs.getString(8));
                usuarioModel.setFotoPerfil(rs.getBytes(11)); // Corrigido para setFotoPerfil

                list.add(usuarioModel);
            }
        } catch (Exception e) {
            Log.e("OngController", "Error while consulting ONGs: " + e.getMessage());
            e.printStackTrace(); // Adicionado para facilitar a depuração
        }
        return list; // Retornando uma lista de UsuarioModel
    }
}
