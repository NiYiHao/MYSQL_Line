package com.example.administrator.mysql_line;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBadapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BIRTH = "birth";
    private DBHelper mDbHelper;
    private SQLiteDatabase mdb;
    private final Context mCtx;
    private static final String TABLE_NAME = "member";
    private Intent i;
    private ContentValues values;
    public DBadapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }
    public void open(){
        mDbHelper = new DBHelper(mCtx);
        mdb = mDbHelper.getWritableDatabase();
        Log.i("DB=",mdb.toString());
    }
    public void close(){
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }
    public Cursor listContacts(){
        Cursor mCursor = mdb.query(TABLE_NAME, new String[] {KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_BIRTH},null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public long createContacts(String name,
                               String phone, String email, String birth) {
        try{
            values = new ContentValues();
            values.put(KEY_NAME, name);
            values.put(KEY_PHONE, phone);
            values.put(KEY_EMAIL, email);
            values.put(KEY_BIRTH, birth);

        }catch(Exception e){

        }finally {
            Toast.makeText(mCtx,"新增成功!", Toast.LENGTH_SHORT).show();

        }

        return mdb.insert(TABLE_NAME,null,values);


    }
    public long updateContacts(int id, String name, String phone, String email, String birth){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_BIRTH, birth);
        Toast.makeText(mCtx,"更新成功!", Toast.LENGTH_SHORT).show();
        return mdb.update(TABLE_NAME, values, "_id=" + id,null);
    }
    public boolean deleteContacts(int id){
        String[] args = {Integer.toString(id)};
        mdb.delete(TABLE_NAME, "_id= ?",args);
        return true;
    }
    public Cursor queryByName(String item_name){
        Cursor mCursor = null;
        mCursor = mdb.query(true, TABLE_NAME, new String[] {KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_BIRTH},
                KEY_NAME + " LIKE '%" + item_name + "%'", null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }
}
