package com.vishnu.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.vishnu.choresapp.model.*
import java.text.DateFormat
import java.util.*

class ChoresDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    //Invoked when the database is created
    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( " +
                "$KEY_ID INTEGER PRIMARY KEY,"+
                "$KEY_CHORE_NAME TEXT,"+
                "$KEY_CHORE_ASSIGNED_BY TEXT,"+
                "$KEY_CHORE_ASSIGNED_TO TEXT,"+
                "$KEY_CHORE_ASSIGNED_TIME LONG)"
        db?.execSQL(CREATE_TABLE)
    }

    //Invoked when the database gets upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        //Create Table Again
        onCreate(db)
    }

    //Functions for CRUD Operations

    fun createChore(chore: Chore) {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues().apply {
            put(KEY_CHORE_NAME, chore.choreName)
            put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
            put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
            put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())
        }

        //Invoking the SQL query
        db?.insert(TABLE_NAME, null, values)

        Log.d("DATA INSERTED", "SUCCESS")

        //Closing the database
        db.close()
    }

    fun retrieveChoreData(id: Int): Chore {
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID,
            KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY,
            KEY_CHORE_ASSIGNED_TIME,
            KEY_CHORE_ASSIGNED_TO), KEY_ID + "=?", arrayOf(id.toString()),
            null, null, null, null)


        if (cursor != null)
            cursor.moveToFirst()


        var chore = Chore()
        chore.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
        chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
        chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
        chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDateTime = dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)


        return chore

    }

    fun updateChore(chore: Chore): Int {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues().apply {
            put(KEY_CHORE_NAME, chore.choreName)
            put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
            put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
            put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())
        }

        //Update a Row
        return db.update(TABLE_NAME, values, "$KEY_ID=", arrayOf(chore.id.toString()))
    }

    fun deleteChore(chore: Chore) {
        var db: SQLiteDatabase = writableDatabase

        //Delete a Row
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(chore.id.toString()))

        //Close the database
        db.close()
    }

    //Function to the count of chores present in the database
    fun getChoresCount(): Int {
        var db: SQLiteDatabase = readableDatabase

        //Query
        var countQuery = "SELECT * FROM $TABLE_NAME"

        //Cursor
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count //Returns the number of entries in the table
    }
}