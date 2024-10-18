package com.example.uniaovoluntaria.model;

public class UsuarioSession {
    private static UsuarioSession instance;
    private UsuarioModel usuario;

    private UsuarioSession() {}

    public static UsuarioSession getInstance() {
        if (instance == null) {
            instance = new UsuarioSession();
        }
        return instance;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
