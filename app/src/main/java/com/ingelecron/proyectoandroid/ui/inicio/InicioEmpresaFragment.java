package com.ingelecron.proyectoandroid.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ingelecron.proyectoandroid.R;

import static com.ingelecron.proyectoandroid.MainActivity.cedula;
import static com.ingelecron.proyectoandroid.MainActivity.empresa;
import static com.ingelecron.proyectoandroid.MainActivity.navController;
import static com.ingelecron.proyectoandroid.MainActivity.sesion;
import static com.ingelecron.proyectoandroid.MainActivity.tipo;

public class InicioEmpresaFragment extends Fragment {

    private InicioEmpresaVistaModelo mViewModel;

   View root;
    String cedula="", tipo="", empresa="",nempresa="";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root= inflater.inflate(R.layout.frame_inicio_empresa, container, false);

        cedula=cedula(getActivity());
        tipo=tipo(getActivity());
        empresa=empresa(getActivity());

        sesion(getActivity(),cedula, tipo, cedula, "");

        navController.navigate(R.id.action_id_frame_inicio_empresa_to_id_nav_ventas);
/*
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Registro exitoso")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }

                });
        builder.create();
        builder.show();*/


        return root;
    }



}
