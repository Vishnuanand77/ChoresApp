package com.vishnu.choresapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.choresapp.R
import com.vishnu.choresapp.data.ChoreListAdapter
import com.vishnu.choresapp.data.ChoresDatabaseHandler
import com.vishnu.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*

class ChoreListActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null //Database Handler Initialization
    private var adapter: ChoreListAdapter? = null //RecyclerView Adapter Initialization
    private var choreList: ArrayList<Chore>? = null //Array of chore objects Initialization
    private var choreListItems: ArrayList<Chore>? = null //Dummy list
    private var layoutManager: RecyclerView.LayoutManager? = null //RecyclerView Layout Manager Initialization

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

    fun testRead() {
        for (c in choreList!!.iterator()) {
            Log.d("Chore List ", c.choreName.toString())
        }
    }
}