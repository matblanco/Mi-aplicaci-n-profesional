package com.ingelecron.proyectoandroid.ui.operador;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;


public class OperadorFragment extends Fragment {

    private OperadorVIstaModelo operadorVIstaModelo;

    View root;

    LinearLayout lagregar;
    RelativeLayout rbuscar;
    SearchView sbuscar;
    TextView tvacio;
    AlmacenDatos almacenDatos;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObjOperador> lista;
    private RecyclerView.Adapter adaptador;

    SwipeRefreshLayout srloperador;

    private String nit="", error="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        operadorVIstaModelo = ViewModelProviders.of(this).get(OperadorVIstaModelo.class);

         root = inflater.inflate(R.layout.frame_operador, container, false);

        MainActivity.actualFrame="frameOPerador";
        nit=cedula(getActivity());
        almacenDatos= new BDPHP(getActivity());

        tvacio=root.findViewById(R.id.tvacio);
        recyclerView=root.findViewById(R.id.rcv_operador);
        sbuscar=root.findViewById(R.id.sbuscar);
        rbuscar= root.findViewById(R.id.rbuscar);
        rbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbuscar.setIconified(false);
            }
        });

        srloperador=root.findViewById(R.id.srloperador);
        srloperador.setEnabled(false);

        lista=new ArrayList<>();
        adaptador=new AdaptadorOperador(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);


        lagregar =  root.findViewById(R.id.lagregar);
        lagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View alertView=inflater.inflate(R.layout.vista_dialogo_edittext, null);
                builder.setView(alertView);

                final EditText tdato=alertView.findViewById(R.id.edato);

                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String cedula=tdato.getText().toString();

                        almacenDatos.agregarOperador(nit, cedula, new RespuestasPHP() {
                            @Override
                            public void leerRespuesta(String respuesta) {

                                if (respuesta.equals("1")){

                                    mensajeEntendido(getActivity(), "¡Operario agregado con exito!");
                                   Navigation.findNavController(v).navigate(R.id.action_nav_operador_actualizar);

                                }else if(respuesta.equals("4")) {

                                    mensajeEntendido(getActivity(), "No ha sido posible agregar al operario.\n" +
                                            "El operario ya esta vinculado con una empresa.");

                                }else if(respuesta.equals("3")){
                                    mensajeEntendido(getActivity(), "Se ha producido un error al intentar  agregar operario.\n" +
                                            "Cedula no registrada.");
                                }else{
                                    mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                            "Por favor intentelo nuevamente");
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();
            }
        });



        almacenDatos.listaOperador(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {
                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjOperador objOperador = new ObjOperador();
                            objOperador.setNombre(objecto.getString("pnombre"));
                            objOperador.setCondicion(objecto.getString("condicion"));
                            objOperador.setCelular(objecto.getString("celular"));
                            objOperador.setCorreo(objecto.getString("correo"));
                            objOperador.setPais(objecto.getString("pais"));
                            objOperador.setEstado(objecto.getString("estado"));
                            objOperador.setCiudad(objecto.getString("ciudad"));

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objOperador);

                        } else {

                            error = objecto.getString("error");

                            if (error.equals("2")){
                                mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                        "Por favor intentelo nuevamente");
                            }

                        }

                    } catch (Exception e) {
                        Log.e("JSONException", "Error: " + e.toString());
                        tvacio.setVisibility(TextView.VISIBLE);
                    }
                }
                adaptador.notifyDataSetChanged();
            }
        });

        return root;
    }

}