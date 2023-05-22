package com.haku.molar.controller.patient.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_patient_historialCitas {
    void onSuccessHistorial(ArrayList<model_cita> historial);
    void onErrorhoraHistorial(String mensaje);
}
