package com.ingelecron.proyectoandroid;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //retardo de inicio de 3 segundos
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}
