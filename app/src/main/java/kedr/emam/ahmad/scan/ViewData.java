package kedr.emam.ahmad.scan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewData extends AppCompatActivity   {
    private ListView ListView;
private int groupid;
 private myadapter adapter;
    ArrayModel model;
    private  ArrayList<String> name;
    private  ArrayList<String> id;
    private ArrayList<String> code;
    private ArrayList<String> quantity;
private TextView etView;
private MyHelper helper;
private SQLiteDatabase db;
    Cursor pointer;
    int positionmark ;

   String[] listData;
    private static final String TAG = "ListDataActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        ListView = (ListView) findViewById(R.id.ListView);
        registerForContextMenu(ListView);
        helper = new MyHelper(ViewData.this);
        db = helper.getWritableDatabase();
        String[]  ok = {"id","code","name","quantity"};
        pointer = db.query("Data",ok,null,null,null,null,null,null);
       // Toast.makeText(this, "Rows = "+pointer.getCount(), Toast.LENGTH_SHORT).show();
        name = new ArrayList<String>();
        id = new ArrayList<String>();
        code = new ArrayList<String>();
        quantity = new ArrayList<String>();

        while( pointer.moveToNext()){

            name.add(pointer.getString(2));

            id.add(pointer.getString(0));

            code.add(pointer.getString(1));

            quantity.add( pointer.getString(3));


        }
        positionmark = name.size()-1; //last Position in listView  To Select and make Light

        ArrayList<ArrayModel> adapt = new ArrayList<ArrayModel>();

         adapter = new myadapter(this,adapt);

for (int i =0; i < name.size(); i++){

     model = new ArrayModel(name.get(i),code.get(i),quantity.get(i));
    adapter.add(model);


}


        ListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        registerForContextMenu(ListView);
Bundle ww = getIntent().getExtras();

if(ww != null){ //selection of list View when edit Your inventory
    positionmark = ww.getInt("position");
    Toast.makeText(this, ""+positionmark, Toast.LENGTH_SHORT).show();
    ListView.setSelection(positionmark);

}



    }
    public  class myadapter extends ArrayAdapter<ArrayModel> {

        public myadapter(@NonNull Context context, @NonNull ArrayList<ArrayModel> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //WE CREATE THIS INFLATER TO INFLATE OUR NEW XML DESIGN TO THE ACTIVITY
            // Get the current item from ListVie

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.customlist, parent, false);
            ArrayModel model = getItem(position);
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            tvName.setText( model.name);

            TextView tvcode = (TextView) v.findViewById(R.id.tvcode);
            tvcode.setText( model.code);

            TextView tvnumer = (TextView) v.findViewById(R.id.tvnumer);
            tvnumer.setText( model.num);


            if( positionmark == position){
                RelativeLayout customlist = v.findViewById(R.id.customlistviewlayout);
                customlist.setBackgroundResource(R.drawable.button_background2);
            }


            return v;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       // Log.d("taa","Destroyed");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Edit");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;


        if(item.getTitle()=="Edit"){
            //Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_LONG).show();

            Intent A = new Intent(ViewData.this,Inventory.class);
            A.putExtra("id",id.get(index));//Id Of ItemSelected in Database
            A.putExtra("name",name.get(index));//name Of ItemSelected in Database
            A.putExtra("code",code.get(index));//code Of ItemSelected in Database
            A.putExtra("quantity",quantity.get(index));//quantity Of ItemSelected in Database
            A.putExtra("postion",index); //ListView Position Mark عشان وانت راجع تلاقي الليست فيو منورة علي اخر تعديل
            startActivity(A);
            this.finish();
        }

        else if(item.getTitle()=="Delete"){
             MyHelper helper = new MyHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
           int a =  db.delete("Data","id = ?",new String[]{id.get(index)});

            if(a > 0){
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(this,ViewData.class);
                startActivity(refresh);
                this.finish();

            }
            else {

                Toast.makeText(this, "Not Deleted ", Toast.LENGTH_SHORT).show();
            }
        }

        else{

            return false;
        }
        return true;
    }

}
