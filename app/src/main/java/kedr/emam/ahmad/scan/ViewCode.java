package kedr.emam.ahmad.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewCode extends AppCompatActivity {
 private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_code);
        text =findViewById(R.id.text);
        Intent d = getIntent();
        String ok = d.getStringExtra("number");
        text.setText(ok);
    }
}
