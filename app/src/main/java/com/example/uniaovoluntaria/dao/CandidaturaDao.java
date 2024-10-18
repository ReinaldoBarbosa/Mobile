package com.example.uniaovoluntaria.dao;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.CandidaturaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidaturaDao {

    private Connection conn = null;

    public void cadastrar(CandidaturaModel c) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();

            // Query de inserção usando placeholders para evitar SQL Injection
            String sql = "INSERT INTO Candidatura(dataCadastro,dataAdmissao, id_usuario, id_evento, statusCadastro) "
                    + "VALUES (?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // Define os parâmetros da query
            pstmt.setString(1, c.getDataCadastro());


            pstmt.setString(2, c.getDataAdmissao());


            pstmt.setInt(3, (int) c.getIdUsuairo());
            pstmt.setString(4, String.valueOf(c.getIdEvento()));
            pstmt.setString(5, c.getStatusCadastro());


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

    public CandidaturaModel carregaPorId(Integer id) {
        Connection conn = null;
        try {
            // Conecta ao banco de dados
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Candidatura WHERE id = " + id;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    CandidaturaModel candi = new CandidaturaModel();
                    candi.setId(rs.getInt(1));
                    candi.setDataCadastro(rs.getString(2));
                    candi.setDataAdmissao(rs.getString(3));
                    candi.setIdUsuairo(rs.getInt(4));
                    candi.setIdEvento(rs.getInt(5));
                    candi.setStatusCadastro(rs.getString(6));

                    conn.close();
                    return candi;
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
}
