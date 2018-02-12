package kedr.emam.ahmad.scan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {
    private ListView ListView;
    private  String[] emam ={" "};
private TextView etView;
private MyHelper helper;
private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        ListView = (ListView) findViewById(R.id.ListView);
        myadapter adapter = new myadapter(this,android.R.layout.simple_list_item_1,emam);
        ListView.setAdapter(adapter);


    }
    public  class myadapter extends ArrayAdapter<String> {

        public myadapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //WE CREATE THIS INFLATER TO INFLATE OUR NEW XML DESIGN TO THE ACTIVITY
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v =  inflater.inflate(R.layout.customlist,parent,false);
            helper = new MyHelper(ViewData.this);
            db = helper.getWritableDatabase();
            String[]  ok = {"id","code","name","num"};
            Cursor pointer = db.query("Data",ok,null,null,null,null,null,null);

            String code = " ";
            String name = " ";
            String num = " ";
            while( pointer.moveToNext()){
                name = pointer.getString(2);
                TextView tvName = (TextView) v.findViewById(R.id.tvName);
                tvName.setText("Product name: " + name);
               code = pointer.getString(1);
                TextView tvcode = (TextView) v.findViewById(R.id.tvcode);
                tvcode.setText("Bar code: " + code);
                num = pointer.getString(3);
                TextView tvnumer = (TextView) v.findViewById(R.id.tvnumer);
                tvnumer.setText("quantity: "+ num);

            }

            return v;
        }
    }
}
