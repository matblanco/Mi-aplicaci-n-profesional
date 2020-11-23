package com.ingelecron.proyectoandroid.ui.ordenes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrdenVistaModelo extends ViewModel {

    private MutableLiveData<String> mText;

    public OrdenVistaModelo() {
        mText = new MutableLiveData<>();
        mText.setValue("Ventas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}