package com.ingelecron.proyectoandroid.ui.inicio;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

import static com.ingelecron.proyectoandroid.MainActivity.drawer;
import static com.ingelecron.proyectoandroid.MainActivity.navController;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {


    public InicioFragment() {
        // Required empty public constructor
    }

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root=inflater.inflate(R.layout.frame_inicio, container, false);

        MainActivity.actualFrame="inicio";

        //((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        String tipo=tipo(getActivity());

        if(tipo.equals("1")){

            navController.navigate(R.id.action_nav_inicio_to_id_frame_inicio_operario);

        }else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            navController.navigate(R.id.action_nav_inicio_to_id_frame_inicio_empresa);
        }


        return root;
    }


}
