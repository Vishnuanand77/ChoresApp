package com.vishnu.choresapp.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.vishnu.choresapp.data.ChoresDatabaseHandler
import com.vishnu.choresapp.model.Chore
import com.vishnu.choresapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Database Handler Initialization
    var dbHandler: ChoresDatabaseHandler? = null

    //ProgressDialog
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DB Handler setup
        dbHandler = ChoresDatabaseHandler(this)
        //Progress Dialog setup
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Saving...")

        saveButton.setOnClickListener {
            progressDialog!!.setMessage("Saving...")
            progressDialog!!.show()
            if (!TextUtils.isEmpty(enterChoreEditText.text.toString())
                && !TextUtils.isEmpty(assignedByEditText.text.toString())
                        && !TextUtils.isEmpty(assignedToEditText.text.toString())) {
                
                //Instantiating chore object from user input
                var chore = Chore()
                chore.choreName = enterChoreEditText.text.toString()
                chore.assignedBy = assignedByEditText.text.toString()
                chore.assignedTo = assignedToEditText.text.toString()
                
                //Invoking local save function
                saveToDB(chore)
                progressDialog!!.cancel()
                startActivity(Intent(this, ChoreListActivity::class.java))
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                progressDialog!!.cancel()
            }
        }

    }

    fun saveToDB(chore: Chore) {
        dbHandler!!.createChore(chore)
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