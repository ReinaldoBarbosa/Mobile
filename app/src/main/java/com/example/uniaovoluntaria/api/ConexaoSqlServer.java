package com.example.uniaovoluntaria.api;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoSqlServer {

    public static Connection conectar() {
        Connection conn = null;
        try {
            // Permitir operações de rede na thread principal (não recomendado em produção)
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Carrega o driver JDBC
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            // Estabelece a conexão com o banco de dados
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://Db_uniaoVoluntaria.mssql.somee.com;databaseName=Db_uniaoVoluntaria;user=uniao;password=01122006;");
        } catch (Exception e) {
            // Log do erro para ajudar no diagnóstico
            Log.e("DB_ERROR", "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }

}


