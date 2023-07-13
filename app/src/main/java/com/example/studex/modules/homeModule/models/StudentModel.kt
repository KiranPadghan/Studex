package com.example.studex.modules.homeModule.models

import android.graphics.Bitmap

data class StudentModel(
    var studentId : Long,
    var studentName : String,
    var studentLastName : String,
    var studentMobile : String,
    var studentCourse : String,
    var studentImg : Bitmap?
)