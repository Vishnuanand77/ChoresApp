package com.vishnu.choresapp.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
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
        db?.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)

        //Create Table Again
        onCreate(db)
    }

    //Functions for CRUD Operations

    fun createChore(chore: Chore) {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        //Invoking the SQL query
        db.insert(TABLE_NAME, null, values)

        Log.d("DATA INSERTED", "SUCCESS")

        //Closing the database
        db.close()
    }

}