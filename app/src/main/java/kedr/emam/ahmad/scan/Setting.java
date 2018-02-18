package kedr.emam.ahmad.scan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
    private TextView settingHeader,textView2;
    private EditText editText;
    private Button button2,button3,button4;
    String email;
    private MyHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingHeader =findViewById(R.id.settingHeader);
        textView2 =findViewById(R.id.textView2);
        editText =findViewById(R.id.editText);
        helper = new MyHelper(Setting.this);
        db = helper.getWritableDatabase();

        SharedPreferences x = getSharedPreferences("data",MODE_PRIVATE);
        email = x.getString("mail"," ");
        editText.setText(email);



    }

    public void save(View view) {
        String mail = editText.getText().toString();
        SharedPreferences E = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor edit  = E.edit();
        edit.putString("mail",mail);
        edit.commit();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    }

    public void backup(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/ScanBackup/");
        intent.setDataAndType(uri, "text/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

    public void wipe(View view) {
        db.delete("Data",null,null);
        db.execSQL("delete from "+ "Data");
        Toast.makeText(this, "All data has een deleted succesfully", Toast.LENGTH_SHORT).show();
    }
}
