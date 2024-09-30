package com.example.uniaovoluntaria;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSqlServer {

    /*
    Método de conexão com SQL SERVER
    Local ou Nuvem no somee.com
     */

    public static Connection conectar() {
        // Objeto de conexão
        Connection conn = null;
        try {
            // Adicionar política de criação de thread
            StrictMode.ThreadPolicy politica;
            politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            // Registrar o driver JDBC
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            // >>>>>>>>>>>>>> Conexão com SQL Server no Somee.com <<<<<<<<<<<<<<
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://uniaoVoluntaria.mssql.somee.com;" +
                    "databaseName=uniaoVoluntaria;user=Rei_SQLLogin_1;password=niujc13t4r;");

        } catch (ClassNotFoundException e) {
            // Driver JDBC não encontrado
            Log.e("ConexaoSqlServer", "Erro: Driver JDBC não encontrado", e);
        } catch (SQLException e) {
            // Erro na conexão com o banco de dados
            Log.e("ConexaoSqlServer", "Erro na conexão com o banco de dados", e);
        }

        return conn;
    }
}
