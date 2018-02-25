package kedr.emam.ahmad.scan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCode extends AppCompatActivity {
 private TextView text,itemcode,proname,nuum;
 private EditText name,num;
 private String ok;
 private MyHelper helper;
 private Integer  cc;
 private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_code);
        text =findViewById(R.id.text);

        name =findViewById(R.id.name);
        num =findViewById(R.id.num);
        helper = new MyHelper(this);
        db =helper.getWritableDatabase();

        Intent d = getIntent();
         ok = d.getStringExtra("number");

        text.setText(ok );
    }

    public void save(View view) {
        //code to save the data in the Sqlite data base then see it beside other products in a table
        String code = text.getText().toString();
        String namo = name.getText().toString();
        String number = num.getText().toString();

        ContentValues row = new ContentValues();
        row.put("code",code);
        row.put("name",namo);
        row.put("quantity",number);
        if(Checkempty()==1) {
            int a = (int) db.insert("Data", null, row);
            if (a > -1) {//code to save the data in the Sqlite database then see it beside other products in a table
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                Intent ww = new Intent(this, Camera.class);
                startActivity(ww);
                this.finish();
            } else {//if no insert
                Toast.makeText(this, "Not Saved = " + a, Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            Toast.makeText(this, "Something_empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void Cancel(View view) {

        this.finish();
    }

    public void saveandscan(View view) {

        //code to save the data in the Sqlite database then see it beside other products in a table
        String code = text.getText().toString();
        String namo = name.getText().toString();
        String number = num.getText().toString();

        ContentValues row = new ContentValues();
        row.put("code",code);
        row.put("name",namo);
        row.put("quantity",number);
        if (Checkempty()== 1) {
        int a = (int) db.insert("Data",null,row);

            if (a > -1) { //if insert done
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        Intent ww = new Intent(this, Camera.class);
        startActivity(ww);
        this.finish();
        } else { //if no insert
        Toast.makeText(this, "Not Saved = " + a, Toast.LENGTH_SHORT).show();
        }
}
      else {
             Toast.makeText(this, "Something_empty", Toast.LENGTH_SHORT).show();
        }
    }


    public int Checkempty() {
        if (text.getText().toString().isEmpty() ||  num.getText().toString().isEmpty()) {

            return 0;
        }
        else {

            return 1;
        }
    }

    }


