package com.haku.molar.controller.patient.interfaces;

public interface Callback_patient_reagendarCitas {
    void onSuccesshoraCitas(String[] datos);
    void onErrorhoraCitas(String mensaje);
    void onSuccessReagendarCita();
    void onErrorReagendarCita(String mensaje);
}
