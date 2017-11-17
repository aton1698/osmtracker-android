package com.example.brandon.pruebadescarga;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void descargar(View view){
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            //primero especificaremos el origen de nuestro archivo a descargar utilizando
            //la ruta completa
            URL url = new URL("https://raw.githubusercontent.com/LabExperimental-SIUA/osmtracker-android/layouts/layouts/acceso.xml");

            //establecemos la conexión con el destino
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //establecemos el método jet para nuestra conexión
            //el método setdooutput es necesario para este tipo de conexiones
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //por último establecemos nuestra conexión y cruzamos los dedos <img draggable="false" class="emoji" alt="😛" src="https://s.w.org/images/core/emoji/72x72/1f61b.png">
            urlConnection.connect();

            //vamos a establecer la ruta de destino para nuestra descarga
            //para hacerlo sencillo en este ejemplo he decidido descargar en
            //la raíz de la tarjeta SD
            File SDCardRoot = Environment.getExternalStorageDirectory();

            //vamos a crear un objeto del tipo de fichero
            //donde descargaremos nuestro fichero, debemos darle el nombre que
            //queramos, si quisieramos hacer esto mas completo
            //cogeríamos el nombre del origen
            File file = new File(SDCardRoot,"ejemplo.xml");

            //utilizaremos un objeto del tipo fileoutputstream
            //para escribir el archivo que descargamos en el nuevo
            FileOutputStream fileOutput = new FileOutputStream(file);

            //leemos los datos desde la url
            InputStream inputStream = urlConnection.getInputStream();

            //obtendremos el tamaño del archivo y lo asociaremos a una
            //variable de tipo entero
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;

            //creamos un buffer y una variable para ir almacenando el
            //tamaño temporal de este
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {

                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;

            }
            //cerramos
            fileOutput.close();


            //y gestionamos errores
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
