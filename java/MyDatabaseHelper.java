package com.example.croudsourcing;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, "CrowdSourcing", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "create table user_info(name varchar(30), email varchar(30), password varchar(15), PRIMARY KEY(email));";
        String query2 = "create table reports(s_no INTEGER PRIMARY KEY AUTOINCREMENT, email varchar(30), title varchar(30), report varchar(500), solution varchar(500),img blob,  FOREIGN KEY (email) REFERENCES user_info(email));";
        String query3 = "create table articles(s_no INTEGER PRIMARY KEY AUTOINCREMENT, email varchar(30), title varchar(30), intro varchar(500), body varchar(10000), img blob, isApproved INTEGER)";
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    public Long insertData(String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password",password);
        return db.insert("user_info", null, cv);
    }

    public boolean validateUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select password from user_info where email = '"+email+"';";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount()==0) return false;
        c.moveToNext();
        String pass = c.getString(0);
        return password.equals(pass);
    }

    public Long newReport(String email, String title, String report, byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("title", title);
        cv.put("report",report);
        cv.put("solution", "");
        cv.put("img", img);
        return db.insert("reports", null, cv);
    }

    public Long writeArticle(String email, String title, String intro, String body, byte[] img, boolean isApproved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("title", title);
        cv.put("intro",intro);
        cv.put("body", body);
        cv.put("img", img);
        int approved;
        if(isApproved){
            approved = 1;
        }
        else approved = 0;
        cv.put("isApproved", approved);
        return db.insert("articles", null, cv);
    }
    @SuppressLint("Range")
    public String getName(String email){
        if(email.equals("expert")){
            return "expert";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select name from user_info where email='"+email+"';";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex("name"));
        }
        else return "";
    }

    public ArrayList<Report> getReports() {
        ArrayList<Report> list = new ArrayList<Report>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from reports";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            String name = getName(email);
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String report = cursor.getString(cursor.getColumnIndex("report"));
            @SuppressLint("Range") String solution = cursor.getString(cursor.getColumnIndex("solution"));
            @SuppressLint("Range") int sno = Integer.parseInt(cursor.getString(cursor.getColumnIndex("s_no")));
            @SuppressLint("Range") byte[] img = cursor.getBlob(cursor.getColumnIndex("img"));
            list.add(new Report(sno, name, email, title, report, solution, img));
        }
        return list;
    }

    public void updateReport(int sno, String newSolution) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update reports set solution='"+newSolution+"' where s_no='"+sno+"';";
        db.execSQL(query);
    }

    @SuppressLint("Range")
    public ArrayList<Article> getArticles() {
        ArrayList<Article> list = new ArrayList<Article>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from articles";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            String name = getName(email);
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String intro = cursor.getString(cursor.getColumnIndex("intro"));
            @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
            @SuppressLint("Range") int sno = Integer.parseInt(cursor.getString(cursor.getColumnIndex("s_no")));
            @SuppressLint("Range") byte[] img = cursor.getBlob(cursor.getColumnIndex("img"));
            boolean isApproved = cursor.getInt(cursor.getColumnIndex("isApproved")) != 0;
            list.add(new Article(sno, name, email, title, intro, body, img, isApproved));
        }
        return list;
    }

    public void setApprove(int sno, int temp) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update articles set isApproved="+temp+" where s_no="+sno+";";
        db.execSQL(query);
    }
}
