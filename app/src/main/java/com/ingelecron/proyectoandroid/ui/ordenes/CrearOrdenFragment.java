package com.ingelecron.proyectoandroid.ui.ordenes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;
import com.ingelecron.proyectoandroid.tiposDatos.TipoCiudad;
import com.ingelecron.proyectoandroid.tiposDatos.TipoDepartamento;
import com.ingelecron.proyectoandroid.tiposDatos.TipoPais;
import com.ingelecron.proyectoandroid.ui.clientes.ObjCliente;
import com.ingelecron.proyectoandroid.ui.maquinas.ObjMaquina;
import com.ingelecron.proyectoandroid.ui.servicios.ObjServicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.empresa;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class CrearOrdenFragment extends Fragment {

    private CrearOrdenViewModel mViewModel;

    View root;
    AlmacenDatos almacenDatos;
    String tipo="", nit="",cedula="", error="", id_servicio="",id_maquina="",id_cliente="", pais="",estado="", ciudad="",direccion="",celular="",precioh="";
    int pais2 = 0, estado2 = 0, ciudad2 = 0, parg2 =0;

    TextView tprecio;
    Spinner sclientes, sservicios, smaquinas,spais, sestado, sciudad;
    EditText edireccion, ecelular;
    Button bcrear;
    List<ObjCliente> listaClientes;
    ArrayAdapter<ObjCliente> adaptadorClientes;
    List<ObjServicio> listaServicios;
    ArrayAdapter<ObjServicio> adaptadorServicios;
    List<ObjMaquina> listaMaquinas;
    ArrayAdapter<ObjMaquina> adaptadorMaquinas;
    ArrayAdapter<String> adaptadorp, adaptadord, adaptadorc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_crear_orden, container, false);

        MainActivity.actualFrame="frame";

        tipo=tipo(getActivity());
        cedula=cedula(getActivity());

        if(tipo.equals("1")){
            nit=empresa(getActivity());
        }else {
            nit=cedula;
        }


        almacenDatos = new BDPHP(getActivity());

        tprecio=root.findViewById(R.id.tprecioo);
        sclientes=root.findViewById(R.id.sclientes);
        sservicios=root.findViewById(R.id.sservicios);
        smaquinas=root.findViewById(R.id.smaquinas);
        spais =  root.findViewById(R.id.spais);
        sestado = root.findViewById(R.id.sestado);
        sciudad =  root.findViewById(R.id.sciudad);
        edireccion=root.findViewById(R.id.edireccion);
        ecelular=root.findViewById(R.id.ecelular);
        bcrear=root.findViewById(R.id.bcrear);

        listaClientes=new ArrayList<>();
        listaClientes();
        sclientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0) {

                    ObjCliente cliente = (ObjCliente) parent.getSelectedItem();
                    id_cliente = cliente.getId();
                    String nit = cliente.getNit();
                    String nombre = cliente.getNombre();
                    direccion = cliente.getDireccion();
                    celular = cliente.getCelular();
                    pais2 = Integer.valueOf(cliente.getPais());
                    estado2 = Integer.valueOf(cliente.getEstado());
                    ciudad2 = Integer.valueOf(cliente.getCiudad());

                    listaLugar();

                    edireccion.setText(direccion);
                    ecelular.setText(celular);

                    String mensaje = "Cliente " + nombre + " con nit " + nit;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("entendido", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listaServicios=new ArrayList<>();
        listaServicios();
        sservicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0) {

                    ObjServicio servicio = (ObjServicio) parent.getSelectedItem();
                    id_servicio= servicio.getId();
                    String nombre = servicio.getNombre();
                    precioh = servicio.getValor();

                    tprecio.setText(precioh);

                    String mensaje = "Servicio " + nombre + " con valor de " + precioh;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("entendido", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    builder.create();
                    builder.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listaMaquinas=new ArrayList<>();
        listaMaquinas();
        smaquinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0) {

                    ObjMaquina maquina = (ObjMaquina) parent.getSelectedItem();
                    id_maquina= maquina.getId();
                    String nombre = maquina.getNombre();
                    String marca = maquina.getMarca();

                    String mensaje = "Maquina " + nombre + " de marca " + marca;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("entendido", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listaLugar();

        bcrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pais2 = spais.getSelectedItemPosition();
                if (pais2 == 43) {
                    estado2 = sestado.getSelectedItemPosition();
                } else if (pais2 == 0) {
                    estado2 = 0;
                } else {
                    estado2 = 0;
                }
                ciudad2 = sciudad.getSelectedItemPosition();
                pais= String.valueOf(pais2);
                estado= String.valueOf(estado2);
                ciudad= String.valueOf(ciudad2);

                almacenDatos.crearOrden(nit, id_servicio, id_maquina, id_cliente, pais, estado, ciudad, direccion, celular, precioh, cedula, tipo, new RespuestasPHP() {
                    @Override
                    public void leerRespuesta(String respuesta) {

                        if (respuesta.equals("1")) {

                            mensajeEntendido(getActivity(), "¡Orden creado con exito!");
                            getActivity().onBackPressed();

                        } else {
                            mensajeEntendido(getActivity(), "Se ha producido un error al intentar crear una orden.\n" +
                                    "Por favor intentelo nuevamente");
                        }
                    }
                });

            }
        });


        return root;
    }

    private void listaClientes() {

        almacenDatos.listaCliente(cedula, tipo, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                ObjCliente objCliente1 = new ObjCliente();
                objCliente1.setNombre("+ agregar cliente");
                listaClientes.add(objCliente1);

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjCliente objCliente = new ObjCliente();
                            objCliente.setId(objecto.getString("id_cliente"));
                            objCliente.setNombre(objecto.getString("nombre"));
                            objCliente.setNit(objecto.getString("nit"));
                            objCliente.setCelular(objecto.getString("celular"));
                            objCliente.setDireccion(objecto.getString("direccion"));
                            objCliente.setPais(objecto.getString("pais"));
                            objCliente.setEstado(objecto.getString("estado"));
                            objCliente.setCiudad(objecto.getString("ciudad"));

                            listaClientes.add(objCliente);

                        } else {

                            error = objecto.getString("error");

                            if (error.equals("2")){
                                mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                        "Por favor intentelo nuevamente");
                            }
                        }

                    } catch (Exception e) {
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
                adaptadorClientes = new ArrayAdapter<ObjCliente>(getActivity(), R.layout.vista_texto_spinner, listaClientes);
                adaptadorClientes.setDropDownViewResource(R.layout.vista_texto_spinner);
                sclientes.setAdapter(adaptadorClientes);
            }
        });
    }

    private void listaServicios() {


        almacenDatos.listaServicio(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                ObjServicio objServicio1 = new ObjServicio();
                objServicio1.setNombre("+ agregar servicio");
                listaServicios.add(objServicio1);

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjServicio objServicio = new ObjServicio();
                            objServicio.setId(objecto.getString("id_servicio"));
                            objServicio.setNombre(objecto.getString("nombre"));
                            objServicio.setValor(objecto.getString("valor"));
                            listaServicios.add(objServicio);

                        } else {

                            error = objecto.getString("error");

                            if (error.equals("2")){
                                mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                        "Por favor intentelo nuevamente");
                            }
                        }

                    } catch (Exception e) {
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
                adaptadorServicios = new ArrayAdapter<ObjServicio>(getActivity(), R.layout.vista_texto_spinner, listaServicios);
                adaptadorServicios.setDropDownViewResource(R.layout.vista_texto_spinner);
                sservicios.setAdapter(adaptadorServicios);
            }
        });
    }

    private void listaMaquinas() {


        almacenDatos.listaMaquina(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                ObjMaquina objMaquina1= new ObjMaquina();
                objMaquina1.setNombre("+ agregar maquina");
                listaMaquinas.add(objMaquina1);

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjMaquina objMaquina= new ObjMaquina();
                            objMaquina.setId(objecto.getString("id_maquina"));
                            objMaquina.setNombre(objecto.getString("nombre"));
                            objMaquina.setMarca(objecto.getString("marca"));
                            listaMaquinas.add(objMaquina);

                        } else {

                            error = objecto.getString("error");

                            if (error.equals("2")){
                                mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                        "Por favor intentelo nuevamente");
                            }
                        }

                    } catch (Exception e) {
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
                adaptadorMaquinas = new ArrayAdapter<ObjMaquina>(getActivity(), R.layout.vista_texto_spinner, listaMaquinas);
                adaptadorMaquinas.setDropDownViewResource(R.layout.vista_texto_spinner);
                smaquinas.setAdapter(adaptadorMaquinas);
            }
        });
    }




    private void listaLugar() {

        adaptadorp = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoPais.getDatosPais());
        adaptadorp.setDropDownViewResource(R.layout.vista_texto_spinner);
        spais.setAdapter(adaptadorp);
        spais.setSelection(pais2);
        spais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2!=pais2){
                    estado2=0;
                    ciudad2=0;
                }

                parg2 = arg2;
                adaptadord = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoDepartamento.getDatosDepartamento(arg2));
                adaptadord.setDropDownViewResource(R.layout.vista_texto_spinner);
                sestado.setAdapter(adaptadord);
                sestado.setSelection(estado2);
                sestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                        if (arg2!=estado2){
                            ciudad2=0;
                        }

                        adaptadorc = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoCiudad.getDatosCiudad(arg2, parg2));
                        adaptadorc.setDropDownViewResource(R.layout.vista_texto_spinner);
                        sciudad.setAdapter(adaptadorc);
                        sciudad.setSelection(ciudad2);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                adaptadorp.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
}

    /*
        Calendar calendario = Calendar.getInstance();
        String año = String.valueOf(calendario.get(Calendar.YEAR));
        String mes = String.valueOf(calendario.get(Calendar.MONTH)+1);
        String dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
        String hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
        String minuto = String.valueOf(calendario.get(Calendar.MINUTE));
        String segundo = String.valueOf(calendario.get(Calendar.SECOND));
        */
