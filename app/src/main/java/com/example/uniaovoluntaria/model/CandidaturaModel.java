package com.example.uniaovoluntaria.model;

public class CandidaturaModel {
    private long id;
    private long idEvento;
    private String dataCadastro;
    private String dataAdmissao;
    private long idUsuairo;
    private String statusCadastro;


    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(String dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public long getIdUsuairo() {
        return idUsuairo;
    }

    public void setIdUsuairo(long idUsuairo) {
        this.idUsuairo = idUsuairo;
    }

    public String getStatusCadastro() {
        return statusCadastro;
    }

    public void setStatusCadastro(String statusCadastro) {
        this.statusCadastro = statusCadastro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
