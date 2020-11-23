package com.ingelecron.proyectoandroid.ui.regsitro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;

import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.sesion;


public class RegistroDosFragment extends Fragment {

    private RegistroDosViewModel mViewModel;

   View root;

    TextView ttitulo;
    EditText ecelular,ecorreo,ecedula,enit,eclave,erclave;
    Button batras,bregistro;
    Bundle bundle;
    String nombre="",direccion="",pnombre="",snombre="",papellido="",sapellido="",pais="",estado="",ciudad="",tipo="";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_registro_dos, container, false);

        bundle=getArguments();
        if(bundle!=null) {
            nombre= getArguments().getString("nombre");
            direccion= getArguments().getString("direccion");
            pnombre= getArguments().getString("pnombre");
            snombre= getArguments().getString("snombre");
            papellido= getArguments().getString("papellido");
            sapellido= getArguments().getString("sapellido");
            pais= getArguments().getString("pais");
            estado= getArguments().getString("estado");
            ciudad= getArguments().getString("ciudad");
            tipo= getArguments().getString("tipo");
        }

        ttitulo=root.findViewById(R.id.ttitulo);
        ecelular=root.findViewById(R.id.ecelular);
        ecorreo=root.findViewById(R.id.ecorreo);
        ecedula=root.findViewById(R.id.ecedula);
        enit=root.findViewById(R.id.enit);
        eclave=root.findViewById(R.id.eclave);
        erclave=root.findViewById(R.id.erclave);
        bregistro= root.findViewById(R.id.bregistro);

        if (tipo.equals("1")){
            ttitulo.setText("Nuevo operador");
            enit.setVisibility(EditText.GONE);
        }else {
            ttitulo.setText("Nueva empresa");
            ecedula.setVisibility(EditText.GONE);
        }

        bregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String celular=ecelular.getText().toString();
                String correo=ecorreo.getText().toString();
                final String cedula=ecedula.getText().toString();
                final String nit=enit.getText().toString();
                String clave=eclave.getText().toString();
                String rclave=erclave.getText().toString();

                AlmacenDatos almacenDatos=new BDPHP(getActivity());

                if (tipo.equals("1")){

                    almacenDatos.registroOperador(cedula, pnombre, snombre, papellido, sapellido, correo, celular, pais, estado, ciudad, clave,
                            new RespuestasPHP() {
                                @Override
                                public void leerRespuesta(String respuesta) {


                                    if (respuesta.equals("1")){

                                        sesion(getActivity(), cedula, tipo, "", "");
                                        mensajeEntendido(getActivity(), "¡Registro exitoso!");
                                        Navigation.findNavController(v).navigate(R.id.action_id_frame_registro_dos_to_nav_inicio);

                                    } else if (respuesta.equals("3")){

                                        mensajeEntendido(getActivity(), "No ha sido posible registrarse.\n" +
                                                "El usuario ya se encuentra registrado.");

                                    } else {

                                        mensajeEntendido(getActivity(), "Se ha producido un error al intentar registrarse.\n" +
                                                "Por favor intentelo nuevamente");

                                    }

                                }
                            });

                }else {


                    almacenDatos.registroEmpresa(nit, nombre, correo, celular, direccion, pais, estado, ciudad, clave,
                            new RespuestasPHP() {
                                @Override
                                public void leerRespuesta(String respuesta) {

                                    if (respuesta.equals("1")){

                                        sesion(getActivity(), nit, tipo, "", "");
                                        mensajeEntendido(getActivity(), "¡Registro exitoso!");
                                        Navigation.findNavController(v).navigate(R.id.action_id_frame_registro_dos_to_nav_inicio);

                                    } else if (respuesta.equals("3")){

                                        mensajeEntendido(getActivity(), "No ha sido posible registrarse.\n" +
                                                "El usuario ya se encuentra registrado.");


                                    } else {

                                        mensajeEntendido(getActivity(), "Se ha producido un error al intentar registrarse.\n" +
                                                "Por favor intentelo nuevamente");

                                    }

                                }
                            });

                }

            }
        });

        return root;
    }



}
