package com.ingelecron.proyectoandroid.tiposDatos;

public class GetDatos {

    private TipoPais pais;
    private TipoCiudad ciudad;
    private TipoDepartamento departamento;
    private TipoEstadoMaquina tipoEstadoMaquina;
    private TipoEstadoProceso tipoEstadoProceso;
    private TipoFormaPago tipoFormaPago;

    public GetDatos( TipoPais pais, TipoDepartamento departamento, TipoCiudad ciudad, TipoEstadoMaquina tipoEstadoMaquina, TipoEstadoProceso tipoEstadoProceso, TipoFormaPago tipoFormaPago) {

        this.pais = pais;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.tipoEstadoMaquina = tipoEstadoMaquina;
        this.tipoEstadoProceso= tipoEstadoProceso;
        this.tipoFormaPago=tipoFormaPago;
    }

    public TipoPais getPais() {
        return pais;
    }

    public void setPais(TipoPais pais) {
        this.pais = pais;
    }

    public TipoDepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(TipoDepartamento departamento) {
        this.departamento = departamento;
    }

    public TipoCiudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(TipoCiudad ciudad) {
        this.ciudad = ciudad;
    }

    public TipoEstadoMaquina getTipoEstadoMaquina() {
        return tipoEstadoMaquina;
    }

    public void setTipoEstadoMaquina(TipoEstadoMaquina tipoEstadoMaquina) {
        this.tipoEstadoMaquina = tipoEstadoMaquina;
    }

    public TipoEstadoProceso getTipoEstadoProceso() {
        return tipoEstadoProceso;
    }

    public void setTipoEstadoProceso(TipoEstadoProceso tipoEstadoProceso) {
        this.tipoEstadoProceso = tipoEstadoProceso;
    }

    public TipoFormaPago getTipoFormaPago() {
        return tipoFormaPago;
    }

    public void setTipoFormaPago(TipoFormaPago tipoFormaPago) {
        this.tipoFormaPago = tipoFormaPago;
    }
}
