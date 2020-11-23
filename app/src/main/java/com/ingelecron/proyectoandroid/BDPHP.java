package com.ingelecron.proyectoandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class BDPHP implements AlmacenDatos {

    private Context contexto;

    String url="http://ingelecron-001-site2.btempurl.com/",
            leer="leer.php",
            login="bd_login.php",
            registroOperario="bd_registro_operador.php",
            registroEmpresa="bd_registro_empresa.php",
            leerOperario="bd_leer_operador.php",
            leerEmpresa="bd_leer_empresa.php",
            editarOperario="bd_editar_operador.php",
            editarEmpresa="bd_editar_empresa.php",
            crearCliente="bd_crear_cliente.php",
            agregarCliente="bd_agregar_cliente.php",
            listaCliente="bd_lista_cliente.php",
            agregarOperador="bd_agregar_operador.php",
            listaOperador="bd_lista_operador.php",
            listaOperadorLibre="bd_lista_operador_libre.php",
            crearServicio="bd_crear_servicio.php",
            listaServicio="bd_lista_servicio.php",
            crearMaquina="bd_crear_maquina.php",
            listaMaquina="bd_lista_maquina.php",
            listaMaquinaLibre="bd_lista_maquina_libre.php",
            operadorMaquina="bd_operador_maquina.php",
            leerMaquina="bd_leer_maquina.php",
            unionEmpresaOperador="bd_union_empresa_operador.php",
            leerMiMaquina="bd_leer_mimaquina.php",
            crearOrden="bd_crear_orden.php",
            listaOrden="bd_lista_orden.php",
            operadorOrden="bd_operador_orden.php",
            leerOrden="bd_leer_orden.php",
            iniciarTrabajo="bd_iniciar_trabajo.php",
            detenerTrabajo="bd_detener_trabajo.php",
            terminarTrabajo="bd_terminar_trabajo.php",
            listaTrabajo="bd_lista_trabajo.php",
            listaVenta="bd_lista_venta.php",
            leerVenta="bd_leer_venta.php",
            editarVenta="bd_editar_venta.php",
            facturarVenta="bd_facturar_venta.php",
            leerFactura="bd_leer_factura.php";

    boolean listo=false;

    private RequestQueue colaPeticion;

    public BDPHP(Context contexto) {
        this.contexto = contexto;
        /*
        Cache cache = new DiskBasedCache(contexto.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        colaPeticion= new RequestQueue(cache, network);
        colaPeticion.start();*/
        //colaPeticion= Volley.newRequestQueue(contexto);
    }

    @Override
    public void leer(final RespuestasPHP vrespuesta) {

        StringRequest peticionGet=new StringRequest(Request.Method.GET,
                url+leer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("dato", response);
                        vrespuesta.leerRespuesta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            throw new Exception("Error: "+error.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

        //colaPeticion.add(peticionGet);
        Singleton.getInstance(contexto).addToRequestQueue(peticionGet);

    }

    @Override
    public void login(final String usuario, final String clave, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        Log.d("dato", response);
                        respuestaPHP.leerRespuesta(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("clave", clave);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void registroOperador(final String cedula, final String pnombre, final String snombre, final String papellido, final String sapellido, final String correo, final String celular, final String pais, final String estado, final String ciudad, final String clave, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + registroOperario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                Log.d("dato", response);
                                respuestaPHP.leerRespuesta(response);
                            }catch (Exception e){
                                Log.e("Error: ", e.getMessage());
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("cedula", cedula);
                parametros.put("pnombre",pnombre);
                parametros.put("snombre", snombre);
                parametros.put("papellido", papellido);
                parametros.put("sapellido", sapellido);
                parametros.put("correo", correo);
                parametros.put("celular", celular);
                parametros.put("pais", pais);
                parametros.put("estado", estado);
                parametros.put("ciudad", ciudad);
                parametros.put("clave", clave);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }


    @Override
    public void registroEmpresa(final String nit, final String nombre, final String correo, final String celular, final String direccion, final String pais, final String estado, final String ciudad, final String clave, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();


        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + registroEmpresa,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("nombre", nombre);
                parametros.put("correo", correo);
                parametros.put("celular", celular);
                parametros.put("direccion", direccion);
                parametros.put("pais", pais);
                parametros.put("estado", estado);
                parametros.put("ciudad", ciudad);
                parametros.put("clave", clave);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void leerOperador(String cedula, final LeerPHP leerPHP) {

          final ProgressDialog progressDialog = new ProgressDialog(contexto);
          progressDialog.setMessage("Cargando...");
          progressDialog.show();

         JSONArray parametros = new JSONArray();
          parametros.put(cedula);

          JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerOperario,
                  parametros,
                  new Response.Listener<JSONArray>() {
                      @Override
                      public void onResponse(JSONArray response) {
                          try {
                              Log.d("dato", "leidos datos JSON");
                              leerPHP.leerDatos(response);
                          }catch (Exception e){
                              Log.e("Error: ", e.getMessage());
                              progressDialog.dismiss();
                          }
                          progressDialog.dismiss();
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Log.e("Volley", error.toString());
                          progressDialog.dismiss();
                      }
                  });

          Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void leerEmpresa(String nit, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

            JSONArray parametros = new JSONArray();
            parametros.put(nit);

            JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerEmpresa,
                    parametros,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Log.d("dato", "leidos datos JSON");
                                leerPHP.leerDatos(response);
                            }catch (Exception e){
                                Log.e("Error: ", e.getMessage());
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", error.toString());
                            progressDialog.dismiss();
                        }
                    });

            Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void editarOperador(final String cedula, final String pnombre, final String snombre, final String papellido, final String sapellido, final String correo, final String celular, final String direccion, final String fechan, final String genero, final String pais, final String estado, final String ciudad, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + editarOperario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("cedula", cedula);
                parametros.put("pnombre",pnombre);
                parametros.put("snombre", snombre);
                parametros.put("papellido", papellido);
                parametros.put("sapellido", sapellido);
                parametros.put("correo", correo);
                parametros.put("celular", celular);
                parametros.put("direccion", direccion);
                parametros.put("fechan", fechan);
                parametros.put("genero", genero);
                parametros.put("pais", pais);
                parametros.put("estado", estado);
                parametros.put("ciudad", ciudad);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void editarEmpresa(final String nit, final String nombre, final String correo, final String celular, final String direccion, final String pais, final String estado, final String ciudad, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + editarEmpresa,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                            progressDialog.dismiss();
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("nombre", nombre);
                parametros.put("correo", correo);
                parametros.put("celular", celular);
                parametros.put("direccion", direccion);
                parametros.put("pais", pais);
                parametros.put("estado", estado);
                parametros.put("ciudad", ciudad);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void crearCliente(final String nit, final String nombre, final String correo, final String celular, final String direccion, final String pais, final String estado, final String ciudad, final String cedula, final String empresa, final String tipo, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + crearCliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("nombre", nombre);
                parametros.put("correo", correo);
                parametros.put("celular", celular);
                parametros.put("direccion", direccion);
                parametros.put("pais", pais);
                parametros.put("estado", estado);
                parametros.put("ciudad", ciudad);
                parametros.put("cedula", cedula);
                parametros.put("empresa", empresa);
                parametros.put("tipo", tipo);


                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void agregarCliente(final String nit, final String cedula, final String empresa, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + agregarCliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("cedula", cedula);
                parametros.put("empresa", empresa);


                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaCliente(String rel_id, final String tipo, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(rel_id);
        parametros.put(tipo);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaCliente,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

       Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void agregarOperador(final String nit, final String cedula, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + agregarOperador,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("cedula", cedula);


                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void listaOperador(String rel_id, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(rel_id);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaOperador,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void listaOperadorLibre(String rel_id, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(rel_id);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaOperadorLibre,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);

    }

    @Override
    public void crearServicio(final String nit, final String nombre, final String marca, final String modelo, final String descripcion, final String valor, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + crearServicio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("nombre",nombre);
                parametros.put("marca",marca);
                parametros.put("modelo",modelo);
                parametros.put("descripcion",descripcion);
                parametros.put("valor",valor);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaServicio(String nit, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(nit);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaServicio,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void crearMaquina(final String nit, final String nombre, final String marca, final String modelo, final String descripcion, final String estado, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + crearMaquina,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("nombre",nombre);
                parametros.put("marca",marca);
                parametros.put("modelo",modelo);
                parametros.put("descripcion",descripcion);
                parametros.put("estado", estado);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaMaquina(String nit, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(nit);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaMaquina,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void operadorMaquina(final String cedula, final String id_maquina, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + operadorMaquina,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("cedula", cedula);
                parametros.put("id_maquina", id_maquina);


                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void leerMaquina(String id_maquina, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(id_maquina);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerMaquina,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void UnionEmpresaOPerador(final String cedula, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(cedula);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + unionEmpresaOperador,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void leerMiMaquina(String cedula, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(cedula);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerMiMaquina,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaMaquinaLibre(String nit, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(nit);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaMaquinaLibre,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void crearOrden(final String nit, final String id_servicio, final String id_maquina, final String id_cliente, final String pais, final String estado, final String ciudad, final String direccion, final String celular, final String preciohora, final String cedula, final String tipo, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + crearOrden,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nit",nit);
                parametros.put("id_servicio",id_servicio);
                parametros.put("id_maquina",id_maquina);
                parametros.put("id_cliente",id_cliente);
                parametros.put("pais", pais);
                parametros.put("estado", estado);
                parametros.put("ciudad", ciudad);
                parametros.put("direccion", direccion);
                parametros.put("celular", celular);
                parametros.put("preciohora", preciohora);
                parametros.put("cedula", cedula);
                parametros.put("tipo", tipo);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaOrden(String rel_id, String tipo, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(rel_id);
        parametros.put(tipo);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaOrden,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void operadorOrden(final String cedula, final String id_orden, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + operadorOrden,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("cedula", cedula);
                parametros.put("id_orden", id_orden);


                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void leerOrden(String id_orden, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(id_orden);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerOrden,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void iniciarTrabajo(final String id_orden, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + iniciarTrabajo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_orden",id_orden);

                return parametros;
            }
        };

       Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void detenerTrabajo(final String id_orden, final RespuestasPHP respuestaPHP) {
        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + detenerTrabajo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_orden",id_orden);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void terminarTrabajo(final String id_orden, final RespuestasPHP respuestaPHP) {
        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + terminarTrabajo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_orden",id_orden);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaTrabajo(String id_orden, final LeerPHP leerPHP) {
        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(id_orden);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaTrabajo,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void listaVenta(String cedula, String tipo, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(cedula);
        parametros.put(tipo);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + listaVenta,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void leerVenta(String id_orden, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(id_orden);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerVenta,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void editarVenta(final String id_orden, final String descuento, final String abono, final String formaPago, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + editarVenta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_orden", id_orden);
                parametros.put("descuento",descuento);
                parametros.put("abono", abono);
                parametros.put("formaPago", formaPago);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void facturarVenta(final String id_orden, final RespuestasPHP respuestaPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest peticionPos= new StringRequest(Request.Method.POST,
                url + facturarVenta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("dato", response);
                            respuestaPHP.leerRespuesta(response);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id_orden",id_orden);

                return parametros;
            }
        };

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }

    @Override
    public void leerFactura(String id_orden, final LeerPHP leerPHP) {

        final ProgressDialog progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JSONArray parametros = new JSONArray();
        parametros.put(id_orden);

        JsonArrayRequest peticionPos = new JsonArrayRequest(Request.Method.POST, url + leerFactura,
                parametros,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("dato", "leidos datos JSON");
                            leerPHP.leerDatos(response);
                        }catch (Exception e){
                            Log.e("Error: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        progressDialog.dismiss();
                    }
                });

        Singleton.getInstance(contexto).addToRequestQueue(peticionPos);
    }


}

  /*   JSONObject parametros = new JSONObject();
          parametros.put("cedula",cedula);

          JsonObjectRequest peticionPos=new JsonObjectRequest(url + leerOperario,
                  parametros,
                  new Response.Listener<JSONObject>() {
                      @Override
                      public void onResponse(JSONObject response) {
                          Log.d("dato", "leidos datos JSON");
                          leerPHP.leerDatos(response);
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          try {
                              throw new Exception("Error: "+error.getMessage());
                          }catch (Exception e){
                              e.printStackTrace();
                          }
                      }
                  });*/
