package com.haku.molar.model.cita;

import java.util.ArrayList;

public interface Callback_cita {

    void onSuccesshoraCitas(String[] datos);
    void onErrorhoraCitas(String mensaje);
    void onSuccessAgendarCita();
    void onErrorAgendarCita(String mensaje);
    void onSuccessNumDoctor(String num);
    void onErrorNumDoctor(String mensaje);
    void onSuccessdisponibilidadDoctor(String matricula);
    void onErrordisponibilidadDoctor(String mensaje);
    void onSuccessHistorial(ArrayList<model_cita> historial);
    void onErrorhoraHistorial(String mensaje);
}
