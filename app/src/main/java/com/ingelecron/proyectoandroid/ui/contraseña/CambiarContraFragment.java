package com.ingelecron.proyectoandroid.ui.contraseña;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ingelecron.proyectoandroid.R;

public class CambiarContraFragment extends Fragment {

    private CambiarContraViewModel mViewModel;

    View root;

    MaterialButton mbenviar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_cambiar_contra, container, false);

        mbenviar=root.findViewById(R.id.mbenviar);
        mbenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;
    }



}
