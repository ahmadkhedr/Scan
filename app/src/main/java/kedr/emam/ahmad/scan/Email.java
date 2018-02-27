package kedr.emam.ahmad.scan;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.acl.Group;

public class Email extends AppCompatActivity {
RadioGroup radioGroup;
MyHelper myHelper;
    SQLiteDatabase sqldb ;
    EditText edEmail;
   String email;
    Cursor c;
    int Check;

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(Email.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        radioGroup = findViewById(R.id.Radiogroup);
        myHelper = new MyHelper(this);
        sqldb = myHelper.getWritableDatabase();//you missed this line..
        c = null;


        SharedPreferences x = getSharedPreferences("data",MODE_PRIVATE);
        email = x.getString("mail"," ");

    }

    public void Convandsend(View view) {
      if (radioGroup.getCheckedRadioButtonId() == R.id.radioexel) {
            //excel

                Convertexcel("MyBackUp.csv");
                if (Check == 1) { // if file Export
                    Send("ScanBackup/MyBackUp.csv", email);
                }
            
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.radiotext) {



                Convert("MyBackUp.txt");
                if(Check == 1 ) { // if file Export
                    Send("ScanBackup/MyBackUp.txt", email);
                }

        }
    }

    public void Convert(String Filnameandtype ){


        try {
            c = sqldb.rawQuery("select * from Data", null); //Data Name of Table
            int rowcount = 0;
            int colcount = 0;
            File sdCardDir = new File(Environment.getExternalStorageDirectory()+File.separator + "ScanBackup");
            if (!sdCardDir.exists()) {   ///If these File (ScanBackup) not found you must Creat IT
                sdCardDir.mkdirs();
            }
            String filename = Filnameandtype; // "MyBackUp.csv" or "MyBackUp.txt"
            // the name of the file to export with

            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();

//                for (int i = 1; i < colcount; i++) {
//                    if (i != colcount - 1) {
//
//                        bw.write(c.getColumnName(i) + ",");
//
//                    } else {
//
//                        bw.write(c.getColumnName(i));
//
//                    }
//                }
//                bw.newLine();

                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);

                    for (int j = 1; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ",");
                        else
                            bw.write(c.getString(j)+",");
                    }
                    bw.newLine();
                }
                bw.flush();

            }
            Toast.makeText(this, "Exported Successfully", Toast.LENGTH_SHORT).show();
            Check=1;
        } catch (Exception ex) {
            if (sqldb.isOpen()) {
                sqldb.close();
                Toast.makeText(this, "UnExported Successfully", Toast.LENGTH_SHORT).show();
                Check=0;
                Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        } finally {

        }


    }
    public void Convertexcel(String Filnameandtype ){


        try {
            c = sqldb.rawQuery("select * from Data", null); //Data Name of Table
            int rowcount = 0;
            int colcount = 0;
            File sdCardDir = new File(Environment.getExternalStorageDirectory()+File.separator + "ScanBackup");
            if (!sdCardDir.exists()) {   ///If these File (ScanBackup) not found you must Creat IT
                sdCardDir.mkdirs();
            }
            String filename = Filnameandtype; // "MyBackUp.csv" or "MyBackUp.txt"
            // the name of the file to export with

            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();

                for (int i = 1; i < colcount; i++) {
                    if (i != colcount - 1) {

                        bw.write(c.getColumnName(i) + ",");

                    } else {

                        bw.write(c.getColumnName(i));

                    }
                }
                bw.newLine();

                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);

                    for (int j = 1; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ",");
                        else
                            bw.write(c.getString(j));
                    }
                    bw.newLine();
                }
                bw.flush();

            }
            Toast.makeText(this, "Exported Successfully", Toast.LENGTH_SHORT).show();
            Check=1;
        } catch (Exception ex) {
            if (sqldb.isOpen()) {
                sqldb.close();
                Toast.makeText(this, "UnExported Successfully", Toast.LENGTH_SHORT).show();
                Check=0;
                Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        } finally {

        }


    }
    private void Send(String Attach_location,String ToEmail){

        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Attach_location);
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {ToEmail};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "QR/Barcode Reader app");

        emailIntent .putExtra(Intent.EXTRA_TEXT, "This mail include an attachment file contains items inventory\n" +
                "Created by QR /Barcode Reader mobile app");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Email.this, "Permission denied to Write your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}