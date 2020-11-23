package com.ingelecron.proyectoandroid.ui.inicio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.nempresa;
import static com.ingelecron.proyectoandroid.MainActivity.sesion;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class InicioOperadorFragment extends Fragment {

    private InicioOperadorViewModel mViewModel;

    View root;

    LinearLayout lmaquina, lventas, lclientes, lordenes;
    ImageView iventas, iordenes, imaquina, iclientes;
    String cedula="", tipo="", nempresa="", error="";
    TextView tempresa, tventas, tordenes, tmaquina, tclientes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root= inflater.inflate(R.layout.frame_inicio_operador, container, false);

        setHasOptionsMenu(true);

        cedula=cedula(getActivity());
        tipo=tipo(getActivity());
        nempresa=nempresa(getActivity());
        MainActivity.bloqueo();

        tempresa=root.findViewById(R.id.tempresa);
        lventas=root.findViewById(R.id.lventas);
        lordenes = root.findViewById(R.id.lordenes);
        lclientes =  root.findViewById(R.id.lclientes);
        lmaquina = root.findViewById(R.id.lmaquina);

        iventas =  root.findViewById(R.id.iventas);
        iordenes = root.findViewById(R.id.iordenes);
        iclientes =  root.findViewById(R.id.iclientes);
        imaquina = root.findViewById(R.id.imaquina);

        tventas = root.findViewById(R.id.tventas);
        tordenes = root.findViewById(R.id.tordenes);
        tclientes =  root.findViewById(R.id.tclientes);
        tmaquina = root.findViewById(R.id.tmaquina);


        if (nempresa.equals("")){

            AlmacenDatos almacenDatos = new BDPHP(getActivity());
            almacenDatos.UnionEmpresaOPerador(cedula, new LeerPHP() {
                @Override
                public void leerDatos(JSONArray datos) {

                    try {

                        JSONObject objecto=datos.getJSONObject(0);

                        if (objecto.length()>1) {

                            String empresa=objecto.getString("id_empresa");
                            String nempresa=objecto.getString("nombre");

                            sesion(getActivity(),cedula, tipo, empresa, nempresa);
                            tempresa.setText(nempresa);

                        }else {
                            lventas.setEnabled(false);
                            lordenes.setEnabled(false);
                            lclientes.setEnabled(false);
                            lmaquina.setEnabled(false);

                            iventas.setColorFilter(getResources().getColor(R.color.colorIcono));
                            iordenes.setColorFilter(getResources().getColor(R.color.colorIcono));
                            iclientes.setColorFilter(getResources().getColor(R.color.colorIcono));
                            imaquina.setColorFilter(getResources().getColor(R.color.colorIcono));

                            tventas.setTextColor(getResources().getColor(R.color.colorIcono));
                            tordenes.setTextColor(getResources().getColor(R.color.colorIcono));
                            tclientes.setTextColor(getResources().getColor(R.color.colorIcono));
                            tmaquina.setTextColor(getResources().getColor(R.color.colorIcono));

                            error = objecto.getString("error");

                            if (error.equals("2")){
                                mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                        "Por favor intentelo nuevamente");
                            }else {
                                mensajeEntendido(getActivity(), "Aún no se encuentra vinculado a una empresa.\n" +
                                        "Por favor intentelo nuevamente");
                            }
                        }

                    }catch (Exception e){
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
            });
        }else {
            tempresa.setText(nempresa);
        }



        lventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_id_frame_inicio_operador_to_id_frame_ventas);

            }
        });

        lordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_id_frame_inicio_operador_to_id_frame_ordenes);
            }
        });


        lclientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_id_frame_inicio_operador_to_id_frame_clientes);
            }
        });

        lmaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_id_frame_inicio_operador_to_id_frame_mimaquina);
            }
        });



        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MainActivity.mperfil = menu.findItem(R.id.action_perfil).setVisible(true);
    }
}

/*
        lagregar =  root.findViewById(R.id.lagregar);
        lagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.vista_dialogo_edittext, null));
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.show();
            }
        });

 */