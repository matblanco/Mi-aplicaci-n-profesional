package com.ingelecron.proyectoandroid.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginVistaModelo extends ViewModel {

    private MutableLiveData<String> mText;

    public LoginVistaModelo() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
