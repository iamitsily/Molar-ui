package com.haku.molar.model.notificaciones;

import java.util.ArrayList;

public interface Callback_notificaciones {
    void onSuccessNotificaciones(ArrayList<model_General_Notificaciones> notificaciones);
    void onErrorNotificaciones(String errorMessage);
}
