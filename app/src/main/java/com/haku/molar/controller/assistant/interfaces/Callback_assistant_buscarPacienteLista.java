package com.haku.molar.controller.assistant.interfaces;

import com.haku.molar.model.assistant.model_Assistant;

import java.util.ArrayList;

public interface Callback_assistant_buscarPacienteLista {
    void onSuccessLista(ArrayList<model_Assistant> model_assistants);
    void onErrorLista(String mensaje);
}
