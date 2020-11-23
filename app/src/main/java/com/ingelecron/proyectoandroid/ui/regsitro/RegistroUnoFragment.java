package com.ingelecron.proyectoandroid.ui.regsitro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.tiposDatos.TipoCiudad;
import com.ingelecron.proyectoandroid.tiposDatos.TipoDepartamento;
import com.ingelecron.proyectoandroid.tiposDatos.TipoPais;

public class RegistroUnoFragment extends Fragment {

    private RegistroUnoViewModel mViewModel;

   View root;

    TextView ttitulo;
    EditText enombre,edireccion,epnombre,esnombre,epapellido,esapellido;
    Button bsiguiente;
    Bundle bundle;

    private Spinner spais, sdepartamento, sciudad;
    private int pais2 = 0, estado2 = 0, ciudad2 = 0, parg2 = 0;
    String tipo="0";

    ArrayAdapter<String> adaptadorp, adaptadord, adaptadorc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root= inflater.inflate(R.layout.frame_registro_uno, container, false);

        bundle=getArguments();
        if(bundle!=null) {
            tipo= getArguments().getString("tipo");
        }


        ttitulo=root.findViewById(R.id.ttitulo);
        enombre=root.findViewById(R.id.enombre);
        edireccion=root.findViewById(R.id.edireccion);
        epnombre=root.findViewById(R.id.epnombre);
        esnombre=root.findViewById(R.id.esnombre);
        epapellido=root.findViewById(R.id.epapellido);
        esapellido=root.findViewById(R.id.esapellido);
        bsiguiente= root.findViewById(R.id.bsiguiente);

        if (tipo.equals("1")){
            ttitulo.setText("Nuevo operador");
            enombre.setVisibility(EditText.GONE);
            edireccion.setVisibility(EditText.GONE);
        }else {
            ttitulo.setText("Nueva empresa");
            epnombre.setVisibility(EditText.GONE);
            esnombre.setVisibility(EditText.GONE);
            epapellido.setVisibility(EditText.GONE);
            esapellido.setVisibility(EditText.GONE);
        }

        spais = root.findViewById(R.id.spais);
        adaptadorp = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoPais.getDatosPais());
        adaptadorp.setDropDownViewResource(R.layout.vista_texto_spinner);
        spais.setAdapter(adaptadorp);
        spais.setSelection(43);
        spais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                parg2 = arg2;
                sdepartamento = root.findViewById(R.id.sdepartamento);
                sciudad =  root.findViewById(R.id.sciudad);

                adaptadord = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoDepartamento.getDatosDepartamento(arg2));
                adaptadord.setDropDownViewResource(R.layout.vista_texto_spinner);
                sdepartamento.setAdapter(adaptadord);
                sdepartamento.setSelection(0);
                sdepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

        bsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre=enombre.getText().toString();
                String direccion=edireccion.getText().toString();
                String pnombre=epnombre.getText().toString();
                String snombre=esnombre.getText().toString();
                String papellido=epapellido.getText().toString();
                String sapellido=esapellido.getText().toString();

                int departamentociudad=0;

                pais2 = spais.getSelectedItemPosition();
                if (pais2 == 43) {
                    estado2 = sdepartamento.getSelectedItemPosition();
                    departamentociudad = sdepartamento.getSelectedItemPosition();
                } else if (pais2 == 0) {
                    estado2 = 0;
                    departamentociudad=0;
                } else {
                    estado2 = 0;
                    departamentociudad = 34;
                }
                ciudad2 = sciudad.getSelectedItemPosition();

                Bundle bundle = new Bundle();
                if(tipo.equals("1")){

                    bundle.putString("nombre", "");
                    bundle.putString("direccion", "");
                    bundle.putString("pnombre",pnombre );
                    bundle.putString("snombre", snombre);
                    bundle.putString("papellido", papellido );
                    bundle.putString("sapellido", sapellido);

                }else {
                    bundle.putString("nombre", nombre);
                    bundle.putString("direccion", direccion);
                    bundle.putString("pnombre","" );
                    bundle.putString("snombre", "");
                    bundle.putString("papellido", "" );
                    bundle.putString("sapellido", "");
                }

                bundle.putString("pais", String.valueOf(pais2));
                bundle.putString("estado", String.valueOf(departamentociudad));
                bundle.putString("ciudad", String.valueOf(ciudad2));
                bundle.putString("tipo", tipo);

                Navigation.findNavController(v).navigate(R.id.action_id_frame_registro_uno_to_id_frame_registro_dos,bundle);

            }
        });


        return root;
    }


}
