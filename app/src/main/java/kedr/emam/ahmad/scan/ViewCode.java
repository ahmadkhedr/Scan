package kedr.emam.ahmad.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewCode extends AppCompatActivity {
 private TextView text,itemcode,proname,nuum;
 private EditText name,num;
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
        Intent d = getIntent();
        String ok = d.getStringExtra("number");
        text.setText(ok);
    }

    public void save(View view) {
        //code to save the data in the Sqlite data base then see it beside other products in a table
    }

    public void Cancel(View view) {
        Intent c = new Intent(this,Camera.class);
        startActivity(c);
    }
}
