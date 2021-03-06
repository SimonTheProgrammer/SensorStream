package com.example.maurer.sensorstream.Frontend;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maurer.sensorstream.DB.MTCDatabaseOpenHelper;
import com.example.maurer.sensorstream.MainActivity;
import com.example.maurer.sensorstream.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notfallkontakthinzufuegen extends AppCompatActivity {

    public static Activity act;
    NotfallKontaktDaten kontaktDaten;

    public Notfallkontakthinzufuegen() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notfallkontakthinzufuegen);
        kontaktDaten = new NotfallKontaktDaten();

        findViewById(R.id.cbEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked){
                    kontaktDaten.emailTrue = true;}
                else{
                    kontaktDaten.emailTrue = false;}
            }
        });

        findViewById(R.id.cbSMS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked){
                    kontaktDaten.smsTrue = true;}
                else{
                    kontaktDaten.smsTrue = false;}
            }
        });

        findViewById(R.id.btnKontaktHinzufuegen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText vn = (EditText) findViewById(R.id.etVorname);
                String vorname = vn.getText().toString();
                EditText nn = (EditText) findViewById(R.id.etNachname);
                String nachname = nn.getText().toString();
                EditText tel = (EditText) findViewById(R.id.etTelefonnummer);
                String telefon = tel.getText().toString();
                EditText em = (EditText) findViewById(R.id.etEmail);
                String email = em.getText().toString();
                if (vorname!=null && !vorname.equals("")&&nachname!=null&&!nachname.equals("")
                        &&telefon!=null && !telefon.equals("")&&email!=null && isEmailValid(email)){
                    kontaktDaten.vorname = vorname;
                    kontaktDaten.nachname = nachname;
                    kontaktDaten.telefonnummer = telefon;
                    kontaktDaten.emailadresse = email;

                    MTCDatabaseOpenHelper db = new MTCDatabaseOpenHelper(act);
                    ContentValues cv = new ContentValues();
                    cv.put("Vorname", kontaktDaten.vorname);
                    cv.put("Nachname", kontaktDaten.nachname);
                    cv.put("TelefonNr", kontaktDaten.telefonnummer);
                    cv.put("EMail", kontaktDaten.emailadresse);
                    if (kontaktDaten.emailTrue)
                        cv.put("Senden_Mail", 1);
                    else
                        cv.put("Senden_Mail", 0);

                    if (kontaktDaten.smsTrue)
                        cv.put("Senden_Sms", 1); //true
                    else
                        cv.put("Senden_Sms", 0); //false
                    SQLiteDatabase write = db.getWritableDatabase();
                    write.insertWithOnConflict("Kontaktdaten", null, cv, SQLiteDatabase.CONFLICT_FAIL);

                    startActivity(new Intent(Notfallkontakthinzufuegen.this, MainActivity.class));
                }else if (isEmailValid(email)==false){
                    Toast.makeText(Notfallkontakthinzufuegen.this, "EMail-Adresse nicht korrekt!", Toast.LENGTH_LONG).show();
                    Log.e("Kontakt","Email falsch!");
                }else{
                    Toast.makeText(Notfallkontakthinzufuegen.this, "falsche Eingabe!", Toast.LENGTH_LONG).show();
                    Log.e("Kontakt","Fehler :(");
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}