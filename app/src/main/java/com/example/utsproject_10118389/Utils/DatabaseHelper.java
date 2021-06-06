package com.example.utsproject_10118389.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.utsproject_10118389.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

//Tanggal Pengerjaan : 4 Juni 2021
//NIM   : 10118389
//Nama  : Muhammad Salman Al-Farisi
//Kelas : IF-09


public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String Database_name = "dailytaskDB";
    private static final String Table_name = "task_Table";
    private static final String Column_1 = "ID";
    private static final String Column_2 = "TASK";
    private static final String Column_3 = "STATUS";



    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);

    }

    public void insertTask(TaskModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column_2, model.getTask());
        values.put(Column_3, 0);
        db.insert(Table_name , null, values);
    }

    public void updateTask (int id, String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column_2 , task);
        db.update(Table_name , values , "ID=?" , new String[]{String.valueOf(id)});

    }

    public void updateStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column_3 , status);
        db.update(Table_name , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db = this.getWritableDatabase();
        db.delete(Table_name , "ID=?" , new String[]{String.valueOf(id)});
    }

    public List<TaskModel> getAllTasks(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<TaskModel> modelList = new ArrayList<>();
        db.beginTransaction();
        try{
            cursor = db.query(Table_name , null ,null , null , null , null , null );
            if (cursor!=null){
                if (cursor.moveToFirst()){
                    do {
                        TaskModel task = new TaskModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(Column_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(Column_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(Column_3)));
                        modelList.add(task);
                    }
                    while (cursor.moveToNext());
                }
            }

        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
