package com.haku.molar.controller.doctor.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_doctor_historialCitas {
    void onSuccessHistorial(ArrayList<model_cita>listaCitas);
    void onErrorhoraHistorial(String mensaje);
}
