package domain;

import data.PeliculaData;

public class Pelicula {

    private int codPelicula;
    private String titulo;
    private int precio;

    public Pelicula() {
        codPelicula = 0;
        titulo = " ";
        precio = 0;
    }

    public Pelicula(int codPelicula, String titulo, int precio) {
        this.codPelicula = codPelicula;
        this.titulo = titulo;
        this.precio = precio;
    }


    public int getCodPelicula() {
        return codPelicula;
    }

    public void setCodPelicula(int codPelicula) {
        this.codPelicula = codPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
