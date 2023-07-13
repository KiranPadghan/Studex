package com.example.studex.modules.homeModule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studex.R
import com.example.studex.databinding.ItemStudentListBinding
import com.example.studex.modules.homeModule.models.StudentModel
import com.example.studex.utils.Utils
import com.google.gson.Gson

class StudentListAdapter(private val onClickEvent: OnClick) : ListAdapter<StudentModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<StudentModel>(){
        override fun areItemsTheSame(oldItem: StudentModel, newItem: StudentModel): Boolean {
           return oldItem.studentId == newItem.studentId
        }

        override fun areContentsTheSame(oldItem: StudentModel, newItem: StudentModel): Boolean {
           return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemStudentListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
           is ItemViewHolder -> {
                holder.bindItem(getItem(position))
            }
        }
    }

    inner class ItemViewHolder(private val binding: ItemStudentListBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bindItem(studentModel : StudentModel){
            binding.apply {
                tvName.text = studentModel.studentName + "  " + studentModel.studentLastName
                tvContact.text =  studentModel.studentMobile.ifEmpty { "--" }
                if (studentModel.studentImg == null){
                    ivProfile.setImageResource(R.mipmap.student)
                } else {
                    ivProfile.setImageBitmap(studentModel.studentImg)
                }
                tvDelete.setOnClickListener {
                    onClickEvent.onDeleteClick(studentModel)
                }
            }
        }
    }
}