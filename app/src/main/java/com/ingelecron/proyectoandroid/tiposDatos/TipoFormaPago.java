package com.ingelecron.proyectoandroid.tiposDatos;


public enum TipoFormaPago {

    FORMAPAGO("+ forma pago"),
    Contado ("Contado"),
    Credito ("Credito");

    private final String texto;

    TipoFormaPago(String texto) {
        this.texto = texto;
    }

    public String getTextoTipoFormaPago() { return texto; }

    public static String[] getDatosTipoFormaPago() {

        String[] resultado = new String[TipoFormaPago.values().length];

        for (TipoFormaPago estado : TipoFormaPago.values()) {
            resultado[estado.ordinal()] = estado.texto;
        }

        return resultado;
    }

}
