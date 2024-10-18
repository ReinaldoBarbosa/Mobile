package com.example.uniaovoluntaria.controller;

import android.content.Context;

import com.example.uniaovoluntaria.api.ConexaoSqlServer;
import com.example.uniaovoluntaria.model.EventoModel;
import com.example.uniaovoluntaria.model.HistoricoModel;
import com.example.uniaovoluntaria.model.PedidoHistoricoModel;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItensDoEventoController {


    public ArrayList<HistoricoModel> apresentarHistorico(Context context, String nrPedido) {
        ArrayList<HistoricoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlServer.conectar().createStatement();

            //Histórico pelo nr do pedido, retornando:
            //NomeBolo, preço do bolo, qtde, total e totalCompra
            ResultSet rs = stm.executeQuery(
                    "SELECT tbBolo.nomeBolo,tbBolo.precoBolo,tbItensPedido.qtde, tbItensPedido.total, tbPedido.totalCompra" +
                            " FROM tbPedido" +
                            " INNER JOIN tbItensPedido" +
                            " ON tbPedido.idPedido= tbItensPedido.idPedido" +
                            " INNER JOIN tbBolo" +
                            " ON tbBolo.id=tbItensPedido.idBolo" +
                            " WHERE tbPedido.idPedido="+nrPedido);
            while (rs.next()) {
                HistoricoModel historicoModel = new HistoricoModel();

                historicoModel.setNomeBolo(rs.getString(1));
                historicoModel.setPrecoBolo(rs.getString(2));
                historicoModel.setQtde(rs.getString(3));
                historicoModel.setTotal(rs.getString(4));

                list.add(historicoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }


    public ArrayList<PedidoHistoricoModel> apresentarListaPedido(Context context) {
        ArrayList<PedidoHistoricoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlServer.conectar().createStatement();

            //Histórico de todos os pedidos sendo o IdPedido da ultima para primeira:
            ResultSet rs = stm.executeQuery(
                    "SELECT id,nome,infos FROM Evento ORDER BY id DESC");

            while (rs.next()) {
                PedidoHistoricoModel pedidoHistoricoModel = new PedidoHistoricoModel();

                pedidoHistoricoModel.setId(rs.getInt(1));
                pedidoHistoricoModel.setNome(rs.getString(2));
                pedidoHistoricoModel.setInfos(rs.getString(3));


                list.add(pedidoHistoricoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }

    public boolean completarPedido(Context context,int nrEvento,int idUsuario, String valorTotalCompra) {
        Boolean sucesso = false;
        try {
            Statement stm = ConexaoSqlServer.conectar().createStatement();
            String sql = "";
            sql = "UPDATE Evento SET ";
            sql += "id_usuario="+idUsuario;
            sql += " WHERE id=" + nrEvento;
            stm.executeUpdate(sql);
            sucesso = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return sucesso;
    }



    public Boolean gravarItensDoPedido(Context context, int idUltimoIdGerado) {

        Boolean sucesso = false;
        try {
            String sql = "";
            Statement stm = ConexaoSqlServer.conectar().createStatement();

            sql = "Insert Into tbItensPedido(idPedido,idBolo,qtde,total) ";
            sql += "SELECT "+ idUltimoIdGerado +", id, qtde, (qtde* CAST(precoBolo AS float))";
            sql += " FROM tbBolo";
            sql += " WHERE frag = 1";
//Insert Into tbItensPedido(idPedido,idBolo,qtde,total) SELECT 3, id, qtde, (qtde*precoBolo) FROM tbBolo WHERE frag = 1
            stm.executeUpdate(sql);
            sucesso=true;
        } catch (Exception e) {
            e.getMessage();
        }

        return sucesso;
    }


    public EventoModel consultarIdPedido(Context context) {
        EventoModel idPedido = null;
        try {
            Statement stm = ConexaoSqlServer.conectar().createStatement();
            ResultSet rs = stm.executeQuery("SELECT MAX (id) FROM Evento");
            while (rs.next()) {
                EventoModel pedidoModel = new EventoModel();

                pedidoModel.setId(rs.getInt(1)); // Envia o Id para tabelaTemp
                idPedido = pedidoModel;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return idPedido;
    }









    public boolean IncluirQtdeItemDaTelaBolo(String id, Context context, String qtde) {
        Boolean sucesso = false;
        try {
            Statement stm = ConexaoSqlServer.conectar().createStatement();
            String sql = "";
            sql = "UPDATE tbBolo ";
            sql += "SET qtde= " + qtde;
            sql += "WHERE id=" + id;

            stm.executeUpdate(sql);
            sucesso = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return sucesso;
    }


    public void deixarTabelasValoresPadrao(Context context) {
        try {
            Statement stm = ConexaoSqlServer.conectar().createStatement();
            String sql = "";
            sql = "UPDATE Evento SET qtde=1,frag=0";
            stm.executeUpdate(sql);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public ArrayList<EventoModel> consultaItensDoPedidoBolo(Context context) {
        ArrayList<EventoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlServer.conectar().createStatement();

            ResultSet rs = stm.executeQuery("Select * from Evento where frag=1");

            while (rs.next()) {
                EventoModel pedidoModel = new EventoModel();

                pedidoModel.setId(rs.getInt(1));
                pedidoModel.setFotoEvento(rs.getBytes(2));
                pedidoModel.setNome(rs.getString(3));
                pedidoModel.setInfos(rs.getString(4));


                list.add(pedidoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }




}
