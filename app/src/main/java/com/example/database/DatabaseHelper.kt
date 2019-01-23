package com.example.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DB_NAME = "College"
val TABLE_NAME = "Students"
val COL_ONE = "id"
val COL_TWO = "Name"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createStmt = """CREATE TABLE ${TABLE_NAME}(
        |$COL_ONE INTEGER PRIMARY KEY,
        |$COL_TWO VARCHAR(20)
        |)
    """.trimMargin()

        db?.execSQL(createStmt)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(student: Students) {
        val db = this.writableDatabase
        var cv = ContentValues()

        cv.put(COL_ONE, student.sid)
        cv.put(COL_TWO, student.sname)

        if (db.insert(TABLE_NAME, null, cv) == -1.toLong())
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
    }

    fun readData(): MutableList<Students> {
        val db = this.readableDatabase
        val list: MutableList<Students> = ArrayList()
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                list.add(Students(result.getString(0).toInt(), result.getString(1)))
            } while (result.moveToNext())
        }

        result.close()
        return list
    }

}