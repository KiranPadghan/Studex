package com.example.studex.modules.homeModule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studex.R
import com.example.studex.databinding.FragmentStudentInfoPdfBinding


class StudentInfoPdfFragment : Fragment() {

    private lateinit var binding : FragmentStudentInfoPdfBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_info_pdf, container, false)
    }

}