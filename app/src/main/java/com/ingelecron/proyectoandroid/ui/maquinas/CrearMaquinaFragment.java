package com.ingelecron.proyectoandroid.ui.maquinas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;
import com.ingelecron.proyectoandroid.tiposDatos.TipoEstadoMaquina;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;


public class CrearMaquinaFragment extends Fragment {

    private CrearMaquinaViewModel mViewModel;

    View root;
    String nit="";
    EditText enombre, emarca,emodelo,edescripcion;
    Spinner sestado;
    Button bcrear;

    ArrayAdapter<String> adaptadorSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_crear_maquina, container, false);

        MainActivity.actualFrame="frame";
        nit=cedula(getActivity());

        enombre=root.findViewById(R.id.enombre);
        emarca=root.findViewById(R.id.emarca);
        emodelo=root.findViewById(R.id.emodelo);
        edescripcion=root.findViewById(R.id.edescripcion);
        bcrear=root.findViewById(R.id.bcrear);
        sestado=root.findViewById(R.id.sestado);


        adaptadorSpinner=new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoEstadoMaquina.getDatosEstadoMaquina());
        adaptadorSpinner.setDropDownViewResource(R.layout.vista_texto_spinner);
        sestado.setAdapter(adaptadorSpinner);
        sestado.setSelection(0);

        bcrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = enombre.getText().toString();
                String marca = emarca.getText().toString();
                String modelo = emodelo.getText().toString();
                String descripcion = edescripcion.getText().toString();

                String estado= String.valueOf(sestado.getSelectedItemPosition());

                AlmacenDatos almacenDatos = new BDPHP(getActivity());
                almacenDatos.crearMaquina(nit, nombre, marca, modelo, descripcion, estado, new RespuestasPHP() {
                    @Override
                    public void leerRespuesta(String respuesta) {

                        if (respuesta.equals("1")) {

                            mensajeEntendido(getActivity(), "¡Maquina creada con exito!");
                            getActivity().onBackPressed();

                        } else if (respuesta.equals("3")) {

                            mensajeEntendido(getActivity(), "No ha sido posible crear la maquina.\n" +
                                    "La máquina ya se encuentra creada.");

                        } else {
                            mensajeEntendido(getActivity(), "Se ha producido un error al intentar crear maquina.\n" +
                                    "Por favor intentelo nuevamente");
                        }
                    }
                });
            }
        });

        return root;
    }



}
