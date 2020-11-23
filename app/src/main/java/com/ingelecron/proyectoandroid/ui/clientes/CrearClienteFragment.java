package com.ingelecron.proyectoandroid.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.ingelecron.proyectoandroid.tiposDatos.TipoCiudad;
import com.ingelecron.proyectoandroid.tiposDatos.TipoDepartamento;
import com.ingelecron.proyectoandroid.tiposDatos.TipoPais;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.empresa;
import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class CrearClienteFragment extends Fragment {

    private CrearClienteViewModel mViewModel;

    View root;

    EditText enit,enombre, ecelular,ecorreo,edireccion;
    Button bcrear;
    private Spinner spais, sestado, sciudad;
    private int  pais2 = 0, estado2 = 0, ciudad2 = 0, parg2 = 0;
    String tipo="", cedula="", empresa;

    Bundle bundle;

    ArrayAdapter<String> adaptadorp, adaptadord, adaptadorc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_crear_cliente, container, false);

        MainActivity.actualFrame="frame";

        tipo=tipo(getActivity());
        cedula=cedula(getActivity());
        empresa=empresa(getActivity());

        enit=root.findViewById(R.id.enit);
        enombre=root.findViewById(R.id.enombre);
        ecelular=root.findViewById(R.id.ecelular);
        ecorreo=root.findViewById(R.id.ecorreo);
        edireccion=root.findViewById(R.id.edireccion);
        bcrear= root.findViewById(R.id.bcrear);

        bundle=getArguments();
        if(bundle!=null){
            enit.setText(getArguments().getString("nit"));
        }

        spais = (Spinner) root.findViewById(R.id.spais);
        adaptadorp = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoPais.getDatosPais());
        adaptadorp.setDropDownViewResource(R.layout.vista_texto_spinner);
        spais.setAdapter(adaptadorp);
        spais.setSelection(43);
        spais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                parg2 = arg2;
                sestado = (Spinner) root.findViewById(R.id.sestado);
                sciudad = (Spinner) root.findViewById(R.id.sciudad);

                adaptadord = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoDepartamento.getDatosDepartamento(arg2));
                adaptadord.setDropDownViewResource(R.layout.vista_texto_spinner);
                sestado.setAdapter(adaptadord);
                sestado.setSelection(0);
                sestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                        adaptadorc = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoCiudad.getDatosCiudad(arg2, parg2));
                        adaptadorc.setDropDownViewResource(R.layout.vista_texto_spinner);
                        sciudad.setAdapter(adaptadorc);
                        sciudad.setSelection(0);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });


                adaptadorp.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        bcrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nit=enit.getText().toString();
                String nombre=enombre.getText().toString();
                String correo=ecorreo.getText().toString();
                String celular=ecelular.getText().toString();
                String direccion=edireccion.getText().toString();

                AlmacenDatos almacenDatos=new BDPHP(getActivity());

                int departamentociudad=0;

                pais2 = spais.getSelectedItemPosition();
                if (pais2 == 43) {
                    estado2 = sestado.getSelectedItemPosition();
                    departamentociudad = sestado.getSelectedItemPosition();
                } else if (pais2 == 0) {
                    estado2 = 0;
                    departamentociudad=0;
                } else {
                    estado2 = 0;
                    departamentociudad = 34;
                }
                ciudad2 = sciudad.getSelectedItemPosition();


              almacenDatos.crearCliente(nit, nombre, correo, celular, direccion, String.valueOf(pais2), String.valueOf(departamentociudad), String.valueOf(ciudad2), cedula, empresa, tipo, new RespuestasPHP() {
                  @Override
                  public void leerRespuesta(String respuesta) {

                      if (respuesta.equals("1")) {

                          mensajeEntendido(getActivity(), "¡Cliente creado con exito!");
                          getActivity().onBackPressed();

                      } else if (respuesta.equals("3")) {

                          mensajeEntendido(getActivity(), "No ha sido posible crear el cliente.\n" +
                                  "El cliente ya se encuentra creado.");

                      } else {
                          mensajeEntendido(getActivity(), "Se ha producido un error al intentar crear cliente.\n" +
                                  "Por favor intentelo nuevamente");
                      }
                  }
            });


            }
        });

        return root;
    }


}
