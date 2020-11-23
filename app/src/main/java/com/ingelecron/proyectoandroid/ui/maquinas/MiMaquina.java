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
import android.widget.Button;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.empresa;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class MiMaquina extends Fragment {

    private MiMaquinaViewModel mViewModel;

    public static MiMaquina newInstance() {
        return new MiMaquina();
    }

    View root;
    LinearLayout lagregar;
    LinearLayout lmaquina;
    AlmacenDatos almacenDatos;
    TextView tnombre,tmarca,tmodelo,tdescripcion,tcondicion,tvacio;

    String nit, cedula="", tipo="", error="", id_maquina="";

    Spinner slista;
    Button bcrear;
    List<ObjMaquina> listaSpinner;
    ArrayAdapter<ObjMaquina> adaptadorSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_mi_maquina, container, false);

        MainActivity.actualFrame="frameMaquinas";
        tipo=tipo(getActivity());
        cedula=cedula(getActivity());
        nit=empresa(getActivity());
        almacenDatos = new BDPHP(getActivity());


        lmaquina=root.findViewById(R.id.lmaquina);
        tnombre=root.findViewById(R.id.tnombre);
        tmarca=root.findViewById(R.id.tmarca);
        tmodelo=root.findViewById(R.id.tmodelo);
        tdescripcion=root.findViewById(R.id.tdescripcion);
        tcondicion=root.findViewById(R.id.tcondicion);
        tvacio=root.findViewById(R.id.tvacio);

        listaSpinner=new ArrayList<>();
        slista =root.findViewById(R.id.slista);
        listaMaquinaLibre();
        slista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                if (position!=0) {

                    ObjMaquina maquina = (ObjMaquina) parent.getSelectedItem();
                    id_maquina = maquina.getId();
                    String nombrem = maquina.getNombre();
                    String marcam = maquina.getNombre();

                    String mensaje = "¿Desea asignar la máquina?\n" + nombrem + " de marca " + marcam;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    almacenDatos.operadorMaquina(cedula, id_maquina, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {

                                            if (respuesta.equals("1")) {

                                                mensajeEntendido(getActivity(), "¡Máquina asignada con exito!");
                                                Navigation.findNavController(view).navigate(R.id.action_id_frame_mimaquina_actualizar);

                                            } else if (respuesta.equals("3")) {

                                                mensajeEntendido(getActivity(), "No ha sido posible asignar la máquina.\n" +
                                                        "Debe desvincularse de la máquina actual para poder vincular una nueva.");
                                            } else {

                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar  asignar máquina.\n" +
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


        almacenDatos.leerMiMaquina(cedula, new LeerPHP() {
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
                        tcondicion.setText(nombreEstado);

                        tvacio.setVisibility(TextView.GONE);
                        lmaquina.setVisibility(LinearLayout.VISIBLE);

                    } else {
                        tvacio.setVisibility(TextView.VISIBLE);
                        lmaquina.setVisibility(LinearLayout.GONE);
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

    private void listaMaquinaLibre() {

        almacenDatos.listaMaquinaLibre(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {


                ObjMaquina objMaquina1 = new ObjMaquina();
                objMaquina1.setNombre("+ asignar máquina");
                listaSpinner.add(objMaquina1);

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjMaquina objMaquina = new ObjMaquina();
                            objMaquina.setId(objecto.getString("id_maquina"));
                            objMaquina.setNombre(objecto.getString("nombre"));
                            objMaquina.setMarca(objecto.getString("marca"));
                            objMaquina.setModelo(objecto.getString("modelo"));
                            objMaquina.setDescripcion(objecto.getString("descripcion"));
                            objMaquina.setEstado(objecto.getString("estado"));

                            listaSpinner.add(objMaquina);

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
                adaptadorSpinner = new ArrayAdapter<ObjMaquina>(getActivity(), R.layout.vista_texto_spinner, listaSpinner);
                adaptadorSpinner.setDropDownViewResource(R.layout.vista_texto_spinner);
                slista.setAdapter(adaptadorSpinner);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MiMaquinaViewModel.class);
        // TODO: Use the ViewModel
    }

}
