package com.ingelecron.proyectoandroid.ui.maquinas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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


public class MaquinaFragment extends Fragment {

    private MaquinaVistaModelo maquinaVistaModelo;
    View root;
    LinearLayout lagregar;
    RelativeLayout rbuscar;
    SearchView sbuscar;
    TextView tvacio;
    String nit="", error="";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObjMaquina> lista;
    private RecyclerView.Adapter adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        maquinaVistaModelo = ViewModelProviders.of(this).get(MaquinaVistaModelo.class);

         root = inflater.inflate(R.layout.frame_maquina, container, false);

        MainActivity.actualFrame="frameMaquinas";
        nit=cedula(getActivity());

        tvacio=root.findViewById(R.id.tvacio);
        recyclerView=root.findViewById(R.id.rcv_maquina);
        sbuscar=root.findViewById(R.id.sbuscar);
        rbuscar= root.findViewById(R.id.rbuscar);
        rbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbuscar.setIconified(false);
            }
        });

        lista=new ArrayList<>();
        adaptador=new AdaptadorMaquina(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);

        AlmacenDatos almacenDatos = new BDPHP(getActivity());
        almacenDatos.listaMaquina(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {
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

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objMaquina);

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

        lagregar =  root.findViewById(R.id.lagregar);
        lagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Navigation.findNavController(v).navigate(R.id.action_nav_maquinas_to_id_frame_crear_maquina);
            }
        });



        return root;
    }
/*
    @Override
    public void onClick(View v) {
        int itemPosition = recyclerView.getChildLayoutPosition(v);
        ObjMaquina objMaquina = lista.get(itemPosition);
        String item=objMaquina.getModelo();
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
    }
    */

}