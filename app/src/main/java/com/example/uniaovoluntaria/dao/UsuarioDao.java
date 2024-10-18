package com.example.uniaovoluntaria.dao;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDao {

    private Connection conn = null;

    public void alterar(UsuarioModel e) throws SQLException {
        String sql = "UPDATE Usuario SET Nome = ?, Infos = ?, FotoPerfil = ? WHERE id = ?";
        try (Connection conn = ConexaoSqlServer.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getInfos());
            stmt.setBytes(3, e.getFotoPerfil()); // Adicionando a foto de perfil
            stmt.setInt(4, e.getId()); // ID do usuário a ser atualizado

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;  // Lança a exceção para ser tratada em outro local
        }
    }

    public void excluir(UsuarioModel e) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (Connection conn = ConexaoSqlServer.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    public UsuarioModel carregaPorId(Integer id) {
        Connection conn = null;
        try {
            conn = ConexaoSqlServer.conectar();
            if (conn != null) {
                String sql = "SELECT * FROM Usuario WHERE id = " + id; // Verifique se o id está correto
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    UsuarioModel user = new UsuarioModel();
                    user.setId(rs.getInt(1));
                    user.setNome(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setInfos(rs.getString(5));
                    user.setCpf_cnpj(rs.getString(6));
                    user.setTelefone(rs.getString(8));
                    user.setDataNasc(rs.getDate(9));
                    user.setFotoPerfil(rs.getBytes(11));

                    conn.close();
                    return user;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private void executeSql(String sql) throws SQLException {
        try (Connection conn = ConexaoSqlServer.conectar();
             Statement st = conn.createStatement()) {

            st.executeUpdate(sql);  // Usar executeUpdate para operações de modificação
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }
}
