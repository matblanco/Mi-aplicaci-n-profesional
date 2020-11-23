package com.ingelecron.proyectoandroid.ui.ayuda;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AyudaFragment extends Fragment {

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_ayuda, container, false);

        MainActivity.actualFrame="frame";

        return root;
    }

}
