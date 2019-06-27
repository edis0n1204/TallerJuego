package com.example.tallerjuego;


import android.Manifest;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.tallerjuego.modelos.Alumno;
import com.example.tallerjuego.modelos.Olimpiada;
import com.example.tallerjuego.modelos.Pregunta;
import com.example.tallerjuego.modelos.Respuesta;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginActivity extends AppCompatActivity{
    private EditText txtEmail;
    private EditText txtPassword;

    public static ProgressDialog loading;
    SharedPreferences myPreferences;
    String user;
    String pass;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private static final String KEY_NAME = "llave_secreta";
    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout bottomSheetView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loading= new ProgressDialog(this);
        bottomSheetView = findViewById(R.id.bottom_sheet);
      cargarDatos();
        if (myPreferences.getBoolean("biometria", false))
            cargarBiometria();

    }

    private void cargarDatos() {

        Alumno a1= new Alumno("Edison Augusto","Vaca Rocha","edison123","edison","Col. Simon Bolivar",0,0,0,"");
        a1.save();
        Alumno a2= new Alumno("Carlos Eduardo","Villegas Acuña","ceva123","ceva","Col. Simon Bolivar",0,0,0,"");
        a2.save();
        Olimpiada ol = new Olimpiada(1,"Olimpiada Matematica", "entra ya", "");
        ol.save();
        Olimpiada ol2 = new Olimpiada(2,"Olimpiada Ciencia", "entra ya", "");
        ol2.save();
        Olimpiada ol3 = new Olimpiada(3,"Olimpiada Biologia", "entra ya", "");
        ol3.save();




        ol = Olimpiada.findById(Olimpiada.class, (long) 1);
        Pregunta p1 = new Pregunta(1,"¿Cuanto es 10 elevado al cuadrado?",10,1);
        p1.olimpiada = ol;
        p1.save();
        Respuesta re1 = new Respuesta(1,"50",false,1);
        re1.pregunta = p1;
        re1.save();
        Respuesta re2 = new Respuesta(2,"100",true,1);
        re2.pregunta = p1;
        re2.save();
        Respuesta re3 = new Respuesta(3,"20",false,1);
        re3.pregunta = p1;
        re3.save();
        Respuesta re4 = new Respuesta(4,"110",false,1);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(2,"¿El triangulo con 3 lados iguales se llama?",10,1);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"isósceles",false,2);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"escaleno",false,2);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"equilatero",true,2);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"igualito",false,2);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(3,"¿Que son los números primos?",10,1);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"numeros que terminan en 0",false,3);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"numeros que son multiplos de 2",false,3);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"numeros mayores a 0",false,3);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"numeros divisibles entre el mismo y 1",true,3);
        re4.pregunta = p1;
        re4.save();
        p1 = new Pregunta(4,"¿Cuanto es 50 x 3 - 20?",10,1);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"130",true,4);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"150",false,4);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"200",false,4);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"180",false,4);
        re4.pregunta = p1;
        re4.save();
        p1 = new Pregunta(5,"¿Cuantos es 20 x 10?",10,1);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"100",false,5);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"2000",false,5);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"200",true,5);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"30",false,5);
        re4.pregunta = p1;
        re4.save();


        ol = Olimpiada.findById(Olimpiada.class, (long) 2);
        p1 = new Pregunta(6,"¿Cómo se llama el componente mínimo que forma a los seres vivos?",10,2);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(7,"¿El proceso por el que una célula se divide para formar dos células hijas se llama?",10,2);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(8,"La información genética en las células se localiza:",10,2);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(9,"¿Con qué respira una ballena?",10,2);
        p1.olimpiada = ol;
        p1.save();
        ol = Olimpiada.findById(Olimpiada.class, (long) 3);
        p1 = new Pregunta(10,"Los cromosomas están formados por:",10,2);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(11,"¿Diferencia entre células vegetales y animales?",10,3);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(12,"¿Quién fue Louis Pasteur?",10,3);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(13,"¿Qué quiere decir \"esterilizado\"?",10,3);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(14,"¿Quién fue Charles Darwin?",10,3);
        p1.olimpiada = ol;
        p1.save();
        p1 = new Pregunta(15,"¿Qué es una proteína?",10,3);
        p1.olimpiada = ol;
        p1.save();


        ol = new Olimpiada(6,"Olimpiada Fisica","entra ya", "");
        ol.save();

        ol = Olimpiada.findById(Olimpiada.class, (long) 4);
        p1 = new Pregunta(1,"¿Cuáles se relacionan con un cuerpo en movimiento que emite la luz?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"oido",false,1);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"vista",true,1);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"olfato",false,1);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"tacto",false,1);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(2,"¿¿Un átomo esta compuesto de??",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"proton",false,2);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"neon",false,2);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"electron",true,2);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"ninguno",false,2);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(3,"¿Qué cantidad no depende de la masa o el volumen?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"energia",false,3);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"tiempo",false,3);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Fuerza",true,3);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"ninguno",false,3);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(4,"¿Cuanto es 50 x 3 - 20?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"130",true,4);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"150",false,4);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"200",false,4);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"180",false,4);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(5,"¿El fenómeno en el cual no hay alteración en la naturaleza del cuerpo, se llama:?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Quimico",false,5);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Fisico",false,5);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Quimico y Fisico",true,5);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Ninguna",false,5);
        re4.pregunta = p1;
        re4.save();


        p1 = new Pregunta(6,"¿La fuerza que mantiene unidas entre sí las moléculas de un cuerpo se llaman:?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Diamante",false,6);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Ninguna",false,6);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Afinidad",false,6);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Adherencia",true,6);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(7,"¿La fuerza que mantiene unidas entre sí las moléculas de un cuerpo se llaman:?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Diamante",false,7);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Ninguna",false,7);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Afinidad",false,7);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Adherencia",true,7);
        re4.pregunta = p1;
        re4.save();


        p1 = new Pregunta(8,"¿Los astronautas tienen menos masa:?",10,4);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"En la luna",true,8);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"en la tierra",false,8);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Igual masa en la Luna y en la Tierra",false,8);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Ninguna",true,8);
        re4.pregunta = p1;
        re4.save();

        ol = new Olimpiada(5,"Olimpiada de Historia", "entra ya", "");
        ol.save();

        //ol5 = Olimpiada.findById(Olimpiada.class, (long) 1);
        p1 = new Pregunta(1,"¿En que año Colon descubrio America?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"1492",true,1);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"1942",false,1);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"1249",false,1);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"1429",false,1);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(2,"¿Alrededor de que mar vivieron los egipcios, fenicios, israelitas, griegos y romanos?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Mar Rojo",false,2);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Mar Muerto",false,2);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Mar Mediterraneo",true,2);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Mar Arabigo",false,2);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(3,"¿Cómo se les llama a los juegos celebrados en honor a los dioses del Olimpo?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Campeonatos",false,3);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Fiestas patrias",false,3);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Ceremonias",false,3);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Olimpiadas",true,3);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(4,"¿Fue el artista que pinto la capilla Sixtina en el vaticano?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Miguel Ángel",true,4);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Marco Polo",false,4);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Edison Vaca",false,4);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Leonardo da Vinci",false,4);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(5,"¿Es el invento que caracteriza a la revolución industrial?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Transistores",false,5);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Electricidad",false,5);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Maquina de vapor",true,5);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Ferrocarriles",false,5);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(6,"¿Que personaje llego al poder de Francia mediante un golpe de estado?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Adolf Hitler",false,6);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Cristobal Colon",false,6);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Napoleon Bonaparte",true,6);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Vladimir Putin",false,6);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(7,"¿fue el autor de la teoría Heliocéntrica (1534)?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Galileo Galilei",false,7);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Albert Heinstein",false,7);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Nicolás Copérnico",true,7);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Isaac Newton",false,7);
        re4.pregunta = p1;
        re4.save();

        p1 = new Pregunta(8,"¿Quien invento el telefono?",10,5);
        p1.olimpiada = ol;
        p1.save();
        re1 = new Respuesta(1,"Nicola Tesla",false,8);
        re1.pregunta = p1;
        re1.save();
        re2 = new Respuesta(2,"Leonardo da Vinci",false,8);
        re2.pregunta = p1;
        re2.save();
        re3 = new Respuesta(3,"Alexander Graham Bell",true,8);
        re3.pregunta = p1;
        re3.save();
        re4 = new Respuesta(4,"Aristoteles",false,8);
        re4.pregunta = p1;
        re4.save();

    }

    public static void mostrarProgreso(String mensaje){
        loading.setMessage(mensaje);
        loading.show();
    }
    public static void ocultarProgreso(){
        loading.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void cargarBiometria() {
        bottomSheetView.setVisibility(View.VISIBLE);
        keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager =
                (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "La seguridad de la pantalla de bloqueo no está habilitada en la configuración",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "Permiso de autenticación de huellas dactilares no habilitado",
                    Toast.LENGTH_LONG).show();

            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Registrar al menos una huella digital en la configuración",
                    Toast.LENGTH_LONG).show();
            return;
        }

        generateKey();

        if (cipherInit()) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            FingerPrinterHandler helper = new FingerPrinterHandler(this);
            helper.startAuth(fingerprintManager, cryptoObject);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    public void llamar(View view){
        mostrarProgreso("Iniciando Sesión");
        Thread tr= new Thread() {
            @Override
            public void run() {
                final String res= enviarPost(txtEmail.getText().toString(), txtPassword.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r= objJSON(res);
                        ocultarProgreso();
                        if(r>0){
                            Intent i= new Intent(getApplicationContext(),MainActivity.class);
                            i.putExtra("Usuario", txtEmail.getText().toString());
                            SharedPreferences.Editor editor = myPreferences.edit();
                            editor.putString("user",txtEmail.getText().toString());
                            editor.putString("pass",txtPassword.getText().toString());
                            editor.commit();
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuario o pass incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    public static String enviarPost(String usuario, String pass) {
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

    public static int objJSON(String rspta) {
        int res = 0;
        try {
            JSONArray json = new JSONArray(rspta);
            if (json.length() > 0)
                res = 1;
        } catch (Exception e) {
        }
        return res;

    }


    public void cancelar(View view) {
        bottomSheetView.setVisibility(View.GONE);
    }
}
