package com.haku.molar.controller.doctor.interfaces;

public interface Callback_doctor_ajustesDoctor {
    void onSuccessbuscarDatos(String[] datos);
    void onErrorbuscarDatos(String mensaje);
    void onSuccessUpdatebyUser();
    void onErrorudpatebyUser(String mensaje);
    void onSuccessObternerPass(String[]datos);
    void onErrorObternerPass(String mensaje);
    void onSuccessUpdateIcon();
    void onErrorUpdateIcon(String mensaje);

}
