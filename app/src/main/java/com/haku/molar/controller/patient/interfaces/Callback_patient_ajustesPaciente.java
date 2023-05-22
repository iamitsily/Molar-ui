package com.haku.molar.controller.patient.interfaces;

public interface Callback_patient_ajustesPaciente {
    void onSuccessbuscarDatos(String[] datos);
    void onErrorbuscarDatos(String mensaje);
    void onSuccessUpdatebyUser();
    void onErrorudpatebyUser(String mensaje);
    void onSuccessObternerPass(String[]datos);
    void onErrorObternerPass(String mensaje);
}
