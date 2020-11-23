package com.ingelecron.proyectoandroid.ui.operador;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OperadorVIstaModelo extends ViewModel {

    private MutableLiveData<String> mText;

    public OperadorVIstaModelo() {
        mText = new MutableLiveData<>();
        mText.setValue("Operadores");
    }

    public LiveData<String> getText() {
        return mText;
    }
}