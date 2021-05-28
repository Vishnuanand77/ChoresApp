package com.vishnu.choresapp.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vishnu.choresapp.Model.*

class ChoresDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    //Invoked when the database is created
    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + "INTEGER PRIMARY KEY," +
                KEY_CHORE_NAME+ " TEXT," +
                KEY_CHORE_ASSIGNED_BY+ " TEXT," +
                KEY_CHORE_ASSIGNED_TO+ " TEXT," +
                KEY_CHORE_ASSIGNED_TIME+ " LONG"+
                ")"
        db?.execSQL(CREATE_TABLE)
    }

    //Invoked when the database gets upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}