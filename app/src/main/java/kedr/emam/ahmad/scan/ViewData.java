package kedr.emam.ahmad.scan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    private ListView ListView;
    private  String[] name;
    private  String[] code;
    private  String[] num;
private TextView etView;
private MyHelper helper;
private SQLiteDatabase db;
    Cursor pointer;

   String[] listData;
    private static final String TAG = "ListDataActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        helper = new MyHelper(ViewData.this);
        db = helper.getWritableDatabase();
        String[]  ok = {"id","code","name","num"};
        pointer = db.query("Data",ok,null,null,null,null,null,null);
        Toast.makeText(this, "Rows = "+pointer.getCount(), Toast.LENGTH_SHORT).show();
        name = new String[pointer.getCount()];
        code = new String[pointer.getCount()];
        num = new String[pointer.getCount()];
        int i=0;
        while( pointer.moveToNext()){

            name[i] = pointer.getString(2);

            code[i] = pointer.getString(1);

            num[i] =  pointer.getString(3);

            i++;
        }




        ListView = (ListView) findViewById(R.id.ListView);
        myadapter adapter = new myadapter(this,name);
        ListView.setAdapter(adapter);


    }
    public  class myadapter extends ArrayAdapter<String> {

        public myadapter(@NonNull Context context,@NonNull String[] objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //WE CREATE THIS INFLATER TO INFLATE OUR NEW XML DESIGN TO THE ACTIVITY
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v =  inflater.inflate(R.layout.customlist,parent,false);

            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            tvName.setText("Product name: " + name[position]);

            TextView tvcode = (TextView) v.findViewById(R.id.tvcode);
            tvcode.setText("Bar code: " + code[position]);

            TextView tvnumer = (TextView) v.findViewById(R.id.tvnumer);
            tvnumer.setText("number: "+ num[position]);

            return v;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("taa","Destroyed");
    }
}
