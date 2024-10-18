package com.example.uniaovoluntaria.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class EventoModel implements Parcelable {
    private int id;
    private String nome;
    private String dataEvento;
    private int vagas;
    private String horaInicio;
    private String infos;
    private String cep;
    private long numero;
    private byte[] fotoEvento;

    private int ong;
    private String statusEvento;

    public EventoModel() {}

    protected EventoModel(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        dataEvento = in.readString();
        vagas = in.readInt();
        horaInicio = in.readString();
        infos = in.readString();
        cep = in.readString();
        numero = in.readLong();
        fotoEvento = in.createByteArray();
        statusEvento = in.readString();
    }

    public static final Creator<EventoModel> CREATOR = new Creator<EventoModel>() {
        @Override
        public EventoModel createFromParcel(Parcel in) {
            return new EventoModel(in);
        }

        @Override
        public EventoModel[] newArray(int size) {
            return new EventoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(dataEvento);
        dest.writeInt(vagas);
        dest.writeString(horaInicio);
        dest.writeString(infos);
        dest.writeString(cep);
        dest.writeLong(numero);
        dest.writeByteArray(fotoEvento);
        dest.writeString(statusEvento);
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public byte[] getFotoEvento() {
        return fotoEvento;
    }

    public void setFotoEvento(byte[] fotoEvento) {
        this.fotoEvento = fotoEvento;
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

    public String getStatusEvento() {
        return statusEvento;
    }

    public void setStatusEvento(String statusEvento) {
        this.statusEvento = statusEvento;
    }

    public int getOng() {
        return ong;
    }

    public void setOng(int ong) {
        this.ong = ong;
    }

    // Método para decodificar Base64
    public String convertHexToBase64(String hex) {
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }

        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }

        return Base64.encodeToString(data, Base64.NO_WRAP);
    }
}
