package com.haku.molar.controller.admin.interfaces;

import com.haku.molar.model.admin.model_Admin;

import java.util.ArrayList;

public interface Callback_admin_reporteGeneral {
    void onSuccessReporte(String[]datos);
    void onErrorReporte(String mensaje);
    void onSuccessReporteMes(ArrayList<model_Admin> model_admins);
    void onErrorReporteMes(String mensaje);
}
