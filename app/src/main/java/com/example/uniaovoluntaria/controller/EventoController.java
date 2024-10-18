package com.example.uniaovoluntaria.controller;

import android.content.Context;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.EventoModel;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EventoController {
    public boolean alterarEvento(String id, Context context){
        Boolean sucesso=false;
        try {
            Statement stm = ConexaoSqlServer.conectar().createStatement();
            String sql = "";
            sql = "UPDATE Evento ";
            sql += "SET frag=1 ";
            sql += "WHERE id=" + id ;
            stm.executeUpdate(sql);
            sucesso=true;
        }
        catch (Exception e){
            e.getMessage();
        }
        return sucesso;
    }


    public ArrayList<EventoModel> consultarEvento(Context context) {
        ArrayList<EventoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlServer.conectar().createStatement();

            ResultSet rs = stm.executeQuery("Select * from Evento WHERE frag=0");

            while (rs.next()) {
                EventoModel eventoModel = new EventoModel();

                eventoModel.setId(rs.getInt(1));
                eventoModel.setFotoEvento(rs.getBytes(2));
                eventoModel.setNome(rs.getString(3));
                eventoModel.setInfos(rs.getString(4));


                list.add(eventoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }
}
