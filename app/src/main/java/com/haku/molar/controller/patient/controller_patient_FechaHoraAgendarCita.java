package com.haku.molar.controller.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.model.cita.Callback_cita;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.model.patient.model_Patient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class controller_patient_FechaHoraAgendarCita extends AppCompatActivity implements Callback_cita {
    String hora="",fecha="",motivo="",descripcion="",nombre="", matricula="", matriculaDoctor="";
    Button hora1,hora2,hora3,hora4,hora5,hora6;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_fecha_hora_agendar_cita);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        motivo = intent.getStringExtra("motivo");
        descripcion = intent.getStringExtra("descripcion");

        calendarView = findViewById(R.id.view_patient_calendarCita);
        hora1 = findViewById(R.id.view_patient_fechaHora_btn1);
        hora2 = findViewById(R.id.view_patient_fechaHora_btn2);
        hora3 = findViewById(R.id.view_patient_fechaHora_btn3);
        hora4 = findViewById(R.id.view_patient_fechaHora_btn4);
        hora5 = findViewById(R.id.view_patient_fechaHora_btn5);
        hora6 = findViewById(R.id.view_patient_fechaHora_btn6);

        horasCita(getDiaCalendar());

        //Listeners
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                limpiarBtn();
                hora="";
                String currentDate = dayOfMonth + "-" + (month+1) + "-" + year;
                fecha = currentDate;
                horasCita(currentDate);
            }
        });
        hora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = "12:30";
                hora1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E09F1F")));
                hora1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Button[] botones = {hora2, hora3, hora4, hora5, hora6};
                for (Button boton : botones) {
                    if (boton.isEnabled()) {
                        boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
                        boton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                    }
                }

            }
        });
        hora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = "1:30";
                hora2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E09F1F")));
                hora2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Button[] botones = {hora1, hora3, hora4, hora5, hora6};
                for (Button boton : botones) {
                    if (boton.isEnabled()) {
                        boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
                        boton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                    }
                }
            }
        });
        hora3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = "2:30";
                hora3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E09F1F")));
                hora3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Button[] botones = {hora1, hora2, hora4, hora5, hora6};
                for (Button boton : botones) {
                    if (boton.isEnabled()) {
                        boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
                        boton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                    }
                }
            }
        });
        hora4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = "4:30";
                hora4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E09F1F")));
                hora4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Button[] botones = {hora1, hora2, hora3, hora5, hora6};
                for (Button boton : botones) {
                    if (boton.isEnabled()) {
                        boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
                        boton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                    }
                }
            }
        });
        hora5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = "5:30";
                hora5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E09F1F")));
                hora5.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Button[] botones = {hora1, hora2, hora3, hora4, hora6};
                for (Button boton : botones) {
                    if (boton.isEnabled()) {
                        boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
                        boton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                    }
                }
            }
        });
        hora6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hora = "6:30";
                hora6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E09F1F")));
                hora6.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Button[] botones = {hora1, hora2, hora3, hora4, hora5};
                for (Button boton : botones) {
                    if (boton.isEnabled()) {
                        boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
                        boton.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
                    }
                }
            }
        });
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click");
            }
        });
    }
    public void agendarCita(View view){
        if (hora.equals("")){
            Toast.makeText(this, "Seleccione una hora disponible", Toast.LENGTH_SHORT).show();
        }else{
            obtenerNumDoctor();
            //model_cita model_cita = new model_cita(generarId(),fecha,hora,motivo,"1","1",matricula,this,this,descripcion);
            //model_cita.agendarCita();
        }
    }
    public String generarId(){
        String id = "0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());
        id = currentDateandTime.substring(6,10)+hora.replace(":","")+matricula.substring(7,10);
        return id;
    }
    public String getDiaCalendar(){
        String date = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        date = dayOfMonth+"-"+(month+1)+"-"+year;
        System.out.println("getDiaCalendar: "+date);
        fecha = date;
        return date;
    }

    public void horasCita(String dia){
        model_cita model_cita = new model_cita(dia,this, this);
        model_cita.horasCita();
    }
    public void limpiarBtn(){
        hora1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
        hora1.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
        hora1.setEnabled(true);
        hora2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
        hora2.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
        hora2.setEnabled(true);

        hora3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
        hora3.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
        hora3.setEnabled(true);

        hora4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
        hora4.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
        hora4.setEnabled(true);

        hora5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
        hora5.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
        hora5.setEnabled(true);

        hora6.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3F4E1")));
        hora6.setTextColor(ColorStateList.valueOf(Color.parseColor("#1C6BA4")));
        hora6.setEnabled(true);
    }
    public String obtenerNumDoctor(){
        String matriculaDoctorPro="";
        model_Patient model_patient = new model_Patient();
        model_patient.obtenerNumDoctor();
        return matriculaDoctorPro;
    }
    @Override
    public void onSuccesshoraCitas(String[] datos) {
            for (int i=0; i< datos.length; i++){
                System.out.println("Datos"+i+" "+datos[i]);
                if (datos[i]==null){

                }else{
                    if (datos[i].equals("12:30")){
                        hora1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        hora1.setTextColor(Color.WHITE);
                        hora1.setEnabled(false);
                    } else
                    if (datos[i].equals("1:30")){
                        hora2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        hora2.setTextColor(Color.WHITE);
                        hora2.setEnabled(false);
                    }else
                    if (datos[i].equals("2:30")){
                        hora3.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        hora3.setTextColor(Color.WHITE);
                        hora3.setEnabled(false);
                    }else
                    if (datos[i].equals("4:30")){
                        hora4.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        hora4.setTextColor(Color.WHITE);
                        hora4.setEnabled(false);
                    }else
                    if (datos[i].equals("5:30")){
                        hora5.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        hora5.setTextColor(Color.WHITE);
                        hora5.setEnabled(false);
                    }else
                    if (datos[i].equals("6:30")){
                        hora6.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        hora6.setTextColor(Color.WHITE);
                        hora6.setEnabled(false);
                    }
                }
            }
    }

    @Override
    public void onErrorhoraCitas(String mensaje) {
        Toast.makeText(this, "Error al recuperar disponibilidad: "+mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessAgendarCita() {
        Intent intent = new Intent(this, controller_patient_AvisoCitaAgendada.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onErrorAgendarCita(String mensaje) {
        Intent intent = new Intent(this, controller_patient_AvisoCitaError.class);
        startActivity(intent);
        finish();
    }
}