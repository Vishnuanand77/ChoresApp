package com.vishnu.choresapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.choresapp.R
import com.vishnu.choresapp.data.ChoreListAdapter
import com.vishnu.choresapp.data.ChoresDatabaseHandler
import com.vishnu.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.popup.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null //Database Handler Initialization
    private var adapter: ChoreListAdapter? = null //RecyclerView Adapter Initialization
    private var choreList: ArrayList<Chore>? = null //Array of chore objects Initialization
    private var choreListItems: ArrayList<Chore>? = null //Dummy list
    private var layoutManager: RecyclerView.LayoutManager? = null //RecyclerView Layout Manager Initialization
    private var dialogBuilder: AlertDialog.Builder? = null //Alert Dialog Builder Initialization
    private var dialog: AlertDialog? = null //Alert Dialog  Initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        //DB Handler setup
        dbHandler = ChoresDatabaseHandler(this)

        //RecyclerView Initializations
        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        //adapter = ChoreListAdapter(choreList!!, this)
        adapter = ChoreListAdapter(choreListItems!!, this)

        //RecyclerView setup
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        //Populating RecyclerView
        choreList = dbHandler!!.readChores()
        choreList!!.reverse()
        adapter!!.notifyDataSetChanged()
        //testRead()
        for (c in choreList!!.iterator()) {

            val chore = Chore()
            chore.choreName = c.choreName
            chore.assignedBy = c.assignedBy
            chore.assignedTo = c.assignedTo
            chore.showFormattedDate(c.timeAssigned!!)

            //Log.d("Chore List ", c.choreName.toString())

            //Adding into chore list
            choreListItems!!.add(chore)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item!!.itemId == R.id.add_menu_button) {
            Log.d("Item Clicked", "Menu Item Clicked")

            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {
        var view = layoutInflater.inflate(R.layout.popup, null)

        //XML Initializations
        var choreName = view.popEnterChore
        var assignedBy = view.popAssignedBy
        var assignedTo = view.popAssignedTo
        var saveBtn = view.popSaveButton

        //Dialog Builder
        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveBtn.setOnClickListener {
            var name= choreName.text.toString().trim()
            var assignedByText = assignedBy.text.toString().trim()
            var assignedToText = assignedTo.text.toString().trim()
            if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(assignedByText)
                    && !TextUtils.isEmpty(assignedToText)) {

                var chore = Chore()
                chore.choreName = name
                chore.assignedBy = assignedByText
                chore.assignedTo = assignedToText

                dbHandler!!.createChore(chore)

                dialog!!.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun testRead() {
        for (c in choreList!!.iterator()) {
            Log.d("Chore List ", c.choreName.toString())
        }
    }
}