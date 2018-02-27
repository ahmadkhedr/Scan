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

public class Inventory extends AppCompatActivity {

    private EditText num,text;
    private String ok;
    private MyHelper helper;
    private Integer  cc;
    private SQLiteDatabase db;
    Boolean fromShowItem;
    Bundle i;
    int postionmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        text =findViewById(R.id.text);

       // name =findViewById(R.id.name);
        num =findViewById(R.id.num);
        helper = new MyHelper(this);
        db =helper.getWritableDatabase();
        fromShowItem = false; //to recognize If Activty Run from MainActivity or From ViewData Activity
         i = getIntent().getExtras();
        if(i!=null){
            text.setText(i.getString("code"));
           // name.setText(i.getString("name"));
            num.setText(i.getString("quantity"));
            postionmark = i.getInt("postion");
       fromShowItem = true;  //From ViewData
        }



    }

    public void save(View view) {
        String code = text.getText().toString();
     //   String namo = name.getText().toString();
        String number = num.getText().toString();

        if (Checkempty() == 1) {
            ContentValues row = new ContentValues();
            row.put("code", code);
          //  row.put("name", namo);
            row.put("quantity", number);
            int a = -1;
            Intent ww = new Intent(this, Inventory.class);
            if(fromShowItem == false) { //From MainActivity Just Insert
                a = (int) db.insert("Data", null, row);
            }else if (fromShowItem == true){ //From ViewData Update
                //Update Row
                a = db.update("Data",row,"id = ?",new String[]{i.getString("id")});
                ww.putExtra("position",postionmark);
            }

            if (a > -1) {//code to save the data in the Sqlite database then see it beside other products in a table
                Toast.makeText(this, "Saved ", Toast.LENGTH_SHORT).show();


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

    public int Checkempty() {
        if (text.getText().toString().isEmpty()  || num.getText().toString().isEmpty()) {

            return 0;
        }
        else {

            return 1;
        }
    }
}

