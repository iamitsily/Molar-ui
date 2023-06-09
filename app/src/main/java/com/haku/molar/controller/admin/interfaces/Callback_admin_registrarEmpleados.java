package com.haku.molar.controller.admin.interfaces;

public interface Callback_admin_registrarEmpleados {
    void onSuccessComprobarEmail();
    void onErrorComprobarEmail(String mensaje);
    void onSuccessComprobarTelefono();
    void onErrorComprobarTelefono(String mensaje);
}

