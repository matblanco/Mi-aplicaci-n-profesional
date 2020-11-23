package com.ingelecron.proyectoandroid.tiposDatos;

public enum TipoDepartamento {

    d0("+ agregar departamento"),
    d1("Amazonas"),
    d2("Antioquia"),
    d3("Arauca"),
    d4("Atlantico"),
    d5("Bogotá D.C."),
    d6("Bolivar"),
    d7("Boyacá"),
    d8("Caldas"),
    d9("Caquetá"),
    d10("Casanare"),
    d11("Cauca"),
    d12("Cesar"),
    d13("Chocó"),
    d14("Cordoba"),
    d15("Cundinamarca"),
    d16("Guainia"),
    d17("Guaviare"),
    d18("Huila"),
    d19("La Guajira"),
    d20("Magdalena"),
    d21("Meta"),
    d22("Nariño"),
    d23("Norte de Santander"),
    d24("Putumayo"),
    d25("Quindío"),
    d26("Risaralda"),
    d27("San Andres y Providencia"),
    d28("Santander"),
    d29("Sucre"),
    d30("Tolima"),
    d31("Valle del cauca"),
    d32("Vaupés"),
    d33("Vichada"),
    d34("Otro");

    private final String td1;


    TipoDepartamento(String td1) {
        this.td1 = td1;
    }

    public String getTextoDepartamento() {
        return td1;
    }


    public static String[] getDatosDepartamento(int pais) {

        String[] resultado2 = new String[TipoDepartamento.values().length];
        int tamaño = 0;

        switch (pais) {


            case 0: {

                tamaño = 1;

                TipoDepartamento r = TipoDepartamento.values()[0];
                resultado2[0] = r.td1;

                break;
            }

            case 43: {

                tamaño = 33;

                for (int i = 0; i < tamaño; i++) {

                    TipoDepartamento departamento=  TipoDepartamento.values()[i + 1];
                    resultado2[departamento.ordinal() - 1] = departamento.td1;
                }


                break;
            }


            case 242: {

                tamaño = 34;

                for (TipoDepartamento departamento : TipoDepartamento.values()) {

                    resultado2[departamento.ordinal()] = departamento.td1;

                }

                break;
            }

            default: {

                tamaño = 1;

                TipoDepartamento r = TipoDepartamento.values()[34];
                resultado2[0] = r.td1;

                break;
            }
        }

        String[] resultado = new String[tamaño];

        for (int i = 0; i < tamaño; i++) {
            resultado[i] = resultado2[i];
        }

        return resultado;

    }

}
