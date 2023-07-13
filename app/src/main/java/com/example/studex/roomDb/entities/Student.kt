package com.example.studex.roomDb.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id") var studentId : Long,
    @ColumnInfo(name = "student_name") var studentName : String,
    @ColumnInfo(name = "student_last_name") var studentLastName : String,
    @ColumnInfo(name = "student_phone") var studentPhone : String,
    @ColumnInfo(name = "student_course") var studentCourse : String,
    @ColumnInfo(name = "student_img", typeAffinity = ColumnInfo.BLOB) var studentImg : Bitmap?
) {

}
