package com.haku.molar.controller.assistant.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_assistant_listarCitasPorCancelar {
    void onSuccessListar(ArrayList<model_cita> listaActivas);
    void onErrorListar(String mensaje);
}
