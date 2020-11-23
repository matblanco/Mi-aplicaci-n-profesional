package com.ingelecron.proyectoandroid.ui.trabajos;

public class ObjTrabajo {

    String id, idOrden="",horasTrabajo="",fechaInicio="",fechaFin="",condicion="";

    public ObjTrabajo() {
    }

    public ObjTrabajo(String id, String idOrden, String horasTrabajo, String fechaInicio, String fechaFin, String condicion) {
        this.id = id;
        this.idOrden = idOrden;
        this.horasTrabajo = horasTrabajo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.condicion = condicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getHorasTrabajo() {
        return horasTrabajo;
    }

    public void setHorasTrabajo(String horasTrabajo) {
        this.horasTrabajo = horasTrabajo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
