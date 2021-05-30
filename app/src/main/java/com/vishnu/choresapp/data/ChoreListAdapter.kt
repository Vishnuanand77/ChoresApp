package com.vishnu.choresapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.choresapp.R
import com.vishnu.choresapp.model.Chore

class ChoreListAdapter(private val list: ArrayList<Chore>,
                        private val context: Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        //Here we create the view from the xml file
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, context: Context): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //TextView Initializations
        var choreName = itemView.findViewById(R.id.listChoreName) as TextView
        var choreAssignedBy = itemView.findViewById(R.id.listAssignedBy) as TextView
        var choreAssignedTo = itemView.findViewById(R.id.listAssignedTo) as TextView
        var choreDate = itemView.findViewById(R.id.listDate) as TextView

        var deleteButton = itemView.findViewById(R.id.listDeleteButton) as Button
        var editButton = itemView.findViewById(R.id.listEditButton) as Button

        var mContext = context

        fun bindItem(chore: Chore) {
            //Registering User Input
            choreName.text = chore.choreName
            choreAssignedBy.text = chore.assignedBy
            choreAssignedTo.text = chore.assignedTo
            choreDate.text = chore.showFormattedDate(System.currentTimeMillis())

            //Registering Buttons
            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                editButton.id -> {
                    Toast.makeText(mContext, "Edit Button", Toast.LENGTH_SHORT).show()

                }
                deleteButton.id -> {
                    Toast.makeText(mContext, "Delete Button", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}