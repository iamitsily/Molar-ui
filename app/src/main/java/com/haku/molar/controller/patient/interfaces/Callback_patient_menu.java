package com.haku.molar.controller.patient.interfaces;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_patient_menu {
    void OnSuccesslistarCitas(ArrayList<model_cita> citas);
    void OneErrorlistarCitas(String mensaje);
}
