package com.ingelecron.proyectoandroid.ui.contraseña;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

public class OlvidoContraFragment extends Fragment {

    private OlvidoContraViewModel mViewModel;

   View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_olvido_contra, container, false);

        MainActivity.actualFrame="frame";

        return root;
    }



}
