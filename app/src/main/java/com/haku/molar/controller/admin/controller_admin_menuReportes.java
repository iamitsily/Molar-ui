package com.haku.molar.controller.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.haku.molar.R;
import com.haku.molar.controller.admin.interfaces.Callback_admin_reporteGeneral;
import com.haku.molar.model.admin.model_Admin;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.exceptions.InvalidImageException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class controller_admin_menuReportes extends AppCompatActivity implements Callback_admin_reporteGeneral {
    String matricula, nombre, rol, sexo, totalPacientes, totalMedicos, totalSecretarios, totalCitas="40", totalCitasAgendadas="20", totalCitasReagendadas="10", totalCitasCanceladas="5", totalCitasTerminadas="5";
    Button btnReporteGeneral, btnReporteMes;
    BottomNavigationView menuNav;
    ProgressDialog progressDialog;
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isAceptado ->{
                if (isAceptado) Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show();
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_menu_reportes);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        menuNav = findViewById(R.id.menu_admin_menu);
        menuNav.setSelectedItemId(R.id.menu_admin_reportes);
        btnReporteGeneral = findViewById(R.id.admin_reporte_reporteGeneral);
        btnReporteMes = findViewById(R.id.admin_reporte_reportePorMes);

        progressDialog = new ProgressDialog(this);

        btnReporteGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Generando reporte");
                progressDialog.setMessage("Espere un momento, se esta generando el reporte.");
                progressDialog.setCancelable(false);
                progressDialog.show();
                model_Admin model_admin = new model_Admin(controller_admin_menuReportes.this,controller_admin_menuReportes.this);
                model_admin.reporteGeneral();
            }
        });

        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_admin_empleados:
                    Intent intent1 = new Intent(this, controller_admin_menuEmpleados.class);
                    intent1.putExtra("matricula",matricula);
                    intent1.putExtra("nombre", nombre);
                    intent1.putExtra("rol", rol);
                    intent1.putExtra("sexo", sexo);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_usuarios:
                    Intent intent2 = new Intent(this, controller_admin_menuUsuario.class);
                    intent2.putExtra("matricula",matricula);
                    intent2.putExtra("nombre", nombre);
                    intent2.putExtra("rol", rol);
                    intent2.putExtra("sexo", sexo);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_citas:
                    Intent intent3 = new Intent(this, controller_admin_HistorialCitas.class);
                    intent3.putExtra("matricula",matricula);
                    intent3.putExtra("nombre", nombre);
                    intent3.putExtra("rol", rol);
                    intent3.putExtra("sexo", sexo);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_home:
                    Intent intent4 = new Intent(this, controller_admin_menuAdmin.class);
                    intent4.putExtra("matricula",matricula);
                    intent4.putExtra("nombre", nombre);
                    intent4.putExtra("rol", rol);
                    intent4.putExtra("sexo", sexo);
                    startActivity(intent4);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Los reportes se guardan en\n/documents/reportesMolar").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(true).show();

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Molar");
        builder.setMessage("¿Desea salir de la aplicación?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(false).show();
    }
    private void verificarPermisosGeneral(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            crearPFDGeneral();
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Snackbar.make(new View(getApplicationContext()), "Este permiso es necesario para crear el archivo", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            });
        }else{
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
    private void crearPFDGeneral(){
        try {
            String carpeta = "/reportesMolar";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+carpeta;

            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }

            String nombreBase = "ReporteGeneral.pdf";
            File archivoBase = new File(dir, nombreBase);
            File nuevoArchivo = obtenerNuevoArchivo(archivoBase);

            FileOutputStream fos = new FileOutputStream(nuevoArchivo);
            Document document = new Document();
            PdfWriter.getInstance(document, fos);

            document.open();
            Paragraph tituloPro = new Paragraph("Reporte General\n", FontFactory.getFont("arial",22, Font.BOLD, BaseColor.BLACK));
            tituloPro.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloPro);

            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = formatter.format(currentDate.getTime());

            Paragraph titulo = new Paragraph("Molar | Administrador | "+nombre+" | "+matricula+" | "+formattedDate+"\n", FontFactory.getFont("arial",18, Font.NORMAL, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Drawable d = getDrawable(R.drawable.logo);
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] bitmapdata = stream.toByteArray();
            Image image = Image.getInstance(bitmapdata);
            image.scaleToFit(100, 100);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            Paragraph postTitulo = new Paragraph("El presente informe general tiene como objetivo " +
                    "proporcionar una visión completa y detallada de la gestión del consultorio dental" +
                    " a través de la aplicación Molar.", FontFactory.getFont("arial",12, Font.NORMAL, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(postTitulo);

            Paragraph cuerpoPro = new Paragraph("\nEstadisticas Usuarios\n", FontFactory.getFont("arial",20, Font.BOLD, BaseColor.BLACK));
            cuerpoPro.setAlignment(Element.ALIGN_LEFT);
            document.add(cuerpoPro);

            Paragraph cuerpo = new Paragraph("Total de pacientes: "+totalPacientes+
                    "\nTotal Doctores: "+totalMedicos+
                    "\nTotal Secreatarios: "+totalSecretarios+"\n\n", FontFactory.getFont("arial",16, Font.BOLD, BaseColor.BLACK));
            cuerpo.setAlignment(Element.ALIGN_LEFT);
            document.add(cuerpo);

            Paragraph cuerpoProPro = new Paragraph("Estadisticas Citas\n\n", FontFactory.getFont("arial",20, Font.BOLD, BaseColor.BLACK));
            cuerpoProPro.setAlignment(Element.ALIGN_LEFT);
            document.add(cuerpoProPro);

            PdfPTable table = new PdfPTable(5);
            table.addCell("Total Citas");
            table.addCell("Agendadas");
            table.addCell("Reagendadas");
            table.addCell("Canceladas");
            table.addCell("Terminadas");

            table.addCell(totalCitas);
            table.addCell(totalCitasAgendadas);
            table.addCell(totalCitasReagendadas);
            table.addCell(totalCitasCanceladas);
            table.addCell(totalCitasTerminadas);
            document.add(table);

            Paragraph estadisticasGenrales = new Paragraph("\nEstadisticas Generales\n\n", FontFactory.getFont("arial",20, Font.BOLD, BaseColor.BLACK));
            estadisticasGenrales.setAlignment(Element.ALIGN_LEFT);
            document.add(estadisticasGenrales);

            Paragraph estadisticas = new Paragraph("Porcentaje de asistencia: "+(Integer.parseInt(totalCitasTerminadas)*100)/Integer.parseInt(totalCitas)+"%"+
                    "\nPorcentaje de reagendar: "+(Integer.parseInt(totalCitasReagendadas)*100)/Integer.parseInt(totalCitas)+"%"+
                    "\nPorcentaje de cancelación: "+(Integer.parseInt(totalCitasCanceladas)*100)/Integer.parseInt(totalCitas)+"%"+
                    "\nPorcentaje de agendadas: "+(Integer.parseInt(totalCitasAgendadas)*100)/Integer.parseInt(totalCitas)+"%"+"\n\n", FontFactory.getFont("arial",16, Font.BOLD, BaseColor.BLACK));
            estadisticas.setAlignment(Element.ALIGN_LEFT);
            document.add(estadisticas);

            Paragraph finalString = new Paragraph("Con amor Molar <3 | molar.haku@gmail.com", FontFactory.getFont("arial",10, Font.BOLD, BaseColor.BLACK));
            finalString.setAlignment(Element.ALIGN_CENTER);

            document.add(finalString);

            document.close();
            progressDialog.dismiss();
            Toast.makeText(this, "Reporte generado con exito", Toast.LENGTH_SHORT).show();
            Uri pdfUri = FileProvider.getUriForFile(this, "com.haku.molar.fileprovider", nuevoArchivo);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (DocumentException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private File obtenerNuevoArchivo(File archivoBase) {
        String nombreArchivo = archivoBase.getName();
        String nombreSinExtension = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));
        File directorio = archivoBase.getParentFile();

        int contador = 0;
        File nuevoArchivo = new File(directorio, nombreSinExtension + contador + extension);

        while (nuevoArchivo.exists()) {
            contador++;
            nuevoArchivo = new File(directorio, nombreSinExtension + contador + extension);
        }

        return nuevoArchivo;
    }

    @Override
    public void onSuccessReporte(String[] datos) {
        totalPacientes = datos[0];
        totalMedicos = datos[1];
        totalSecretarios = datos[2];

        totalCitas = datos[3];
        totalCitasAgendadas = datos[4];
        totalCitasReagendadas = datos[5];
        totalCitasCanceladas = datos[6];
        totalCitasTerminadas = datos[7];
        verificarPermisosGeneral();
    }

    @Override
    public void onErrorReporte(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}