package com.ingelecron.proyectoandroid.ui.trabajos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.ingelecron.proyectoandroid.RespuestasPHP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class VerTrabajosFragment extends Fragment {

    private VerTrabajosViewModel mViewModel;

    public static VerTrabajosFragment newInstance() {
        return new VerTrabajosFragment();
    }

    View root;
    TextView tnumero, tservicio, tvacio, tterminar,ttiempo;
    boolean cuenta=false;
    String nit="", tipo="", id_orden="",numero="", servicio="",proceso="",error="",horaTotal="00:00:00", hora="", minuto="", segundo="";
    int hora2=0, minuto2=0, segundo2=0;
    LinearLayout lcronometro;

    Chronometer ctiempo;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObjTrabajo> lista;
    private RecyclerView.Adapter adaptador;

    AlmacenDatos almacenDatos;
    Date date;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ver_trabajos, container, false);


        MainActivity.actualFrame="frame";
        final Bundle bundle=getArguments();
        if (bundle!=null){
            id_orden=getArguments().getString("id_orden");
            numero=getArguments().getString("numero");
            servicio=getArguments().getString("servicio");
            proceso=getArguments().getString("proceso");
        }

        nit=cedula(getActivity());
        tipo=tipo(getActivity());

        tnumero=root.findViewById(R.id.tnumero);
        tservicio=root.findViewById(R.id.tservicio);
        tvacio=root.findViewById(R.id.tvacio);
        lcronometro=root.findViewById(R.id.lcronometro);
        ttiempo=root.findViewById(R.id.ttiempo);
        tterminar=root.findViewById(R.id.tterminar);
        ctiempo=root.findViewById(R.id.ctiempo);
        recyclerView=root.findViewById(R.id.rcv_trabajo);

        if (proceso.equals("4")||proceso.equals("5")||proceso.equals("6")||!tipo.equals("1")){
            lcronometro.setVisibility(LinearLayout.GONE);
            tterminar.setVisibility(TextView.GONE);
        }


        tnumero.setText(numero);
        tservicio.setText(servicio);

        ctiempo.setText("00:00:00");
        ctiempo.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s = (int)(time - h*3600000 - m*60000)/1000 ;
                String texto = (h<10? "0" + h:h) +":"+ (m<10? "0" + m:m) +":"+ (s<10? "0" + s:s);
                chronometer.setText(texto);
            }
        });

        lista=new ArrayList<>();
        adaptador=new AdaptadorTrabajos(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);

        almacenDatos = new BDPHP(getActivity());
        almacenDatos.listaTrabajo(id_orden, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjTrabajo objTrabajo = new ObjTrabajo();
                            objTrabajo.setId(objecto.getString("id_trabajo"));
                            objTrabajo.setIdOrden(objecto.getString("id_orden"));
                            objTrabajo.setHorasTrabajo(objecto.getString("horas_trabajo"));
                            objTrabajo.setFechaInicio(objecto.getString("fecha_inicio"));
                            objTrabajo.setFechaFin(objecto.getString("fecha_fin"));
                            objTrabajo.setCondicion(objecto.getString("condicion"));

                            String horas=objecto.getString("horas_trabajo");

                            date=formato.parse(horas);
                            Calendar calendario = Calendar.getInstance();
                            calendario.setTime(date);
                            int horat=calendario.get(Calendar.HOUR);
                            int minutot=calendario.get(Calendar.MINUTE);
                            int segundot=calendario.get(Calendar.SECOND);

                            hora2+=horat;
                            minuto2+=minutot;
                            segundo2+=segundot;

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objTrabajo);

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
                try {
                    date=formato.parse(horaTotal);
                    Calendar calendario = Calendar.getInstance();
                    calendario.setTime(date);
                    calendario.set(Calendar.HOUR, hora2);
                    calendario.set(Calendar.MINUTE, minuto2);
                    calendario.set(Calendar.SECOND, segundo2);

                    int horat=calendario.get(Calendar.HOUR);
                    int minutot=calendario.get(Calendar.MINUTE);
                    int segundot=calendario.get(Calendar.SECOND);

                    hora= String.valueOf(horat);
                    minuto= String.valueOf(minutot);
                    segundo= String.valueOf(segundot);
                    if(horat<10){
                        hora="0"+hora;
                    }
                    if (minutot<10){
                        minuto="0"+minuto;
                    }
                    if (segundot<10){
                        segundo="0"+segundo;
                    }

                    horaTotal=hora+":"+minuto+":"+segundo;
                }catch (Exception e){
                    Log.e("Exception", "Error: " + e.toString());
                }

                ttiempo.setText(horaTotal);
                adaptador.notifyDataSetChanged();
            }
        });

        ctiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String mensaje="";
                if (!cuenta){
                     mensaje = "¿Desea iniciar el trabajo?";
                }else {
                     mensaje = "¿Desea detener el trabajo?";
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(mensaje)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                if (!cuenta){

                                    almacenDatos.iniciarTrabajo(id_orden, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {

                                            if (respuesta.equals("1")) {

                                                //cronometro.setBase(SystemClock.elapsedRealtime() - (2 *3600000 + 0* 60000 + 0 * 1000));
                                                ctiempo.setBase(SystemClock.elapsedRealtime() - 0);
                                                ctiempo.start();
                                                cuenta=true;

                                                Drawable imagen = getActivity().getResources().getDrawable(R.drawable.ic_parar);
                                                int h = imagen.getIntrinsicHeight();
                                                int w = imagen.getIntrinsicWidth();
                                                imagen.setBounds( 0, 0, w, h );
                                                ctiempo.setCompoundDrawables(imagen, null, null, null);

                                            } else {
                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar iniciar el trabajo.\n" +
                                                        "Por favor intentelo nuevamente");
                                            }
                                        }
                                    });
                                }else {

                                    almacenDatos.detenerTrabajo(id_orden, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {
                                            if (respuesta.equals("1")) {

                                                final Bundle bundle=new Bundle();
                                                bundle.putString("id_orden", id_orden);
                                                bundle.putString("numero", numero);
                                                bundle.putString("servicio", servicio);
                                                bundle.putString("proceso", proceso);

                                                ctiempo.stop();
                                                ctiempo.setText("00:00:00");
                                                cuenta=false;

                                                Drawable imagen = getActivity().getResources().getDrawable(R.drawable.ic_iniciar);
                                                int h = imagen.getIntrinsicHeight();
                                                int w = imagen.getIntrinsicWidth();
                                                imagen.setBounds( 0, 0, w, h );
                                                ctiempo.setCompoundDrawables(imagen, null, null, null);

                                                Navigation.findNavController(v).navigate(R.id.action_id_frame_ver_trabajos_actualizar, bundle);

                                            } else {
                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar detener el trabajo.\n" +
                                                        "Por favor intentelo nuevamente");
                                            }
                                        }
                                    });

                                }



                            }

                        })
                        .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });

        tterminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (!cuenta) {
                    String mensaje = "¿Desea terminar el trabajo?";

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensaje)
                            .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    almacenDatos.terminarTrabajo(id_orden, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {

                                            if (respuesta.equals("1")){
                                                final Bundle bundle = new Bundle();
                                                bundle.putString("id_orden", id_orden);
                                                bundle.putString("numero", numero);
                                                bundle.putString("servicio", servicio);
                                                bundle.putString("proceso", "4");
                                                Navigation.findNavController(v).navigate(R.id.action_id_frame_ver_trabajos_actualizar, bundle);
                                            }else if (respuesta.equals("3")) {

                                                mensajeEntendido(getActivity(), "No ha sido posible terminar el trabajo.\n" +
                                                        "La orden no existe.");

                                            } else {
                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar terminar el trabajo.\n" +
                                                        "Por favor intentelo nuevamente");
                                            }

                                        }
                                    });
                                }

                            })
                            .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create();
                    builder.show();

                } else {
                    mensajeEntendido(getActivity(), "No es posible terminar el trabajo.\n" +
                            "Debe detener primero el trabajo que se esta ejecutando");
                }

            }
        });



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VerTrabajosViewModel.class);
        // TODO: Use the ViewModel
    }

}
