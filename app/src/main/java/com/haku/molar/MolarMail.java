package com.haku.molar;

import android.content.Context;

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
    String Remitente, RemitentePass ,Receptor, PassReceptor, MatriculaReceptor, NombreReceptor;
    Context context;

    public MolarMail() {
    }

    public MolarMail(String receptor, String passReceptor, String matriculaReceptor, String nombreReceptor, Context context) {
        Receptor = receptor;
        PassReceptor = passReceptor;
        MatriculaReceptor = matriculaReceptor;
        NombreReceptor = nombreReceptor;
        this.context = context;
    }

    public void sendMail() {
        Remitente = "molar.haku@gmail.com";
        RemitentePass = "itinfvuyckoiajdl";

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
            mimeMessage.setText("Haku Molar\nHola "+NombreReceptor+
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
