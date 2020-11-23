package com.ingelecron.proyectoandroid.ui.servicios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;

public class CrearServicioFragment extends Fragment {

    private CrearServicioViewModel mViewModel;

    public static CrearServicioFragment newInstance() {
        return new CrearServicioFragment();
    }

    View root;

    String nit="";
    EditText enombre, emarca,emodelo,edescripcion, evalor;
    Button bcrear;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_crear_servicio, container, false);

        MainActivity.actualFrame="frame";
        nit=cedula(getActivity());

        enombre=root.findViewById(R.id.enombre);
        emarca=root.findViewById(R.id.emarca);
        emodelo=root.findViewById(R.id.emodelo);
        edescripcion=root.findViewById(R.id.edescripcion);
        evalor= root.findViewById(R.id.evalor);
        bcrear=root.findViewById(R.id.bcrear);

        bcrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = enombre.getText().toString();
                String marca = emarca.getText().toString();
                String modelo = emodelo.getText().toString();
                String descripcion = edescripcion.getText().toString();
                String valor = evalor.getText().toString();

                AlmacenDatos almacenDatos = new BDPHP(getActivity());
                almacenDatos.crearServicio(nit, nombre, marca, modelo, descripcion, valor, new RespuestasPHP() {
                    @Override
                    public void leerRespuesta(String respuesta) {

                        if (respuesta.equals("1")) {

                            mensajeEntendido(getActivity(), "¡Servicio creado con exito!");
                            getActivity().onBackPressed();

                        } else if (respuesta.equals("3")) {

                            mensajeEntendido(getActivity(), "No ha sido posible crear el servicio.\n" +
                                    "El servicio ya se encuentra creado.");

                        } else {
                            mensajeEntendido(getActivity(), "Se ha producido un error al intentar crear servicio.\n" +
                                    "Por favor intentelo nuevamente");
                        }
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CrearServicioViewModel.class);
        // TODO: Use the ViewModel
    }

}
