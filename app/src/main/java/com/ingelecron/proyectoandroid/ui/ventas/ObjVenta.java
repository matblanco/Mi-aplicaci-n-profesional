package com.ingelecron.proyectoandroid.ui.ventas;

public class ObjVenta {

    String id, idorden="",horastrabajo="",valorventa="", descuento="",abono="", saldo="", formapago="", fechavence="", diasfaltantes="", estatus="", credito="", condicion="", numero="", fechaCreado="", proceso="",servicio="", cliente="";

    public ObjVenta() {
    }

    public ObjVenta(String id, String idorden, String horastrabajo, String valorventa, String descuento, String abono, String saldo, String formapago, String fechavence, String diasfaltantes, String estatus, String credito, String condicion, String numero, String fechaCreado, String proceso, String servicio, String cliente) {
        this.id = id;
        this.idorden = idorden;
        this.horastrabajo = horastrabajo;
        this.valorventa = valorventa;
        this.descuento = descuento;
        this.abono = abono;
        this.saldo = saldo;
        this.formapago = formapago;
        this.fechavence = fechavence;
        this.diasfaltantes = diasfaltantes;
        this.estatus = estatus;
        this.credito = credito;
        this.condicion = condicion;
        this.numero = numero;
        this.fechaCreado = fechaCreado;
        this.proceso = proceso;
        this.servicio = servicio;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdorden() {
        return idorden;
    }

    public void setIdorden(String idorden) {
        this.idorden = idorden;
    }

    public String getHorastrabajo() {
        return horastrabajo;
    }

    public void setHorastrabajo(String horastrabajo) {
        this.horastrabajo = horastrabajo;
    }

    public String getValorventa() {
        return valorventa;
    }

    public void setValorventa(String valorventa) {
        this.valorventa = valorventa;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public String getFechavence() {
        return fechavence;
    }

    public void setFechavence(String fechavence) {
        this.fechavence = fechavence;
    }

    public String getDiasfaltantes() {
        return diasfaltantes;
    }

    public void setDiasfaltantes(String diasfaltantes) {
        this.diasfaltantes = diasfaltantes;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
