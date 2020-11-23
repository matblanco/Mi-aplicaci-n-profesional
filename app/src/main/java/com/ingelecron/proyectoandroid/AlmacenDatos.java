package com.ingelecron.proyectoandroid;

public interface AlmacenDatos {

    void leer(final RespuestasPHP vrespuesta);

    void login(String usuario, String clave, RespuestasPHP respuestaPHP);

    void registroOperador(String cedula,
                          String pnombre,
                          String snombre,
                          String papellido,
                          String sapellido,
                          String correo,
                          String celular,
                          String pais,
                          String estado,
                          String ciudad,
                          String clave,
                          RespuestasPHP respuestaPHP);

    void registroEmpresa(String nit,
                         String nombre,
                         String correo,
                         String celular,
                         String direccion,
                         String pais,
                         String estado,
                         String ciudad,
                         String clave,
                         RespuestasPHP respuestaPHP);

    void leerOperador(String cedula, LeerPHP leerPHP);

    void leerEmpresa(String nit, LeerPHP leerPHP);

    void editarOperador(String cedula,
                        String pnombre,
                        String snombre,
                        String papellido,
                        String sapellido,
                        String correo,
                        String celular,
                        String direccion,
                        String fechan,
                        String genero,
                        String pais,
                        String estado,
                        String ciudad,
                        RespuestasPHP respuestaPHP);

    void editarEmpresa(String nit,
                       String nombre,
                       String correo,
                       String celular,
                       String direccion,
                       String pais,
                       String estado,
                       String ciudad,
                       RespuestasPHP respuestaPHP);

    void crearCliente(String nit,
                      String nombre,
                      String correo,
                      String celular,
                      String direccion,
                      String pais,
                      String estado,
                      String ciudad,
                      String cedula,
                      String empresa,
                      String tipo,
                      RespuestasPHP respuestaPHP);

    void agregarCliente(String nit,
                        String cedula,
                        String empresa,
                        RespuestasPHP respuestaPHP);

    void listaCliente(String rel_id, String tipo, LeerPHP leerPHP);

    void agregarOperador(String nit,
                         String cedula,
                         RespuestasPHP respuestaPHP);

    void listaOperador(String rel_id, LeerPHP leerPHP);

    void listaOperadorLibre(String rel_id, LeerPHP leerPHP);

    void crearServicio(String nit,
                       String nombre,
                       String marca,
                       String modelo,
                       String descripcion,
                       String valor,
                       RespuestasPHP respuestaPHP);

    void listaServicio(String nit, LeerPHP leerPHP);

    void crearMaquina(String nit,
                      String nombre,
                      String marca,
                      String modelo,
                      String descripcion,
                      String estado,
                      RespuestasPHP respuestaPHP);

    void listaMaquina(String nit, LeerPHP leerPHP);

    void operadorMaquina(String cedula,
                         String id_maquina,
                         RespuestasPHP respuestaPHP);

    void leerMaquina(String id_maquina, LeerPHP leerPHP);

    void UnionEmpresaOPerador(String cedula, LeerPHP leerPHP);

    void leerMiMaquina(String cedula, LeerPHP leerPHP);

    void listaMaquinaLibre(String nit, LeerPHP leerPHP);

    void crearOrden(String nit,
                    String id_servicio,
                    String id_maquina,
                    String id_cliente,
                    String pais,
                    String estado,
                    String ciudad,
                    String direccion,
                    String celular,
                    String preciohora,
                    String cedula,
                    String tipo,
                    RespuestasPHP respuestaPHP);

    void listaOrden(String rel_id, String tipo, LeerPHP leerPHP);

    void operadorOrden(String cedula,
                       String id_orden,
                       RespuestasPHP respuestaPHP);

    void leerOrden(String id_orden, LeerPHP leerPHP);

    void iniciarTrabajo(String id_orden, RespuestasPHP respuestaPHP);

    void detenerTrabajo(String id_orden, RespuestasPHP respuestaPHP);

    void terminarTrabajo(String id_orden, RespuestasPHP respuestaPHP);

    void listaTrabajo(String id_orden, LeerPHP leerPHP);

    void listaVenta(String cedula, String tipo, LeerPHP leerPHP);

    void leerVenta(String id_orden, LeerPHP leerPHP);

    void editarVenta(String id_orden,
                     String descuento,
                     String abono,
                     String formaPago,
                     RespuestasPHP respuestaPHP);

    void facturarVenta(String id_orden, RespuestasPHP respuestaPHP);

    void leerFactura(String id_orden, LeerPHP leerPHP);
}
