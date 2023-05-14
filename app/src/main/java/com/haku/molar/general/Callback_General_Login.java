package com.haku.molar.general;

public interface Callback_General_Login {
    void onSuccess(String[] datos);
    void onError(String mensaje);
    void onSuccessForgetPass();
    void onErrorForgetPass();
    void onSuccesValidarEmail();
    void onErrorValidarEmail(String mensaje);

}
