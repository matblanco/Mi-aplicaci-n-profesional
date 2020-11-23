package com.ingelecron.proyectoandroid.ui.ventas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import static com.ingelecron.proyectoandroid.MainActivity.drawer;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.navController;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;

public class VentasFragment extends Fragment {

    private VentasViewModel mViewModel;

    public static VentasFragment newInstance() {
        return new VentasFragment();
    }

    View root;

    String cedula="", tipo="", id_orden="", error="";

    TextView tvacio;
    LinearLayout lagregar;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObjVenta> lista;
    private RecyclerView.Adapter adaptador;

    AlmacenDatos almacenDatos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ventas, container, false);


        tipo=tipo(getActivity());
        cedula=cedula(getActivity());

        if(tipo.equals("1")){
            MainActivity.actualFrame="frameVentas";
        }else {
            MainActivity.actualFrame="inicio";
        }

        tvacio=root.findViewById(R.id.tvacio);
        recyclerView=root.findViewById(R.id.rcv_venta);
        lagregar= root.findViewById(R.id.lagregar);

        lista=new ArrayList<>();
        adaptador=new AdaptadorVenta(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);

        almacenDatos = new BDPHP(getActivity());
        almacenDatos.listaVenta(cedula, tipo, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {


                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjVenta objVenta = new ObjVenta();
                            objVenta.setId(objecto.getString("id_venta"));
                            objVenta.setIdorden(objecto.getString("id_orden"));
                            objVenta.setHorastrabajo(objecto.getString("horas_trabajo"));
                            objVenta.setValorventa(objecto.getString("valor_venta"));
                            objVenta.setDescuento(objecto.getString("descuento"));
                            objVenta.setAbono(objecto.getString("abono"));
                            objVenta.setSaldo(objecto.getString("saldo"));
                            objVenta.setFormapago(objecto.getString("forma_pago"));
                            objVenta.setFechavence(objecto.getString("fecha_vence"));
                            objVenta.setDiasfaltantes(objecto.getString("dias_faltantes"));
                            objVenta.setEstatus(objecto.getString("estatus"));
                            objVenta.setCredito(objecto.getString("credito"));
                            objVenta.setCondicion(objecto.getString("condicion"));
                            objVenta.setNumero(objecto.getString("numero_orden"));
                            objVenta.setFechaCreado(objecto.getString("fecha_creado"));
                            objVenta.setProceso(objecto.getString("proceso"));
                            objVenta.setServicio(objecto.getString("nombres"));
                            objVenta.setCliente(objecto.getString("nombrec"));

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objVenta);

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

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VentasViewModel.class);
        // TODO: Use the ViewModel
    }

}
