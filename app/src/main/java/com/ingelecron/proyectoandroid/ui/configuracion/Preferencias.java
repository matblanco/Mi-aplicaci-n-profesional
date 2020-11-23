package com.ingelecron.proyectoandroid.ui.configuracion;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;

public class Preferencias extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        MainActivity.actualFrame="frame";
        addPreferencesFromResource(R.xml.preferencias);
    }
}
