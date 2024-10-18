package com.example.uniaovoluntaria.dao;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.EventoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventoDao {

    private Connection conn = null;


    public void cadastrar(EventoModel e) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();

            // Query de inserção usando placeholders para evitar SQL Injection
            String sql = "INSERT INTO Evento (nome, dataEvento, vagas, horaInicio, infos, cep, numero, statusEvento, id_usuario) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // Define os parâmetros da query
            pstmt.setString(1, e.getNome());


            pstmt.setString(2, e.getDataEvento());


            pstmt.setInt(3, e.getVagas());
            pstmt.setString(4, e.getHoraInicio());
            pstmt.setString(5, e.getInfos());
            pstmt.setString(6, e.getCep());
            pstmt.setLong(7, e.getNumero());
            pstmt.setString(8, e.getStatusEvento());
            pstmt.setLong(9, e.getOng());

            // Executa a query
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Erro ao cadastrar evento", ex);
        } finally {
            // Fecha o PreparedStatement e a conexão
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }


    public void alterar(EventoModel e) throws SQLException {
        executeSql("update Evento set Nome = '" + e.getNome() + "' where id = " + e.getId() + "");
    }

    public void excluir(EventoModel e) throws SQLException {
        executeSql("Delete from Evento where id = " + e.getId());
    }

    public EventoModel carregaPorId(Integer id) {
        Connection conn = null;
        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Evento WHERE id = " + id;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    EventoModel evnt = new EventoModel();
                    evnt.setId(rs.getInt(1));
                    evnt.setNome(rs.getString(2));
                    evnt.setDataEvento(rs.getString(3));
                    evnt.setVagas(rs.getInt(4));
                    evnt.setHoraInicio(rs.getString(5));
                    evnt.setInfos(rs.getString(6));
                    evnt.setCep(rs.getString(8));
                    evnt.setNumero(rs.getLong(9));
                    evnt.setFotoEvento(rs.getBytes(10));
                    evnt.setStatusEvento(rs.getString(11));

                    conn.close();
                    return evnt;
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
    private List<EventoModel> getEvento(String sql) throws SQLException {
        List<EventoModel> lista = new ArrayList<>();

        Statement st = null;
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            EventoModel evnt = new EventoModel();
            evnt.setId(rs.getInt(1));
            evnt.setNome(rs.getString(2));
            evnt.setDataEvento(rs.getString(3));
            evnt.setVagas(rs.getInt(4));
            evnt.setHoraInicio(rs.getString(5));
            evnt.setInfos(rs.getString(6));
            evnt.setCep(rs.getString(7));
            evnt.setNumero(rs.getLong(8));
            evnt.setFotoEvento(rs.getBytes(9)); // Certifique-se que este é o formato correto
            evnt.setStatusEvento(rs.getString(10));
            // evnt.setOng(rs.getLong(11)); // Ajuste conforme necessário

            lista.add(evnt);
        }
        return lista;
    }


    public ArrayList<EventoModel> getALl(){
        ArrayList<EventoModel> listEvento = new ArrayList<EventoModel>();

        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM  Evento";

                listEvento = (ArrayList<EventoModel>) getEvento(sql);

                conn.close();
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return listEvento;
    }

    public List<EventoModel> getALl(String busca) {
        ArrayList<EventoModel> listEvento = new ArrayList<>();

        Connection conn = null;

        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                // Corrigindo a query SQL
                String sql = "SELECT * FROM Evento WHERE nome LIKE '%" + busca + "%'";

                listEvento = (ArrayList<EventoModel>) getEvento(sql);
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

        return listEvento; // Retornando a lista de eventos
    }

}
