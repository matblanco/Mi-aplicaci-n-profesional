package com.ingelecron.proyectoandroid.ui.clientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClienteVistaModelo extends ViewModel {

    private MutableLiveData<String> mText;

    public ClienteVistaModelo() {
        mText = new MutableLiveData<>();
        mText.setValue("Clientes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}