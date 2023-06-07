package com.haku.molar.controller.admin.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_admin_historialCitas {
    void onSuccessListar(ArrayList<model_cita> listaActivas);
    void onErrorListar(String mensaje);
}
