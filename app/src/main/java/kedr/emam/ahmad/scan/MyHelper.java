package kedr.emam.ahmad.scan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ahmad Khedr on 2/11/2018.
 */

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Data(id integer primary key autoincrement,quantity varchar(50),code varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Data");
        onCreate(db);
    }

}
