package com.vishnu.choresapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.vishnu.choresapp.Data.ChoresDatabaseHandler
import com.vishnu.choresapp.Model.Chore
import com.vishnu.choresapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Database Handler Initialization
    var dbHandler: ChoresDatabaseHandler? = null

    //XML Initializations
    var enterChore = enterChoreEditText
    var assignedBy = assignedByEditText
    var assignedTo = assignedToEditText
    var saveBtn = saveButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        saveBtn.setOnClickListener {
            if (!TextUtils.isEmpty(enterChore.text.toString())
                && !TextUtils.isEmpty(assignedBy.text.toString())
                        && !TextUtils.isEmpty(assignedTo.text.toString())) {
                TODO("SAVE TO DATABASE")
            }
        }

    }

    fun TestDB() {
        var chore = Chore()
        chore.choreName = "Clean Room 3"
        chore.assignedTo = "Varun"
        chore.assignedBy = "Vishnu"

        dbHandler!!.createChore(chore)

        Log.d("Database Entry ", chore.choreName.toString() )

        //Read from database
        var chores: Chore = dbHandler!!.retrieveChoreData(3)

        Log.d("Item:", chores.id.toString() + " " + chores.choreName.toString())
    }
}