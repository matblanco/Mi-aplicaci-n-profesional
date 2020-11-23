package com.ingelecron.proyectoandroid.ui.facturas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.LeerPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.tiposDatos.GetDatos;
import com.ingelecron.proyectoandroid.tiposDatos.NuevoDato;
import com.ingelecron.proyectoandroid.tiposDatos.TipoFormaPago;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;

public class VerFacturaFragment extends Fragment {

    private VerFacturaViewModel mViewModel;

    public static VerFacturaFragment newInstance() {
        return new VerFacturaFragment();
    }

    View root;

    String id_orden="", tipo="", nit="", numero="",formaPago="", fechaCreado="", proceso="", estado="",error="";
    TextView tnumero,tfecha,tcliente,tfpago,tservicio,tprecio,tdescuento,tsubtotal,tiva,ttotal,tvacio;
    LinearLayout lfactura;
    AlmacenDatos almacenDatos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ver_factura, container, false);

        MainActivity.actualFrame="frame";

        Bundle bundle=getArguments();
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
        tcliente=root.findViewById(R.id.tcliente);
        tfpago=root.findViewById(R.id.tfpago);
        tservicio=root.findViewById(R.id.tservicio);
        tprecio=root.findViewById(R.id.tprecio);
        tdescuento=root.findViewById(R.id.tdescuento);
        tsubtotal=root.findViewById(R.id.tsubtotal);
        tiva=root.findViewById(R.id.tiva);
        ttotal=root.findViewById(R.id.ttotal);
        lfactura=root.findViewById(R.id.lfactura);
        tvacio=root.findViewById(R.id.tvacio);

        nit=cedula(getActivity());
        tipo=tipo(getActivity());
        almacenDatos = new BDPHP(getActivity());
        almacenDatos.leerFactura(id_orden, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {

                try {
                    JSONObject objecto = datos.getJSONObject(0);

                    if (objecto.length() > 1) {

                        tnumero.setText(objecto.getString("numero_factura"));
                        tfecha.setText(objecto.getString("fecha_creado"));
                        tcliente.setText(objecto.getString("nombrec"));

                        String fpago=objecto.getString("forma_pago");

                        int valorFpago= Integer.valueOf(fpago);
                        GetDatos datosEstado = NuevoDato.elemento();
                        datosEstado.setTipoFormaPago(TipoFormaPago.values()[valorFpago]);
                        String nombreFpago=datosEstado.getTipoFormaPago().getTextoTipoFormaPago();

                        tfpago.setText(nombreFpago);
                        tservicio.setText(objecto.getString("nombres"));
                        tprecio.setText(objecto.getString("valor_venta"));
                        tdescuento.setText(objecto.getString("descuento"));
                        tsubtotal.setText(objecto.getString("subtotal"));
                        tiva.setText(objecto.getString("iva"));
                        ttotal.setText(objecto.getString("valor_total"));

                        tvacio.setVisibility(TextView.GONE);
                        lfactura.setVisibility(LinearLayout.VISIBLE);

                    } else {

                        tvacio.setVisibility(TextView.VISIBLE);
                        lfactura.setVisibility(LinearLayout.GONE);

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

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VerFacturaViewModel.class);
        // TODO: Use the ViewModel
    }

}
