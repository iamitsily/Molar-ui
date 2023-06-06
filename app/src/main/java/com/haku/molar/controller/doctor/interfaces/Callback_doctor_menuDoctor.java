package com.haku.molar.controller.doctor.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_doctor_menuDoctor {
    void onSuccesListar(ArrayList<model_cita>model_citas);
    void onErrorListar(String mensaje);
}
