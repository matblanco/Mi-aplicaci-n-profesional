package com.ingelecron.proyectoandroid.ui.clientes;

public class ObjCliente {

    String id="",nit="",nombre="",correo="",celular="",direccion="",fechan="",genero="",pais="",estado="",ciudad="",condicion="";

    public ObjCliente() {
    }

    public ObjCliente(String id, String nit, String nombre, String correo, String celular, String direccion, String pais, String estado, String ciudad, String condicion) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.direccion = direccion;
        this.pais=pais;
        this.estado=estado;
        this.ciudad=ciudad;
        this.condicion = condicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechan() {
        return fechan;
    }

    public void setFechan(String fechan) {
        this.fechan = fechan;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
