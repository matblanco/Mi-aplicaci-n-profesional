package com.ingelecron.proyectoandroid.ui.maquinas;

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
import com.ingelecron.proyectoandroid.tiposDatos.TipoEstadoMaquina;
import com.ingelecron.proyectoandroid.ui.operador.ObjOperador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;

public class VerMaquinaFragment extends Fragment {

    private VerMaquinaViewModel mViewModel;

    public static VerMaquinaFragment newInstance() {
        return new VerMaquinaFragment();
    }

    View root;

    AlmacenDatos almacenDatos;

    TextView tnombre,tmarca,tmodelo,tdescripcion,testado,toperario;
    String nit="", id_maquina="", error="";
    Spinner slista;
    List<ObjOperador> listaSpinner;
    ArrayAdapter<ObjOperador> adaptadorSpinner;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ver_maquina, container, false);

        MainActivity.actualFrame="frame";
        Bundle bundle=getArguments();
        if (bundle!=null){
            id_maquina=getArguments().getString("id_maquina");
        }
        nit=cedula(getActivity());
        almacenDatos = new BDPHP(getActivity());

        tnombre=root.findViewById(R.id.tnombre);
        tmarca=root.findViewById(R.id.tmarca);
        tmodelo=root.findViewById(R.id.tmodelo);
        tdescripcion=root.findViewById(R.id.tdescripcion);
        testado=root.findViewById(R.id.testado);
        toperario=root.findViewById(R.id.toperario);

        listaSpinner=new ArrayList<>();
        slista=root.findViewById(R.id.slista);

        listaOperadorLibre();

        slista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

                if (position!=0) {

                    ObjOperador operador = (ObjOperador) parent.getSelectedItem();
                    final String cedulao = operador.getCedula();
                    String nombreo = operador.getNombre();

                    String mensaje = "¿Desea asignar al operario?\n" + nombreo + " con C.C " + cedulao;

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    almacenDatos.operadorMaquina(cedulao, id_maquina, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {

                                            if (respuesta.equals("1")) {
                                                Bundle bundle=new Bundle();
                                                bundle.putString("id_maquina",id_maquina);

                                                mensajeEntendido(getActivity(), "¡Operario asignado con exito!");
                                                Navigation.findNavController(view).navigate(R.id.action_id_frame_ver_maquina_actualizar,bundle);

                                            } else if (respuesta.equals("3")) {

                                                mensajeEntendido(getActivity(), "No ha sido posible asignar el operario.\n" +
                                                        "El operario ya esta asignado a otra maquina.");
                                            } else {

                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar  asignar operario.\n" +
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

        almacenDatos.leerMaquina(id_maquina, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                    try {
                        JSONObject objecto = datos.getJSONObject(0);

                        if (objecto.length() > 1) {

                                tnombre.setText(objecto.getString("nombre"));
                                tmarca.setText(objecto.getString("marca"));
                                tmodelo.setText(objecto.getString("modelo"));
                                tdescripcion.setText(objecto.getString("descripcion"));

                                int valorEstado=objecto.getInt("estado");
                                GetDatos datosEstado = NuevoDato.elemento();
                                datosEstado.setTipoEstadoMaquina(TipoEstadoMaquina.values()[valorEstado]);
                                String nombreEstado=datosEstado.getTipoEstadoMaquina().getTextoEstadoMaquina();
                                testado.setText(nombreEstado);

                            if(objecto.length() > 8){
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

    private void listaOperadorLibre() {

        almacenDatos.listaOperadorLibre(nit, new LeerPHP() {
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
        mViewModel = ViewModelProviders.of(this).get(VerMaquinaViewModel.class);
        // TODO: Use the ViewModel
    }



}
