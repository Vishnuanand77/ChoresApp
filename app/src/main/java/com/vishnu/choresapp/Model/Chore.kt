package com.vishnu.choresapp.Model

class Chore() {

    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var timeAssigned: Long? = null
    var id: Int? = null //Each chore should have it's own unique ID.

    constructor(choreName: String, assignedBy: String, assignedTo: String, timeAssigned: Long, id: Int): this() {
        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.timeAssigned = timeAssigned
        this.id = id
    }
}