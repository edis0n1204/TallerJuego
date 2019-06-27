package com.example.tallerjuego;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.example.tallerjuego.LoginActivity.enviarPost;
import static com.example.tallerjuego.LoginActivity.loading;
import static com.example.tallerjuego.LoginActivity.mostrarProgreso;
import static com.example.tallerjuego.LoginActivity.objJSON;
import static com.example.tallerjuego.LoginActivity.ocultarProgreso;

/**
 * Created by aejarroyo on 08/03/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerPrinterHandler extends FingerprintManager.AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private Context appContext;
    SharedPreferences myPreferences;
    TextView txtStatus;


    public FingerPrinterHandler(Context context) {
        appContext = context;
        myPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        txtStatus = ((Activity)appContext).findViewById(R.id.item_status);
    }
    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }
    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        txtStatus.setText("Fallo la autentificacion\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        txtStatus.setText("Authentication help\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        txtStatus.setText("Autentificacion Fallida.");
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

//        Toast.makeText(appContext,
//                "Authentication succeeded.",
//                Toast.LENGTH_LONG).show();
        loginBiometria();

    }
    public void loginBiometria(){
        mostrarProgreso("Iniciando Sesión");
        Thread tr= new Thread() {
            @Override
            public void run() {
                final String res= enviarPost(myPreferences.getString("user",""), myPreferences.getString("pass",""));
                ((Activity)appContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r= objJSON(res);
                        ocultarProgreso();
                        if(r>0){
                            Intent i= new Intent(appContext,MainActivity.class);
                            i.putExtra("Usuario", myPreferences.getString("user",""));
                            appContext.startActivity(i);
                        }else{
                            Toast.makeText(appContext,"Usuario o pass incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }
    public String enviarPost(String usuario, String pass) {
        String parametros = "usuario=" + usuario + "&pass=" + pass;
        HttpURLConnection connection = null;
        String respuesta = "";
        try {
            URL url = new URL("https://teamsoftinnovation.000webhostapp.com/prueba/valida.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length","" + Integer.toString(parametros.getBytes().length));

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream = new Scanner(connection.getInputStream());

            while (inStream.hasNextLine())
                respuesta += (inStream.nextLine());
        } catch (Exception e) {
        }
        return respuesta.toString();
    }

    public int objJSON(String rspta) {
        int res = 0;
        try {
            JSONArray json = new JSONArray(rspta);
            if (json.length() > 0)
                res = 1;
        } catch (Exception e) {
        }
        return res;

    }
}
