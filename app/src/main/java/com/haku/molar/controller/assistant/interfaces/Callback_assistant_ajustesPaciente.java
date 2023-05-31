package com.haku.molar.controller.assistant.interfaces;

public interface Callback_assistant_ajustesPaciente {
    void onSuccessbuscarDatos(String[] datos);
    void onErrorbuscarDatos(String mensaje);
    void onSuccessUpdatebyUser();
    void onErrorudpatebyUser(String mensaje);
    void onSuccessObternerPass(String[]datos);
    void onErrorObternerPass(String mensaje);
    void onSuccessUpdateIcon();
    void onErrorUpdateIcon(String mensaje);
}
