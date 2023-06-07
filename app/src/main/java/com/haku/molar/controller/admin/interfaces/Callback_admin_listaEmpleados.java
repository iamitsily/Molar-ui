package com.haku.molar.controller.admin.interfaces;

import com.haku.molar.model.admin.model_Admin;
import com.haku.molar.model.assistant.model_Assistant;

import java.util.ArrayList;

public interface Callback_admin_listaEmpleados {
    void onSuccessLista(ArrayList<model_Admin> model_assistants);
    void onErrorLista(String mensaje);
}
