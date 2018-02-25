package kedr.emam.ahmad.scan;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        radioGroup = findViewById(R.id.Radiogroup);
        myHelper = new MyHelper(this);
        sqldb = myHelper.getWritableDatabase();//you missed this line..
        c = null;

        edEmail = findViewById(R.id.edEmail);
        SharedPreferences x = getSharedPreferences("data",MODE_PRIVATE);
        email = x.getString("mail"," ");
        edEmail.setText(email);
    }

    public void Convandsend(View view) {
      if (radioGroup.getCheckedRadioButtonId() == R.id.radioexel) {
            //excel
            if (edEmail.getText().toString().isEmpty()) {

                Toast.makeText(this, "plz .. Enter your Email first ", Toast.LENGTH_SHORT).show();
            } else {
                Convertexcel("MyBackUp.csv");
                if (Check == 1) { // if file Export
                    Send("ScanBackup/MyBackUp.csv", edEmail.getText().toString());
                }
            }
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.radiotext) {


            if (edEmail.getText().toString().isEmpty()) {

                Toast.makeText(this, "plz .. Enter your Email first ", Toast.LENGTH_SHORT).show();
            } else {
                Convert("MyBackUp.txt");
                if(Check == 1 ) { // if file Export
                    Send("ScanBackup/MyBackUp.txt", edEmail.getText().toString());
                }
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
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");

        emailIntent .putExtra(Intent.EXTRA_TEXT, "This mail include an attachment file contains items inventory\n" +
                "Created by QR /Barcode Reader mobile app");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));

    }

}