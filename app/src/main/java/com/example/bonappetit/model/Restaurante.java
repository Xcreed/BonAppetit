package com.example.bonappetit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurante implements Serializable {

    private String id;
    private String rangoPrecio;
    private String imagenesMenu;
    private String ubicacion;
    private String tipoComida;
    private String nombre;
    private String imagenPerfil;
    private String imagenComida;
    private String latitud_longitud;
    private String website;
    private String linkOrdenar;




    public Restaurante(String id, String rangoPrecio, String imagenesMenu, String ubicacion, String tipoComida) {
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

    public String getImagenesMenu() {
        return imagenesMenu;
    }

    public void setImagenesMenu(String imagenesMenu) {
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

    public String getLatitud_longitud() {
        return latitud_longitud;
    }

    public void setLatitud_longitud(String latitud_longitud) {
        this.latitud_longitud = latitud_longitud;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLinkOrdenar() {
        return linkOrdenar;
    }

    public void setLinkOrdenar(String linkOrdenar) {
        this.linkOrdenar = linkOrdenar;
    }

    public String getImagenComida() {
        return imagenComida;
    }

    public void setImagenComida(String imagenComida) {
        this.imagenComida = imagenComida;
    }
}
