package com.ingelecron.proyectoandroid.tiposDatos;

import java.util.ArrayList;
import java.util.List;

public class NuevoDato {

    protected static List<GetDatos> vectorDatos = crearDatos();

    public NuevoDato() {
        vectorDatos = crearDatos();
    }

    public static ArrayList<GetDatos> crearDatos() {

        ArrayList<GetDatos> nuevodato = new ArrayList<GetDatos>();

        nuevodato.add(new GetDatos(TipoPais.P43, TipoDepartamento.d1, TipoCiudad.c1, TipoEstadoMaquina.Maquina, TipoEstadoProceso.Proceso, TipoFormaPago.FORMAPAGO));

        return nuevodato;
    }

    public static GetDatos elemento() {
        return vectorDatos.get(0);
    }

}
