package com.ingelecron.proyectoandroid.ui.clientes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
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

import java.util.ArrayList;
import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.empresa;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class ClienteFragment extends Fragment {

    private ClienteVistaModelo clienteVistaModelo;
    View root;

    String cedula="",empresa="", tipo="", nit="", error="";
    LinearLayout lagregar;
    RelativeLayout rbuscar;
    SearchView sbuscar;
    TextView tvacio, tagregar;

    //
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adaptador;
    private List<ObjCliente> lista;
    //

    AlmacenDatos almacenDatos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        clienteVistaModelo = ViewModelProviders.of(this).get(ClienteVistaModelo.class);

        root = inflater.inflate(R.layout.frame_cliente, container, false);

        MainActivity.actualFrame="frameClientes";
        tipo=tipo(getActivity());
        cedula=cedula(getActivity());
        empresa=empresa(getActivity());

        tvacio=root.findViewById(R.id.tvacio);
        sbuscar=root.findViewById(R.id.sbuscar);
        rbuscar= root.findViewById(R.id.rbuscar);
        rbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbuscar.setIconified(false);
            }
        });
        lagregar= root.findViewById(R.id.lagregar);
        tagregar= root.findViewById(R.id.tagregar);


        //

        recyclerView=root.findViewById(R.id.rcv_cliente);
        lista=new ArrayList<>();
        adaptador=new AdaptadorCliente(getActivity(), lista);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adaptador);

        //

        almacenDatos= new BDPHP(getActivity());
        almacenDatos.listaCliente(cedula, tipo, new LeerPHP() {
            @Override
            public void leerDatos(JSONArray datos) {
                for (int i = 0; i < datos.length(); i++) {

                    try {
                        JSONObject objecto = datos.getJSONObject(i);

                        if (objecto.length() > 1) {

                            ObjCliente objCliente = new ObjCliente();
                            objCliente.setNombre(objecto.getString("nombre"));
                            objCliente.setCondicion(objecto.getString("condicion"));
                            objCliente.setCelular(objecto.getString("celular"));
                            objCliente.setCorreo(objecto.getString("correo"));
                            objCliente.setPais(objecto.getString("pais"));
                            objCliente.setEstado(objecto.getString("estado"));
                            objCliente.setCiudad(objecto.getString("ciudad"));

                            tvacio.setVisibility(TextView.GONE);
                            lista.add(objCliente);

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

        if(tipo.equals("1")){
            tagregar.setText("Agregar");
        }

        lagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (tipo.equals("1")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View alertView = inflater.inflate(R.layout.vista_dialogo_edittext, null);
                    ImageView ititulo=alertView.findViewById(R.id.ititulo);
                    ititulo.setImageResource(R.drawable.ic_clientes);
                    TextView ttitulo=alertView.findViewById(R.id.ttitulo);
                    ttitulo.setText("Agregar cliente");
                    EditText edato=alertView.findViewById(R.id.edato);
                    edato.setHint("cedula cliente");
                    builder.setView(alertView);

                    final EditText tdato = alertView.findViewById(R.id.edato);

                    builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            final String nit = tdato.getText().toString();

                            almacenDatos.agregarCliente(nit, cedula, empresa, new RespuestasPHP() {
                                @Override
                                public void leerRespuesta(String respuesta) {

                                    if (respuesta.equals("1")) {

                                        mensajeEntendido(getActivity(), "¡Cliente agregado con exito!");

                                        Navigation.findNavController(v).navigate(R.id.action_id_frame_clientes_actualizar);

                                    } else if (respuesta.equals("3")) {

                                        mensajeEntendido(getActivity(), "No ha sido posible agregar al cliente.\n" +
                                                "La cedula del cliente no esta registrada.");

                                        Bundle bundle=new Bundle();
                                        bundle.putString("nit", nit);

                                        Navigation.findNavController(v).navigate(R.id.action_id_frame_clientes_to_id_frame_crear_cliente, bundle);

                                    } else if (respuesta.equals("4")) {

                                    mensajeEntendido(getActivity(), "No ha sido posible agregar al cliente.\n" +
                                            "El cliente ya habia sido agregado.");

                                    } else {

                                            mensajeEntendido(getActivity(), "Se ha producido un error al conectarse al servidor.\n" +
                                                    "Por favor intentelo nuevamente");

                                    }
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.show();

                }else {
                    Navigation.findNavController(v).navigate(R.id.action_nav_clientes_to_id_frame_crear_cliente);
                }
            }

        });


        return root;
    }

}

 /*
        SearchView searchView =  root.findViewById(R.id.sbuscar);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_buscar));*/
