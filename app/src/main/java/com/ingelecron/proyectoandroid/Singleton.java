package com.ingelecron.proyectoandroid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public final class Singleton {

    private static Singleton instancia;
    private RequestQueue requestQueue;
    private static Context contexto;

    private Singleton(Context contexto) {
        this.contexto = contexto;
        requestQueue = getRequestQueue();
    }

    public static synchronized Singleton getInstance(Context contexto) {
        if (instancia == null) {
            instancia = new Singleton(contexto);
        }
        return instancia;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
