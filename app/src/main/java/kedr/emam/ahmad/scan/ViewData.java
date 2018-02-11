package kedr.emam.ahmad.scan;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {
private TextView etView;
private MyHelper helper;
private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        etView = (TextView) findViewById(R.id.etView);
        helper = new MyHelper(this);
        db = helper.getWritableDatabase();
        String[] ok = {"id","name","num"};
        Cursor pointer = db.query("Data",ok,null,null,null,null,null,null);
        String data = " ";
       while( pointer.moveToNext()){
           data += " " + pointer.getInt(0) + " " + pointer.getString(1)+ " " + pointer.getInt(2) + ("\n");
           etView.setText(data);
       }
    }
}
