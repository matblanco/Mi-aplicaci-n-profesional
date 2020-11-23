package com.ingelecron.proyectoandroid.ui.ventas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;


import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;
import com.ingelecron.proyectoandroid.tiposDatos.TipoFormaPago;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;

public class VerVentaFragment extends Fragment {

    private VerVentaViewModel mViewModel;

    public static VerVentaFragment newInstance() {
        return new VerVentaFragment();
    }

    View root;

    String id_orden="", tipo="", nit="", numero="",formaPago="", fechaCreado="", proceso="", estado="",error="";
    TextView tnumero,tfecha,thoras,tprecio,tproceso,tcobrar,tsaldo;
    EditText edescuento, eabono;
    Button beditar;

    AlmacenDatos almacenDatos;

    Spinner slista;
    ArrayAdapter<String> adaptadorSpinner;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ver_venta, container, false);

        MainActivity.actualFrame="frame";

        final Bundle bundle=getArguments();
        if (bundle!=null){
            id_orden=getArguments().getString("id_orden");
            numero=getArguments().getString("numero");
            formaPago=getArguments().getString("formaPago");
            fechaCreado=getArguments().getString("fechaCreado");
            proceso=getArguments().getString("proceso");
            estado=getArguments().getString("estado");
        }

        tnumero=root.findViewById(R.id.tnumero);
        tfecha=root.findViewById(R.id.tfecha);
        tcobrar=root.findViewById(R.id.tcobrar);
        thoras=root.findViewById(R.id.thoras);
        tprecio=root.findViewById(R.id.tprecio);
        edescuento=root.findViewById(R.id.edescuento);
        eabono=root.findViewById(R.id.eabono);
        tsaldo=root.findViewById(R.id.tsaldo);
        tproceso=root.findViewById(R.id.tproceso);
        beditar=root.findViewById(R.id.beditar);
        slista=root.findViewById(R.id.slista);

        nit=cedula(getActivity());
        tipo=tipo(getActivity());
        almacenDatos = new BDPHP(getActivity());

        if (estado.equals("4")){
            tcobrar.setVisibility(TextView.VISIBLE);
        }
        if(estado.equals("5")||estado.equals("6")){
            edescuento.setEnabled(false);
            eabono.setEnabled(false);
            slista.setEnabled(false);
            beditar.setVisibility(Button.GONE);
        }




        adaptadorSpinner=new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoFormaPago.getDatosTipoFormaPago());
        adaptadorSpinner.setDropDownViewResource(R.layout.vista_texto_spinner);
        slista.setAdapter(adaptadorSpinner);
        slista.setSelection(Integer.valueOf(formaPago));

        almacenDatos.leerVenta(id_orden, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                try {
                    JSONObject objecto = datos.getJSONObject(0);

                    if (objecto.length() > 1) {

                        tnumero.setText(numero);
                        tfecha.setText(fechaCreado);
                        thoras.setText(objecto.getString("horas_trabajo"));
                        tprecio.setText(objecto.getString("valor_venta"));
                        edescuento.setText(objecto.getString("descuento"));
                        eabono.setText(objecto.getString("abono"));
                        tsaldo.setText(objecto.getString("saldo"));
                        tproceso.setText(proceso);

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
        });

        beditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String descuento=edescuento.getText().toString();
                final String abono=eabono.getText().toString();

                formaPago= String.valueOf(slista.getSelectedItemPosition());

                String mensaje = "¿Desea editar la venta?";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(mensaje)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                almacenDatos.editarVenta(id_orden, descuento, abono, formaPago, new RespuestasPHP() {
                                    @Override
                                    public void leerRespuesta(String respuesta) {

                                        if (respuesta.equals("1")){

                                            Bundle bundle = new Bundle();
                                            bundle.putString("id_orden", id_orden);
                                            bundle.putString("numero", numero);
                                            bundle.putString("formaPago", formaPago);
                                            bundle.putString("fechaCreado", fechaCreado);
                                            bundle.putString("proceso", proceso);
                                            bundle.putString("estado", estado);

                                            Navigation.findNavController(v).navigate(R.id.action_id_frame_ver_ventas_actualizar, bundle);
                                        }else if (respuesta.equals("3")) {

                                            mensajeEntendido(getActivity(), "No ha sido posible editar la venta.\n" +
                                                    "La venta no existe.");

                                        } else {
                                            mensajeEntendido(getActivity(), "Se ha producido un error al intentar editar venta.\n" +
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

            }
        });

        tcobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String mensaje = "¿Desea facturar la venta?";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(mensaje)
                        .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                    almacenDatos.facturarVenta(id_orden, new RespuestasPHP() {
                                        @Override
                                        public void leerRespuesta(String respuesta) {
                                            if (respuesta.equals("1")){

                                                Bundle bundle = new Bundle();
                                                bundle.putString("id_orden", id_orden);
                                                bundle.putString("numero", numero);
                                                bundle.putString("formaPago", formaPago);
                                                bundle.putString("fechaCreado", fechaCreado);
                                                bundle.putString("proceso", proceso);
                                                bundle.putString("estado", "5");

                                                Navigation.findNavController(v).navigate(R.id.action_id_frame_ver_ventas_actualizar, bundle);
                                            }else if (respuesta.equals("3")) {

                                                mensajeEntendido(getActivity(), "No ha sido posible facturar la venta.\n" +
                                                        "La venta no existe.");

                                            } else {
                                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar facturar venta.\n" +
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
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VerVentaViewModel.class);
        // TODO: Use the ViewModel
    }

}
