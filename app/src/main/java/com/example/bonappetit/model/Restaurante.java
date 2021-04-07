package com.example.bonappetit.model;

import java.util.ArrayList;

public class Restaurante {

    private String id;
    private String rangoPrecio;
    private ArrayList<String> imagenesMenu;
    private String ubicacion;
    private String tipoComida;
    private String nombre;
    private String imagenPerfil;



    public Restaurante(String id, String rangoPrecio, ArrayList<String> imagenesMenu, String ubicacion, String tipoComida) {
        this.id = id;
        this.rangoPrecio = rangoPrecio;
        this.imagenesMenu = imagenesMenu;
        this.ubicacion = ubicacion;
        this.tipoComida = tipoComida;
    }

    public Restaurante() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRangoPrecio() {
        return rangoPrecio;
    }

    public void setRangoPrecio(String rangoPrecio) {
        this.rangoPrecio = rangoPrecio;
    }

    public ArrayList<String> getImagenesMenu() {
        return imagenesMenu;
    }

    public void setImagenesMenu(ArrayList<String> imagenesMenu) {
        this.imagenesMenu = imagenesMenu;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}
