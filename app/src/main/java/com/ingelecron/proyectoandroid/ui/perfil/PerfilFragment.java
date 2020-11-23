package com.ingelecron.proyectoandroid.ui.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.ingelecron.proyectoandroid.MainActivity.lugarPais;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.navController;


public class PerfilFragment extends Fragment {

    private PerfilVistaModelo perfilVistaModelo;
    View root;
    TextView tcedula,tnit,tnombre,tgenero,tfechan,tcelular,tcorreo,tdireccion,tlugar,tconfi,tcondición,tcambiar, teditar,tsalir;
    RelativeLayout rgenero, rfechan;
    String tipo="",cedula="",nombre="",correo="",celular="",direccion="",fechan="",genero="",lugar="",estado="",ciudad="",condicion="";
    int pais2=0,estado2=0,ciudad2=0,genero2=3;

    //private GetDatos dato;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {


         root = inflater.inflate(R.layout.frame_perfil, container, false);

        MainActivity.actualFrame="frame";
        tipo=MainActivity.tipo(getActivity());
        cedula=MainActivity.cedula(getActivity());

        tcedula=root.findViewById(R.id.tcedula);
        tnombre=root.findViewById(R.id.tnombre);
        tgenero=root.findViewById(R.id.tgenero);
        tfechan=root.findViewById(R.id.tfechan);
        tcelular=root.findViewById(R.id.tcelular);
        tcorreo=root.findViewById(R.id.tcorreo);
        tdireccion=root.findViewById(R.id.tdireccion);
        tlugar=root.findViewById(R.id.tlugar);
        tcondición=root.findViewById(R.id.tcondición);

        tnit=root.findViewById(R.id.tnit);
        rgenero=root.findViewById(R.id.rgenero);
        rfechan=root.findViewById(R.id.rfechan);

        AlmacenDatos almacenDatos= new BDPHP(getActivity());
        //dato = NuevoDato.elemento();


        if (tipo.equals("2")){
            tnit.setText("Nit");
            rgenero.setVisibility(RelativeLayout.GONE);
            rfechan.setVisibility(RelativeLayout.GONE);

            almacenDatos.leerEmpresa(cedula, new LeerPHP() {
                @Override
                public void leerDatos(JSONArray datos) {
                    try {

                        JSONObject objecto = datos.getJSONObject(0);

                        if (objecto.length() > 1) {

                            cedula = objecto.getString("id_empresa");
                            tcedula.setText(cedula);
                            nombre = objecto.getString("nombre");
                            tnombre.setText(nombre);
                            correo = objecto.getString("correo");
                            tcorreo.setText(correo);
                            celular = objecto.getString("celular");
                            tcelular.setText(celular);
                            direccion = objecto.getString("direccion");
                            tdireccion.setText(direccion);
                            pais2=objecto.getInt("pais");
                            estado2=objecto.getInt("estado");
                            ciudad2=objecto.getInt("ciudad");
                            lugar=lugarPais(objecto);
                            tlugar.setText(lugar);
                            condicion = objecto.getString("condicion");
                            if(condicion.equals("1")){
                                condicion="Activo";
                            }else {
                                condicion="Inactivo";
                            }
                            tcondición.setText(condicion);

                        } else {

                            mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                    "Por favor intentelo nuevamente");
                        }


                    } catch (Exception e) {
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
            });
        }else {

            almacenDatos.leerOperador(cedula, new LeerPHP() {
                @Override
                public void leerDatos(JSONArray datos) {

                    try {

                        JSONObject objecto = datos.getJSONObject(0);

                        if (objecto.length() > 1) {

                            cedula = objecto.getString("id_operador");
                            tcedula.setText(cedula);
                            nombre = objecto.getString("pnombre") + " " + objecto.getString("snombre") + " " + objecto.getString("papellido") + " " + objecto.getString("sapellido");
                            tnombre.setText(nombre);
                            correo = objecto.getString("correo");
                            tcorreo.setText(correo);
                            celular = objecto.getString("celular");
                            tcelular.setText(celular);
                            direccion = objecto.getString("direccion");
                            tdireccion.setText(direccion);
                            fechan = objecto.getString("fechan");
                            tfechan.setText(fechan);
                            genero2 = objecto.getInt("genero");
                            if(genero2==0){
                                genero="Hombre";
                            }else if(genero2==1){
                                genero="Mujer";
                            }else {
                                genero="Agregar genero";
                            }
                            tgenero.setText(genero);
                            pais2=objecto.getInt("pais");
                            estado2=objecto.getInt("estado");
                            ciudad2=objecto.getInt("ciudad");
                            lugar=lugarPais(objecto);
                            tlugar.setText(lugar);
                            condicion = objecto.getString("condicion");
                            if(condicion.equals("1")){
                                condicion="Activo";
                            }else {
                                condicion="Inactivo";
                            }
                            tcondición.setText(condicion);

                        } else {

                            mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                    "Por favor intentelo nuevamente");
                        }


                    } catch (Exception e) {
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
            });

        }

        tcambiar=root.findViewById(R.id.tcambiar);
        tcambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tipo.equals("1")) {
                    Navigation.findNavController(v).navigate(R.id.action_id_frame_perfil_to_id_frame_cambiar_contra);
                }else {
                    Navigation.findNavController(v).navigate(R.id.action_nav_perfil_to_id_frame_cambiar_contra);
                }

            }
        });

        teditar=root.findViewById(R.id.teditar);
        teditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                if(tipo.equals("1")){
                    String[] nombreTodoo=nombre.split(" ");
                    bundle.putString("pnombre",nombreTodoo[0]);
                    bundle.putString("snombre", nombreTodoo[1]);
                    bundle.putString("papellido", nombreTodoo[2] );
                    bundle.putString("sapellido", nombreTodoo[3]);
                    bundle.putString("correo", correo);
                    bundle.putString("celular", celular);
                    bundle.putString("direccion", direccion);
                    bundle.putString("fechan", fechan);
                    bundle.putInt("genero", genero2);
                    bundle.putInt("pais", pais2);
                    bundle.putInt("estado", estado2);
                    bundle.putInt("ciudad", ciudad2);
                    Navigation.findNavController(v).navigate(R.id.action_id_frame_perfil_to_id_frame_editar_perfil,bundle);

                }else {
                    bundle.putString("nombre", nombre);
                    bundle.putString("correo", correo);
                    bundle.putString("celular", celular);
                    bundle.putString("direccion", direccion);
                    bundle.putInt("pais", pais2);
                    bundle.putInt("estado", estado2);
                    bundle.putInt("ciudad", ciudad2);
                    Navigation.findNavController(v).navigate(R.id.action_nav_perfil_to_id_frame_editar_perfil,bundle);

                }
            }
        });

        tconfi=root.findViewById(R.id.tconfi);
        tconfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavOptions navOptions =new NavOptions.Builder()
                        .setEnterAnim(R.anim.nav_default_enter_anim)
                        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                        .setExitAnim(R.anim.nav_default_exit_anim)
                        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                        .build();
               navController.navigate(R.id.id_frame_preferencias, null, navOptions);

            }
        });


        tsalir=root.findViewById(R.id.tsalir);
                tsalir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //3. eliminar archivo preferencias

                        SharedPreferences preferencias=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferencias.edit();
                        editor.clear();
                        editor.commit();

                        if(tipo.equals("1")) {
                            Navigation.findNavController(v).navigate(R.id.action_id_frame_perfil_to_id_frame_login);
                        }else {
                            Navigation.findNavController(v).navigate(R.id.action_nav_perfil_to_id_frame_login);
                        }





            }
        });

        return root;
    }
}

/*
        almacenDatos.leerOperador(cedula, new LeerPHP() {
            @Override
            public void leerDatos(JSONObject datos) {
                try {
                    Toast.makeText(getActivity(), "lei: ", Toast.LENGTH_LONG).show();

                    JSONObject object=datos.getJSONObject("0");

                    if(!error.equals("")){
                        Toast.makeText(getActivity(), "ERROR: ", Toast.LENGTH_LONG).show();
                    }else {

                        //nombre=object.getString("pnombre");
                        tnombre.setText(String.valueOf(object.length()));
                    }

                }catch (Exception e){
                    Log.e("JSONException", "Error: " + e.toString());
                }

            }
        });*/
