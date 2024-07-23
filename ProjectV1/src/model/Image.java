package model;

import java.awt.image.BufferedImage;

public class Image {
    BufferedImage imagen;
    String nombre;

    public void setImagen(BufferedImage img) {
        this.imagen = img;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }
}
