package com.ingelecron.proyectoandroid.tiposDatos;


public enum TipoEstadoMaquina {

    Maquina("+ estado máquina"),
    Activa ("Activa"),
    Inactiva ("Inactiva"),
    Mantenimiento ("Mantenimiento"),
    Desuso  ("Desuso");

    private final String texto;

    TipoEstadoMaquina(String texto) {
        this.texto = texto;
    }

    public String getTextoEstadoMaquina() { return texto; }

    public static String[] getDatosEstadoMaquina() {

        String[] resultado = new String[TipoEstadoMaquina.values().length];

        for (TipoEstadoMaquina estado : TipoEstadoMaquina.values()) {
            resultado[estado.ordinal()] = estado.texto;
        }

        return resultado;
    }

}
