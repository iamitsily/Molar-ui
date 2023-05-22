package com.haku.molar.controller.patient.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_patient_listarCitasActivas {
    void onSuccessListarCitasActivas(ArrayList<model_cita> listaActivas);
    void onErrorListarCitasActivas(String mensaje);
}
