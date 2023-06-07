package com.haku.molar.controller.admin.interfaces;

import com.haku.molar.model.admin.model_Admin;

import java.util.ArrayList;

public interface Callback_admin_listaPacientes
{
    void onSuccessLista(ArrayList<model_Admin> model_admins);
    void onErrorLista(String mensaje);
}
