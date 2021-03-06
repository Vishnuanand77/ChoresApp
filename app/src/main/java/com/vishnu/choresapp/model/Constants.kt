package com.vishnu.choresapp.model

//We add all database constants in this class
val DATABASE_VERSION: Int = 1
val DATABASE_NAME: String = "chore.db"
val TABLE_NAME: String = "chores"

//Chores table -> Column names
val KEY_ID: String = "id" //Primary Key

//Remaining Columns in the table
val KEY_CHORE_NAME: String = "chore_name"
val KEY_CHORE_ASSIGNED_BY: String = "chore_assigned_by"
val KEY_CHORE_ASSIGNED_TO: String = "chore_assigned_to"
val KEY_CHORE_ASSIGNED_TIME: String = "chore_assigned_time"