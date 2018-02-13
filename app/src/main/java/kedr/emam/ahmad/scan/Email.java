package kedr.emam.ahmad.scan;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;

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
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        radioGroup = findViewById(R.id.Radiogroup);
        myHelper = new MyHelper(this);
        sqldb = myHelper.getWritableDatabase();//you missed this line..
        c = null;
        for (int i = 0; i < 10; i++) {
            ContentValues row = new ContentValues();
            row.put("name", "a" + i);
            row.put("num", "n" + i);
            row.put("code", "c" + i);
            sqldb.insert("Data", null, row);

        }
    }

    public void Convandsend(View view) {
            if(radioGroup.getCheckedRadioButtonId() == R.id.radioexel){

                try {
                    c = sqldb.rawQuery("select * from Data", null); //Data Name of Table
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = new File(Environment.getExternalStorageDirectory()+File.separator + "ScanBackup");
                    if (!sdCardDir.exists()) {   ///If these File (ScanBackup) not found you must Creat IT
                        sdCardDir.mkdirs();
                    }
                    String filename = "MyBackUp.csv";
                    // the name of the file to export with
                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);

                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();
                    if (rowcount > 0) {
                        c.moveToFirst();

                        for (int i = 0; i < colcount; i++) {
                            if (i != colcount - 1) {

                                bw.write(c.getColumnName(i) + ",");

                            } else {

                                bw.write(c.getColumnName(i));

                            }
                        }
                        bw.newLine();

                        for (int i = 0; i < rowcount; i++) {
                            c.moveToPosition(i);

                            for (int j = 0; j < colcount; j++) {
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
                } catch (Exception ex) {
                    if (sqldb.isOpen()) {
                        sqldb.close();
                        Toast.makeText(this, "UnExported Successfully", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                } finally {

                }

            }
else  if (radioGroup.getCheckedRadioButtonId()==R.id.radiotext){


                try {
                    c = sqldb.rawQuery("select * from Data", null); //Data Name of Table
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = new File(Environment.getExternalStorageDirectory()+File.separator + "ScanBackup");
                    if (!sdCardDir.exists()) {   ///If these File (ScanBackup) not found you must Creat IT
                      sdCardDir.mkdirs();
                    }
                    String filename = "MyBackUp.txt";
                    // the name of the file to export with
                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);

                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();
                    if (rowcount > 0) {
                        c.moveToFirst();

                        for (int i = 0; i < colcount; i++) {
                            if (i != colcount - 1) {

                                bw.write(c.getColumnName(i) + ",");

                            } else {

                                bw.write(c.getColumnName(i));

                            }
                        }
                        bw.newLine();

                        for (int i = 0; i < rowcount; i++) {
                            c.moveToPosition(i);

                            for (int j = 0; j < colcount; j++) {
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
                } catch (Exception ex) {
                    if (sqldb.isOpen()) {
                        sqldb.close();
                        Toast.makeText(this, "UnExported Successfully", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                } finally {

                }




            }
        }
    }

