package cyderx.com.map.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 数据库连接工具类
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table User(\n" +
                "    full_name varchar(30),\n" +
                "    username varchar(30) primary key ,\n" +
                "    password varchar(30),\n" +
                "    gender varchar(30),\n" +
                "    phone varchar(30),\n" +
                "    email varchar(30),\n" +
                "    province varchar(30),\n" +
                "    city varchar(30),\n" +
                "    birthday varchar(30),\n" +
                "    interests varchar(30),\n" +
                "    comment varchar(150)\n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
