package cyderx.com.map.component;

import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import cyderx.com.map.MapApplication;
import cyderx.com.map.data.User;
import cyderx.com.map.data.UserMapper;

/**
 * 登陆时的校验工具类
 */
public class LoginValidator {
    public static boolean checkUser(Editable username, Editable password, SQLiteDatabase db, MapApplication app) {
        User user = UserMapper.queryUser(db, username.toString());
        if (user != null && user.getPassword().equals(password.toString())) {
            app.setUser(user);
            return true;
        }
        return false;
    }
}
