package br.com.pr.senai.restaurantecomabem;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import lombok.Getter;

public class Main extends Application {
    @SuppressLint("StaticFieldLeak")
    @Getter
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
