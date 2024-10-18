package com.example.uniaovoluntaria.model;

import java.time.LocalDate;

public class PedidoHistoricoModel {
    private long id;
    private String nome;
    private LocalDate dataEvento;
    private int vagas;
    private String horaInicio;



    private String fotoEvento;
    private String infos;
    private String cep;
    private long numero;
    private String statusEvento;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVagas() {
        return vagas;
    }
    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }
    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getInfos() {
        return infos;
    }
    public void setInfos(String infos) {
        this.infos = infos;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public long getNumero() {
        return numero;
    }
    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getFotoEvento() {
        return fotoEvento;
    }

    public void setFotoEvento(String fotoEvento) {
        this.fotoEvento = fotoEvento;
    }
    public String getStatusEvento() {
        return statusEvento;
    }
    public void setStatusEvento(String statusEvento) {
        this.statusEvento = statusEvento;
    }


}
