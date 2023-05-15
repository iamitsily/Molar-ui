package com.haku.molar.model.patient;

import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public interface Callback_patient {
    void onSuccessbuscarDatos(String[] datos);
    void onErrorbuscarDatos(String mensaje);
    void onSuccessudpatebyUser(String[] datos);
    void onErrorudpatebyUser(String mensaje);
    void OnSuccesslistarCitas(ArrayList<model_cita> citas);
    void OneErrorlistarCitas(String mensaje);
}
