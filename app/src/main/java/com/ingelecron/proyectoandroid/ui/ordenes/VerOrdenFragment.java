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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;


import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;
import com.ingelecron.proyectoandroid.tiposDatos.GetDatos;
import com.ingelecron.proyectoandroid.tiposDatos.NuevoDato;
import com.ingelecron.proyectoandroid.tiposDatos.TipoEstadoProceso;
import com.ingelecron.proyectoandroid.ui.operador.ObjOperador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.lugarPais;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;

public class VerOrdenFragment extends Fragment {

    private VerOrdenViewModel mViewModel;

    public static VerOrdenFragment newInstance() {
        return new VerOrdenFragment();
    }

    View root;

    AlmacenDatos almacenDatos;

    TextView tnumero,tfecha,tservicio,tmaquina,tcliente,tlugar,tdireccion,tcelular,tprecio,tproceso,toperario;
    String nit="", id_orden="", tipo="", lugar="", error="";
    Spinner slista;
    List<ObjOperador> listaSpinner;
    ArrayAdapter<ObjOperador> adaptadorSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ver_orden, container, false);

        MainActivity.actualFrame="frame";
        final Bundle bundle=getArguments();
        if (bundle!=null){
            id_orden=getArguments().getString("id_orden");
        }
        nit=cedula(getActivity());
        tipo=tipo(getActivity());
        almacenDatos = new BDPHP(getActivity());

        tnumero=root.findViewById(R.id.tnumero);
        tfecha=root.findViewById(R.id.tfecha);
        tservicio=root.findViewById(R.id.tservicio);
        tmaquina=root.findViewById(R.id.tmaquina);
        tcliente=root.findViewById(R.id.tcliente);
        tlugar=root.findViewById(R.id.tlugar);
        tdireccion=root.findViewById(R.id.tdireccion);
        tcelular=root.findViewById(R.id.tcelular);
        tprecio=root.findViewById(R.id.tprecio);
        tproceso=root.findViewById(R.id.tproceso);
        toperario=root.findViewById(R.id.toperario);

        listaSpinner=new ArrayList<>();
        slista=root.findViewById(R.id.slista);

        if (tipo.equals("1")){
            slista.setVisibility(Spinner.GONE);
        }
        listaOperador();

        slista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

                if (position!=0) {

                    ObjOperador operador = (ObjOperador) parent.getSelectedItem();
                    final String cedulao = operador.getCedula();
                    String nombreo = operador.getNombre();

                    String mensaje = "¿Desea asignar el operador?\n" + nombreo + " \nC.C " + cedulao;

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    almacenDatos.operadorOrden(cedulao, id_orden, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {

                                            if (respuesta.equals("1")) {

                                                Bundle bundle1=new Bundle();
                                                bundle.putString("id_orden",id_orden);
                                                mensajeEntendido(getActivity(), "¡Operador asignado con exito!");
                                                Navigation.findNavController(view).navigate(R.id.action_id_frame_ver_orden_actualizar,bundle);

                                            } else if (respuesta.equals("3")) {

                                                mensajeEntendido(getActivity(), "No ha sido posible asignar el operador.\n" +
                                                        "Esta orden ya tiene un operador asignado.");
                                            } else {

                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar  asignar operador.\n" +
                                                        "Intentelo nuevamente.");

                                            }
                                        }
                                    });
                                }

                            })
                            .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

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

        almacenDatos.leerOrden(id_orden, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                try {
                    JSONObject objecto = datos.getJSONObject(0);

                    if (objecto.length() > 1) {

                        tservicio.setText(objecto.getString("nombres"));
                        tmaquina.setText(objecto.getString("nombrem"));
                        tcliente.setText(objecto.getString("nombrec"));
                        lugar=lugarPais(objecto);
                        tlugar.setText(lugar);
                        tdireccion.setText(objecto.getString("direccion"));
                        tcelular.setText(objecto.getString("celular"));
                        tprecio.setText(objecto.getString("precio_hora"));
                        tnumero.setText(objecto.getString("numero_orden"));
                        tfecha.setText(objecto.getString("fecha_creado"));
                        int valorEstado=objecto.getInt("proceso");
                        GetDatos datosEstado = NuevoDato.elemento();
                        datosEstado.setTipoEstadoProceso(TipoEstadoProceso.values()[valorEstado]);
                        String nombreEstado=datosEstado.getTipoEstadoProceso().getTextoEstadoProceso();
                        tproceso.setText(nombreEstado);

                        if(objecto.length() > 19){
                            toperario.setText(objecto.getString("pnombre")+" "+
                                    objecto.getString("snombre")+" "+
                                    objecto.getString("papellido")+" "+
                                    objecto.getString("sapellido"));
                        }else {
                            toperario.setText("no asignado");
                        }

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
        });


        return root;
    }

    private void listaOperador() {

        almacenDatos.listaOperador(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                ObjOperador objOperador1 = new ObjOperador();
                objOperador1.setNombre("+ asignar operario");
                listaSpinner.add(objOperador1);

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjOperador objOperador = new ObjOperador();
                            objOperador.setCedula(objecto.getString("id_operador"));
                            objOperador.setNombre(objecto.getString("pnombre")+" "
                                    +objecto.getString("snombre")+" "
                                    +objecto.getString("papellido")+" "
                                    +objecto.getString("sapellido"));

                            listaSpinner.add(objOperador);

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
                adaptadorSpinner = new ArrayAdapter<ObjOperador>(getActivity(), R.layout.vista_texto_spinner, listaSpinner);
                adaptadorSpinner.setDropDownViewResource(R.layout.vista_texto_spinner);
                slista.setAdapter(adaptadorSpinner);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VerOrdenViewModel.class);
        // TODO: Use the ViewModel
    }

}
