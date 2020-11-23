package com.ingelecron.proyectoandroid.ui.servicios;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class ServiciosFragment extends Fragment {

    private ServiciosViewModel mViewModel;

    public static ServiciosFragment newInstance() {
        return new ServiciosFragment();
    }

    View root;
    LinearLayout lagregar;
    RelativeLayout rbuscar;
    SearchView sbuscar;
    TextView tvacio;
    String nit="", error="";

    AlmacenDatos almacenDatos;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObjServicio> lista;
    private RecyclerView.Adapter adaptador;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_servicios, container, false);

        MainActivity.actualFrame="frame";
        nit=cedula(getActivity());

        tvacio=root.findViewById(R.id.tvacio);
        recyclerView=root.findViewById(R.id.rcv_servicio);
        sbuscar=root.findViewById(R.id.sbuscar);
        rbuscar= root.findViewById(R.id.rbuscar);
        rbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbuscar.setIconified(false);
            }
        });

        lista=new ArrayList<>();
        adaptador=new AdaptadorServicio(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);

        almacenDatos = new BDPHP(getActivity());
        almacenDatos.listaServicio(nit, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjServicio objServicio = new ObjServicio();
                            objServicio.setNombre(objecto.getString("nombre"));
                            objServicio.setMarca(objecto.getString("marca"));
                            objServicio.setModelo(objecto.getString("modelo"));
                            objServicio.setDescripcion(objecto.getString("descripcion"));
                            objServicio.setValor(objecto.getString("valor"));

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objServicio);

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
                Navigation.findNavController(v).navigate(R.id.action_nav_servicios_to_id_frame_crear_servicio);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ServiciosViewModel.class);

    }

}
