package cyderx.com.map.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 用于完成对User的增加和删除操作
 */
public class UserMapper {
    /**
     * 根据用户名查询用户
     *
     * @param db       数据库对象
     * @param username 用户名
     * @return 用户对象
     */
    public static User queryUser(SQLiteDatabase db, String username) {
        Cursor cursor = db.rawQuery("select * from User where username = ?", new String[]{username});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            User user = new User();
            user.setFullName(cursor.getString(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setGender(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            user.setProvince(cursor.getString(6));
            user.setCity(cursor.getString(7));
            user.setBirthday(cursor.getString(8));
            user.setInterests(cursor.getString(9));
            user.setComment(cursor.getString(10));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    /**
     * 根据用户名检查用户是否已存在
     *
     * @param db       数据库对象
     * @param username 用户名
     * @return 用户是否存在
     */
    public static boolean isUserExists(SQLiteDatabase db, String username) {
        Cursor cursor = db.rawQuery("select * from User where username = ?", new String[]{username});
        boolean result = cursor.getCount() != 0;
        cursor.close();
        return result;
    }

    /**
     * 向数据库添加一个用户
     *
     * @param db   数据库对象
     * @param user 用户对象
     */
    public static void addUser(SQLiteDatabase db, User user) {
        ContentValues cv = new ContentValues();
        cv.put("full_name", user.getFullName());
        cv.put("username", user.getUsername());
        cv.put("password", user.getPassword());
        cv.put("gender", user.getGender());
        cv.put("phone", user.getPhone());
        cv.put("email", user.getEmail());
        cv.put("province", user.getProvince());
        cv.put("city", user.getCity());
        cv.put("birthday", user.getBirthday());
        cv.put("interests", user.getInterests());
        cv.put("comment", user.getComment());
        db.insert("User", "username", cv);
    }

}
