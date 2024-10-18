package com.example.uniaovoluntaria.dao;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.UsuarioModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OngDao {

    private Connection conn = null;

    public UsuarioModel carregaPorId(Integer id) {
        Connection conn = null;
        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Usuario WHERE id = " + id +" and nivelAcesso='ONG'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    UsuarioModel usuarioModel = new UsuarioModel();
                    usuarioModel.setId(rs.getInt(1));
                    usuarioModel.setNome(rs.getString(2));
                    usuarioModel.setEmail(rs.getString(3));
                    usuarioModel.setInfos(rs.getString(5));
                    usuarioModel.setCpf_cnpj(rs.getString(6));
                    usuarioModel.setTelefone(rs.getString(8));
                    usuarioModel.setFotoPerfil(rs.getBytes(11));

                    conn.close();
                    return usuarioModel;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private void executeSql(String sql) throws SQLException {
        try{
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                Statement st = conn.createStatement();
                st.executeQuery(sql);
                conn.close();

                conn.close();
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private List<UsuarioModel> getOng(String sql) throws SQLException {
        List<UsuarioModel> lista = new ArrayList<>();

        Statement st = null;
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setId(rs.getInt(1));
            usuarioModel.setNome(rs.getString(2));
            usuarioModel.setEmail(rs.getString(3));
            usuarioModel.setInfos(rs.getString(5));
            usuarioModel.setCpf_cnpj(rs.getString(6));
            usuarioModel.setTelefone(rs.getString(8));
            usuarioModel.setFotoPerfil(rs.getBytes(11));

            lista.add(usuarioModel);
        }
        return lista;
    }


    public ArrayList<UsuarioModel> getALl(){
        ArrayList<UsuarioModel> listEvento = new ArrayList<UsuarioModel>();

        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Usuario Where nivelAcesso='ONG'";

                listEvento = (ArrayList<UsuarioModel>) getOng(sql);

                conn.close();
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return listEvento;
    }

    public List<UsuarioModel> getALl(String busca) {
        ArrayList<UsuarioModel> listOng = new ArrayList<>();

        Connection conn = null;

        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                // Corrigindo a query SQL
                String sql = "SELECT * FROM Usuario WHERE nome LIKE '%" + busca + "%' and nivelAcesso='ONG'";

                listOng = (ArrayList<UsuarioModel>) getOng(sql);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // Fechando a conexão
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listOng; // Retornando a lista de eventos
    }
}
