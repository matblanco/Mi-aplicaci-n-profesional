package com.ingelecron.proyectoandroid.ui.ordenes;

public class ObjOrden {

    String id, idEmpresa = "", servicio = "", maquina = "", cliente = "", pais = "", estado = "", ciudad = "", direccion = "", celular = "", preciohora = "", numero = "", fechaCreado = "", proceso = "", fechaTermino, condicion = "";

    public ObjOrden() {
    }

    public ObjOrden(String id, String idEmpresa, String servicio, String maquina, String cliente, String pais, String estado, String ciudad, String direccion, String celular, String preciohora, String numero, String fechaCreado, String proceso, String fechaTermino, String condicion) {
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.servicio = servicio;
        this.maquina = maquina;
        this.cliente = cliente;
        this.pais = pais;
        this.estado = estado;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.celular = celular;
        this.preciohora = preciohora;
        this.numero = numero;
        this.fechaCreado = fechaCreado;
        this.proceso = proceso;
        this.fechaTermino = fechaTermino;
        this.condicion = condicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPreciohora() {
        return preciohora;
    }

    public void setPreciohora(String preciohora) {
        this.preciohora = preciohora;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(String fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}

