package kedr.emam.ahmad.scan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void can(View view) {
        Intent a = new Intent(this,Camera.class);
        startActivity(a);
    }

    public void show(View view) {
        //use to show the user the items in the data base
        Intent e = new Intent(this,ViewData.class);
        startActivity(e);
    }

    public void Email(View view) {
        Intent e = new Intent(this,Email.class);
        startActivity(e);
    }



    public void invent(View view) {
        Intent xx = new Intent(this,Inventory.class);
        startActivity(xx);
    }





    public void Exit(View view) {
        ShowExitAlert();
    }

    @Override
    public void onBackPressed() {
        ShowExitAlert();
    }

    public void ShowExitAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to Exit ? ");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });
        alertDialogBuilder.setTitle("Warrning");
        alertDialogBuilder.setIcon(android.R.drawable.stat_sys_warning);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
