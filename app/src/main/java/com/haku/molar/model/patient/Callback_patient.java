package com.haku.molar.model.patient;

public interface Callback_patient {
    void onSuccessbuscarDatos(String[] datos);
    void onErrorbuscarDatos(String mensaje);
    void onSuccessudpatebyUser(String[] datos);
    void onErrorudpatebyUser(String mensaje);

}
