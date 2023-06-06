package com.haku.molar.controller.doctor.interfaces;

public interface Callback_doctor_detallesCitaPaciente {
    void onSuccessDetallesCita(String[] datos);
    void onErrorDetallesCita(String mensaje);
    void onSuccesTerminarCita();
    void OnErrorTerminarCita(String mensaje);
}
