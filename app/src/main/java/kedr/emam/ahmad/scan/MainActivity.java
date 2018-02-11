package kedr.emam.ahmad.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
    }
}
