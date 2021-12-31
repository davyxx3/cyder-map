package cyderx.com.map.component;

import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import java.util.regex.Pattern;

import cyderx.com.map.data.UserMapper;

/**
 * 注册时的校验工具类
 */
public class SignupValidator {
    public static boolean checkUsername(Editable text, SQLiteDatabase db) {
        return Pattern.matches("^[a-zA-Z][a-zA-Z0-9_]{0,19}$", text) && !UserMapper.isUserExists(db, text.toString());
    }

    public static boolean checkPassword(Editable text) {
        return Pattern.matches("^[a-zA-Z]\\w{5,17}$", text);
    }

    public static boolean checkPhone(Editable text) {
        return Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", text);
    }

    public static boolean checkEmail(Editable text) {
        return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", text);
    }

    public static boolean checkConfirmPassword(Editable text, Editable password) {
        return text.toString().equals(password.toString());
    }

    public static boolean checkComment(Editable text) {
        return text.length() <= 150;
    }
}
