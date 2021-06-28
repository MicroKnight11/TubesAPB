package com.example.tubesabp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import com.example.tubesabp.data.model.LoggedInUser;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context){
        super(context,"tubes.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id_user integer PRIMARY KEY AUTOINCREMENT, username text, email text, password text)");
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, id_user integer)");
        db.execSQL("insert into session values(1,0)");
        db.execSQL("insert into user values(1, 'coba', 'test@gmail.com', 'test123')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS session");
        onCreate(db);
    }

    public Boolean checkSession(String sessionValues){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE id_user = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean upgradeSession(String id_user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user",id_user);
        long update = db.update("session", contentValues,"id = 1",null);
        if (update == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean downgradeSession(){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user","0");
        long update = db.update("session", contentValues,"id = 1",null);
        if (update == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean insertUser(String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean deleteUser(String id_user){
        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete("user", "id_user = ?", new String[]{id_user});
        if (deleted == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean updateUser(String username, String email, String id_user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("email",email);
        long update = db.update("user", contentValues,"id_user = ?",new String[]{id_user});
        if (update == -1){
            return false;
        }
        else {
            return true;
        }
    }

//    public int getUserId(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT id_user FROM session", null);
//        if (cursor.getCount() > 0){
//            return cursor.getInt(0);
//        }
//        else {
//            return 0;
//        }
//    }

    public LoggedInUser getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user where email = ?", new String[]{email});
        cursor.moveToFirst();
        LoggedInUser user = new LoggedInUser(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        return user;
    }

    public LoggedInUser getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user INNER JOIN session on session.id_user = user.id_user", null);
        cursor.moveToFirst();
        LoggedInUser user = new LoggedInUser(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        return user;
    }

    public Boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
