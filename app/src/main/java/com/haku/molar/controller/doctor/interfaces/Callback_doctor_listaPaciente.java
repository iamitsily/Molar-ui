package com.haku.molar.controller.doctor.interfaces;

import com.haku.molar.model.doctor.model_Doctor;

import java.util.ArrayList;

public interface Callback_doctor_listaPaciente {
    void onSuccessLista(ArrayList<model_Doctor> listaPaciente);
    void onErrorLista(String mensaje);
}
