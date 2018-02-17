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
import java.util.Collections;

public class ViewData extends AppCompatActivity {
    private ListView ListView;
    private  ArrayList<String> name;
    private ArrayList<String> code;
    private ArrayList<String> quantity;
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
        String[]  ok = {"id","code","name","quantity"};
        pointer = db.query("Data",ok,null,null,null,null,null,null);
        Toast.makeText(this, "Rows = "+pointer.getCount(), Toast.LENGTH_SHORT).show();
        name = new ArrayList<String>();
        code = new ArrayList<String>();
        quantity = new ArrayList<String>();

        while( pointer.moveToNext()){

            name.add(pointer.getString(2));

            code.add(pointer.getString(1));

            quantity.add( pointer.getString(3));


        }
        Collections.reverse(name);
        Collections.reverse(code);
        Collections.reverse(quantity);
        ArrayList<ArrayModel> adapt = new ArrayList<ArrayModel>();
        myadapter adapter = new myadapter(this,adapt);

for (int i =0; i < name.size(); i++){

    ArrayModel model = new ArrayModel(name.get(i),code.get(i),quantity.get(i));
    adapter.add(model);

}
        ListView = (ListView) findViewById(R.id.ListView);

        ListView.setAdapter(adapter);


    }
    public  class myadapter extends ArrayAdapter<ArrayModel> {

        public myadapter(@NonNull Context context,@NonNull ArrayList<ArrayModel> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //WE CREATE THIS INFLATER TO INFLATE OUR NEW XML DESIGN TO THE ACTIVITY
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v =  inflater.inflate(R.layout.customlist,parent,false);
ArrayModel model = getItem(position);
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            tvName.setText("Product name: " + model.name);

            TextView tvcode = (TextView) v.findViewById(R.id.tvcode);
            tvcode.setText("Bar code: " + model.code);

            TextView tvnumer = (TextView) v.findViewById(R.id.tvnumer);
            tvnumer.setText("Quantity: "+ model.num);

            return v;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("taa","Destroyed");
    }
}
