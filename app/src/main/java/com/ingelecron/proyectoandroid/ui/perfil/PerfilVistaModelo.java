package com.ingelecron.proyectoandroid.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilVistaModelo extends ViewModel {

    private MutableLiveData<String> mText;

    public PerfilVistaModelo() {
        mText = new MutableLiveData<>();
        mText.setValue("Perfil");
    }

    public LiveData<String> getText() {
        return mText;
    }
}