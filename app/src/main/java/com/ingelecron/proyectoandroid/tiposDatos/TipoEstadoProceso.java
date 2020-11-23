package com.ingelecron.proyectoandroid.tiposDatos;


public enum TipoEstadoProceso {

    Proceso("+ estado proceso"),
    Creado ("Creado"),
    Asignado ("Asignado"),
    Activo ("Activo"),
    Terminado ("Terminado"),
    Facturado ("Facturado"),
    Cancelado  ("Cancelado");

    private final String texto;

    TipoEstadoProceso(String texto) {
        this.texto = texto;
    }

    public String getTextoEstadoProceso() { return texto; }

    public static String[] getDatosEstadoProceso() {

        String[] resultado = new String[TipoEstadoProceso.values().length];

        for (TipoEstadoProceso estado : TipoEstadoProceso.values()) {
            resultado[estado.ordinal()] = estado.texto;
        }

        return resultado;
    }

}
