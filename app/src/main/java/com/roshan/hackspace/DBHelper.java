package com.roshan.hackspace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="Roshan.db";

    public DBHelper(Context context ){
        super(context,"Roshan1.db",null,1);
    } {
    }
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create TABLE userinfo(username TEXT primary key, password TEXT,name TEXT,emailid TEXT,yearofpassing TEXT,semester TEXT)");
        MyDB.execSQL("create TABLE bookinfo(bookname TEXT , author TEXT, publication TEXT,donorname TEXT,donoradress TEXT,donormob NUMBER)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB,int i,int i1) {
        MyDB.execSQL("drop TABLE if exists userinfo");
        MyDB.execSQL("drop TABLE if exists bookinfo");

    }
    public Boolean insertData(String username,String password,String name,String emailid,String yearofpassing,String semester){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("name",name);
        contentValues.put("emailid",emailid);
        contentValues.put("yearofpassing",yearofpassing);
        contentValues.put("semester",semester);

        long result = MyDB.insert("userinfo",null,contentValues);
        if (result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        Cursor cursor= MyDB.rawQuery("select * from userinfo where username=?",new String[]{username});
        if(cursor.getCount()>0)return true;

        else
            return false;
    }

    public Boolean checkusernamepassword(String username,String password){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        Cursor cursor= MyDB.rawQuery("select * from userinfo where username=? and password=?",new String[]{username,password});
        if(cursor.getCount()>0)return true;

        else
            return false;
    }
    public boolean getuser(String username ){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        Cursor cursor= MyDB.rawQuery("select name from userinfo where username=?",new String[]{username});
        if(cursor.getCount()>0)return true;
        else
            return false;
    }
    /*public String getemail(String email ){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        Cursor cursor= MyDB.rawQuery("select emailid from userinfo where username=?",new String[]{email});
        return email;
    }*/


    public Boolean update(String username,String password,String name,String emailid,String yearofpassing,String semester){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        long result=0;
        ContentValues contentValues= new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("name",name);
        contentValues.put("emailid",emailid);
        contentValues.put("yearofpassing",yearofpassing);
        contentValues.put("semester",semester);
        Cursor cursor= MyDB.rawQuery("select name from userinfo where username=?",new String[]{username});
        if(cursor.getCount()>0) {
            result = MyDB.update("userinfo", contentValues, "username =?", new String[]{username});
            if (result == -1) return false;
            else
                return true;
        }return false;
    }

    public Boolean insertbook(String bookname,String author,String publication,String donorname,String donoradress,String donormob){
        SQLiteDatabase MyDB= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("bookname",bookname);
        contentValues.put("author",author);
        contentValues.put("publication",publication);
        contentValues.put("donorname",donorname);
        contentValues.put("donoradress",donoradress);
        contentValues.put("donormob",donormob);
        long result = MyDB.insert("bookinfo",null,contentValues);
        if (result==-1) return false;
        else
            return true;
    }

    public  Cursor searchbook(String bookname) {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor book = MyDB.rawQuery("SELECT * FROM bookinfo where bookname=?", new String[]{bookname});
        return book ;
    }




public boolean deletebook(String bookname,String author,String publication,String donorname,String donoradress,String donormob){
    SQLiteDatabase MyDB = this.getWritableDatabase();
    Cursor abc=MyDB.rawQuery("SELECT * FROM bookinfo where bookname=? and author=? and publication=? and donorname=? and donoradress=? and donormob=? ",new String[]{bookname,author,publication,donorname,donoradress,donormob});
    if(abc.getCount()>0){
        Cursor result=MyDB.rawQuery("DELETE FROM bookinfo where bookname=? and author=? and publication=? and donorname=? and donoradress=? and donormob=? ",new String[]{bookname,author,publication,donorname,donoradress,donormob});
        if(result.getCount()>0){
            return false;
        }else
            return true;
    }

    else
        return false;


    }}




