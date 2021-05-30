package com.vishnu.choresapp.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.choresapp.R
import com.vishnu.choresapp.activity.ChoreListActivity
import com.vishnu.choresapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private val list: ArrayList<Chore>,
                        private val context: Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        //Here we create the view from the xml file
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view, context, list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Chore>): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //TextView Initializations
        var choreName = itemView.findViewById(R.id.listChoreName) as TextView
        var choreAssignedBy = itemView.findViewById(R.id.listAssignedBy) as TextView
        var choreAssignedTo = itemView.findViewById(R.id.listAssignedTo) as TextView
        var choreDate = itemView.findViewById(R.id.listDate) as TextView

        var deleteButton = itemView.findViewById(R.id.listDeleteButton) as Button
        var editButton = itemView.findViewById(R.id.listEditButton) as Button

        var mContext = context
        var mList = list



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
            var mPosition: Int = adapterPosition
            var chore = mList[mPosition]
            when (v!!.id) {
                editButton.id -> {
                    editChore(chore)

                    //Toast.makeText(mContext, "Edit Button", Toast.LENGTH_SHORT).show()

                }
                deleteButton.id -> {
                    deleteChore(chore.id!!.toInt())
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    //Toast.makeText(mContext, "Delete Button", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun deleteChore(id: Int) {
            var db: ChoresDatabaseHandler = ChoresDatabaseHandler(mContext)
            db.deleteChore(id)
        }

        fun editChore(chore: Chore) {
            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler: ChoresDatabaseHandler = ChoresDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)

            //XML Initializations
            var choreName = view.popEnterChore
            var assignedBy = view.popAssignedBy
            var assignedTo = view.popAssignedTo
            var saveBtn = view.popSaveButton

            //Dialog Builder
            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveBtn.setOnClickListener {
                var name= choreName.text.toString().trim()
                var assignedByText = assignedBy.text.toString().trim()
                var assignedToText = assignedTo.text.toString().trim()

                if (!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(assignedByText)
                    && !TextUtils.isEmpty(assignedToText)) {

                    //var chore = Chore()
                    chore.choreName = name
                    chore.assignedBy = assignedByText
                    chore.assignedTo = assignedToText

                    dbHandler!!.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)

                    dialog!!.dismiss()

                } else {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}