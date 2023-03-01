package com.example.panicring.modelo;

public class RegistroUsuarioNuevo {

    private String nombre;
    private String apellido;
    private String correo;
    private Double numero;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public RegistroUsuarioNuevo(){

    }

    public RegistroUsuarioNuevo(String nombre, String apellido, String correo, Double numero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.numero = numero;
    }
}
