package com.ingelecron.proyectoandroid.ui.ordenes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class OrdenFragment extends Fragment {

    private OrdenVistaModelo ordenVistaModelo;
    View root;

    String cedula="", tipo="", error="";

    TextView tvacio;
    LinearLayout lagregar;


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObjOrden> lista;
    private RecyclerView.Adapter adaptador;

    AlmacenDatos almacenDatos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ordenVistaModelo = ViewModelProviders.of(this).get(OrdenVistaModelo.class);

         root = inflater.inflate(R.layout.frame_orden, container, false);

        MainActivity.actualFrame="frameOrdenes";
        tipo=tipo(getActivity());
        cedula=cedula(getActivity());

        tvacio=root.findViewById(R.id.tvacio);
        recyclerView=root.findViewById(R.id.rcv_orden);
        lagregar= root.findViewById(R.id.lagregar);

        lista=new ArrayList<>();
        adaptador=new AdaptadorOrden(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);

        almacenDatos = new BDPHP(getActivity());
        almacenDatos.listaOrden(cedula, tipo, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {
                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjOrden objOrden = new ObjOrden();
                            objOrden.setId(objecto.getString("id_orden"));
                            objOrden.setNumero(objecto.getString("numero_orden"));
                            objOrden.setFechaCreado(objecto.getString("fecha_creado"));
                            objOrden.setServicio(objecto.getString("nombres"));
                            objOrden.setMaquina(objecto.getString("nombrem"));
                            objOrden.setCliente(objecto.getString("nombrec"));
                            objOrden.setProceso(objecto.getString("proceso"));

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objOrden);

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
                adaptador.notifyDataSetChanged();
            }
        });


        lagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tipo.equals("1")){
                    Navigation.findNavController(v).navigate(R.id.action_id_frame_ordenes_to_id_frame_crear_orden);
                }else {
                    Navigation.findNavController(v).navigate(R.id.action_nav_ordenes_to_id_frame_crear_orden);
                }


            }
        });


        return root;
    }
}