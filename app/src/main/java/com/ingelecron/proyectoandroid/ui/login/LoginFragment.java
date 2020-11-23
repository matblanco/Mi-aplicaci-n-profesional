package com.ingelecron.proyectoandroid.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;

import static com.ingelecron.proyectoandroid.MainActivity.bloqueo;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.sesion;


public class LoginFragment extends Fragment {

    private LoginVistaModelo mViewModel;
    View root;
    TextView tnuevo_usuario, tolvido_clave;
    MaterialButton blogin;

    private TextInputEditText eusuario, eclave;

    private RequestQueue colaPeticion;
    public static String respuesta="";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(LoginVistaModelo.class);

        root = inflater.inflate(R.layout.frame_login, container, false);

        MainActivity.actualFrame="login";
        setHasOptionsMenu(true);

        bloqueo();

        eusuario = root.findViewById(R.id.tieusuario);
        eclave = root.findViewById(R.id.tiecontra);

        tnuevo_usuario = root.findViewById(R.id.tnuevo_usuario);
        tnuevo_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_id_frame_login_to_id_frame_tipo_registro);

            }
        });

        tolvido_clave = root.findViewById(R.id.tolvido_clave);
        tolvido_clave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_id_frame_login_to_id_frame_olvido);

            }
        });

        blogin = root.findViewById(R.id.blogin);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String usuario=eusuario.getText().toString();
                String clave=eclave.getText().toString();

                AlmacenDatos almacenDatos = new BDPHP(getActivity());
                almacenDatos.login(usuario, clave, new RespuestasPHP() {
                   @Override
                   public void leerRespuesta(String respuesta) {

                       if (respuesta.equals("1") || respuesta.equals("2")){

                           //1. guardar en preferencias: con el archivo "login" la variable "ingreso" con el valor "true"
                          sesion(getActivity(), usuario, respuesta, "", "");

                           Navigation.findNavController(v).navigate(R.id.action_id_frame_login_to_nav_inicio);

                       }else {

                           mensajeEntendido(getActivity(), "Usuario y/o contraseña no validos");

                       }
                }
               });


            }
        });

        return root;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MainActivity.mayuda = menu.findItem(R.id.action_ayuda).setVisible(true);
    }
}

  /* almacenDatos.leer(new RespuestasPHP() {
                    @Override
                    public void traerRespuesta(String result) {
                        eusuario.setText(result);
                    }
                });*/


    /*
     public void httpGet(String url) throws Exception{

        StringRequest peticion=new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("dato", "la respuesta es: "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.e("dato", "Error: "+error.getMessage());
                    }
                });

        colaPeticion.add(peticion);

    }
     */