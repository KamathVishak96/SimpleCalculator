package com.example.database

class Students{
    var sid = 0
    var sname = ""

    constructor(sid: Int, sname: String){
        this.sid = sid
        this.sname = sname
    }

    override fun toString(): String {
        return "$sid    $sname"
    }
}