package kedr.emam.ahmad.scan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewCode extends AppCompatActivity {
 private TextView text,itemcode,proname,nuum;
 private EditText name,num;
 private String ok;
 private MyHelper helper;
 private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_code);
        text =findViewById(R.id.text);
        proname =findViewById(R.id.proname);
        nuum =findViewById(R.id.nuum);
        itemcode =findViewById(R.id.itemcode);
        name =findViewById(R.id.name);
        num =findViewById(R.id.num);
        helper = new MyHelper(this);
        db =helper.getWritableDatabase();

        Intent d = getIntent();
         ok = d.getStringExtra("number");
        text.setText(ok);
    }

    public void save(View view) {
        //code to save the data in the Sqlite data base then see it beside other products in a table
        String namo = name.getText().toString();
        int number = Integer.parseInt(num.getText().toString());
        ContentValues row = new ContentValues();
        row.put("name",namo);
        row.put("num",number);
        db.insert("Data",null,row);
    }

    public void Cancel(View view) {
        Intent c = new Intent(this,Camera.class);
        startActivity(c);
    }
}
