package com.haku.molar.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MolarMail {
    String Remitente, RemitentePass="webysycthvywcvlf" ,Receptor, PassReceptor, MatriculaReceptor, NombreReceptor, codigoRecuperacion;
    Context context;
    MolarConfig molarConfig = new MolarConfig();

    public MolarMail() {
    }
    //controller_assistant_registrarPaciente
    public MolarMail(String receptor, String passReceptor, String matriculaReceptor, String nombreReceptor, Context context) {
        Receptor = receptor;
        PassReceptor = passReceptor;
        MatriculaReceptor = matriculaReceptor;
        NombreReceptor = nombreReceptor;
        this.context = context;
    }
    //controller_general_login -> recuperarPass
    public MolarMail(String receptor, String codigoRecuperacion, Context context) {
        Receptor = receptor;
        this.codigoRecuperacion = codigoRecuperacion;
        this.context = context;
    }
    public void sendMailRegistro() {
        System.out.println("sendMailRegistro");
        Remitente = "molar.haku@gmail.com";

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Remitente, RemitentePass);
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Receptor));
            mimeMessage.setSubject("Detalles de Cuenta - Molar");
            mimeMessage.setText("Haku Molar\n\nHola "+NombreReceptor+
                    " te damos la bienvenida a Molar,\npara nosotros es un placer que formes parte de nuestra familia.\n\n" +
                    "A continuación tus datos de acceso: \n\n" +
                    "Matricula: "+MatriculaReceptor+"\n" +
                    "Contraseña: "+PassReceptor+"\n\n" +
                    "Te recomendamos cambiar tu contraseña en Perfil -> Ajustes de cuenta -> Configuracion de perfil\n\n" +
                    "Gracias por confiar en nosotros.\n" +
                    "Con amor, Molar <3\n\n" +
                    "Email generado automaticamente, no responder.");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMailRegistroRedMovil(){
        System.out.println("sendMailRegistroRedMovil");
        String url = molarConfig.getDomainAzure()+"/general/email.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Enviado correctamente")) {
                    System.out.println("sendMailRegistro exitoso");
                }else {
                    System.out.println("sendMailRegistro fallido");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("destino",Receptor);
                params.put("asunto","Detalles de Cuenta - Molar");
                params.put("mensaje","Haku Molar<br><br>Hola " + NombreReceptor + " te damos la biendenida a Molar, <br>para nosotros es un placer que formes parte de nuestra familia.<br><br>A continuación tus datos de acceso: <br><br>Matricula: "+ MatriculaReceptor + "<br>Contraseña: "+ PassReceptor + "<br><br>Te recomendamos cambiar tu Contraseña en Perfil -> Ajustes de cuenta -> Configuración de perfil<br><br>Gracias por confiar en osotros.<br>Con amor, Molar <3<br><br>Email generado automaticamente, no responder.");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void codeMailFP() {
        Remitente = "molar.haku@gmail.com";

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Remitente, RemitentePass);
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Receptor));
            mimeMessage.setSubject("Código de autentificación - Molar");
            mimeMessage.setText("Haku Molar\n\nHola! "+
                    " Recibimos una solicitud para restablecer su contraseña de Molar,\nSi no ha solicitado un restablecimiento de contraseña, puede ignorar este correo electronico.\n\n" +
                    "Codigo de recuperación: \n\n" +
                    "Codigo: "+codigoRecuperacion+"\n\n" +
                    "Ingrese el codigo en la aplicación, este codigo expira en cuanto cierre la app o pantalla de recuperación\n\n" +
                    "Si tiene alguna pregunta o necesita ayuda adicional, por favor contactenos al correo molar.haku@gmail.com\n" +
                    "Con amor, Molar <3\n\n" +
                    "Email generado automaticamente, no responder.");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void codeMailFPRedMovil(){
        System.out.println("sendMailRegistroRedMovil");
        String url = molarConfig.getDomainAzure()+"/general/email.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Enviado correctamente")) {
                    System.out.println("sendMailRegistro exitoso");
                }else {
                    System.out.println("sendMailRegistro fallido");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("destino",Receptor);
                params.put("asunto","Código de autentificación - Molar");
                params.put("mensaje","Haku Molar<br><br>Hola! Recibimos una solicitud para restablecer su contraseña de Molar,<br>Si no ha solicitado un restablecimiento de contraseña, puede ignorar este correo electronico.<br><br>Codigo de recuperación: <br><br>Codigo: "+ codigoRecuperacion +"<br><br>Ingrese el codigo en la aplicación, este codigo expira en cuanto cierrre la app o pantalla de recuperación<br><br>Con amor, Molar <3<br><br>Email generado automaticamente, no responder.");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public String getRemitente() {
        return Remitente;
    }

    public void setRemitente(String remitente) {
        Remitente = remitente;
    }

    public String getRemitentePass() {
        return RemitentePass;
    }

    public void setRemitentePass(String remitentePass) {
        RemitentePass = remitentePass;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String receptor) {
        Receptor = receptor;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
