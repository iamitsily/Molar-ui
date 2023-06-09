package com.haku.molar.controller.assistant.interfaces;

public interface Callback_assistant_registrarPaciente {
    void onSuccessComprobarEmail();
    void onErrorComprobarEmail(String mensaje);
    void onSuccessComprobarTelefono();
    void onErrorComprobarTelefono(String mensaje);
}
