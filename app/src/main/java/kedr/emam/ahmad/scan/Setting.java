package kedr.emam.ahmad.scan;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        /////font Style
        TextView SettingHeader = findViewById(R.id.SettingHeader);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/mandali.ttf");
        SettingHeader.setTypeface(font);
        /////////////\\\\\\\\

    }
}
