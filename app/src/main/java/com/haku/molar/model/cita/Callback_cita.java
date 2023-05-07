package com.haku.molar.model.cita;

public interface Callback_cita {

    void onSuccesshoraCitas(String[] datos);
    void onErrorhoraCitas(String mensaje);
    void onSuccessAgendarCita();
    void onErrorAgendarCita(String mensaje);

}
