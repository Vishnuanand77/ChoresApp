package com.vishnu.choresapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vishnu.choresapp.Data.ChoresDatabaseHandler
import com.vishnu.choresapp.Model.Chore
import com.vishnu.choresapp.R

class MainActivity : AppCompatActivity() {
    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        var chore = Chore()
        chore.choreName = "Clean Room 2"
        chore.assignedTo = "Varun2"
        chore.assignedBy = "Vishnu2"

        dbHandler!!.createChore(chore)

        //Read from database
        var chores: Chore = dbHandler!!.retrieveChoreData(2)

        Log.d("Item:", chores.choreName.toString())
    }
}