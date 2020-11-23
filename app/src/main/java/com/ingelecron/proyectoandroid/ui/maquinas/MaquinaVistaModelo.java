package com.ingelecron.proyectoandroid.ui.maquinas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaquinaVistaModelo extends ViewModel {

    private MutableLiveData<String> mText;

    public MaquinaVistaModelo() {
        mText = new MutableLiveData<>();
        mText.setValue("Maquinas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}