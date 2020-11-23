package com.ingelecron.proyectoandroid.ui.regsitro;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

public class TipoRegistroFragment extends Fragment {

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_tipo_registro, container, false);

        MainActivity.actualFrame="frame";

        LinearLayout loperador=root.findViewById(R.id.loperador);
        loperador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("tipo", "1");
                Navigation.findNavController(v).navigate(R.id.action_id_frame_tipo_registro_to_id_frame_registro_uno,bundle);

            }
        });

        LinearLayout lempresa=root.findViewById(R.id.lempresa);
        lempresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("tipo", "2");
                Navigation.findNavController(v).navigate(R.id.action_id_frame_tipo_registro_to_id_frame_registro_uno,bundle);

            }
        });

        return root;
    }

}
