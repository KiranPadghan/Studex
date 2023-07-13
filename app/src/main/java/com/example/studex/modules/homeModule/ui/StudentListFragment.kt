package com.example.studex.modules.homeModule.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studex.databinding.FragmentStudentListBinding
import com.example.studex.modules.homeModule.OnClick
import com.example.studex.modules.homeModule.StudentListAdapter
import com.example.studex.modules.homeModule.models.StudentModel
import com.example.studex.modules.loginModule.ui.MainActivity
import com.example.studex.modules.loginModule.ui.StudentActivity
import com.example.studex.modules.viewmodel.HomeViewModel
import com.example.studex.roomDb.entities.Student
import com.example.studex.utils.Constants
import com.example.studex.utils.SharedPreferences
import com.example.studex.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentListFragment : Fragment(), View.OnClickListener, OnClick {

    private lateinit var binding: FragmentStudentListBinding
    private lateinit var studentListAdapter: StudentListAdapter

    lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
        setObserver()
    }

    private fun setListeners() {
        binding.tvAddNewStudent.setOnClickListener(this)
        binding.tvLogOut.setOnClickListener(this)
    }

    private fun setObserver() {
        viewModel.studentList.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                studentListAdapter.submitList(getStudentModelFromStudent(it))
            } else {
                Utils.showToast(requireContext(),"No Student Added")
            }
        }
        viewModel.studentDeleteResponse.observe(viewLifecycleOwner){
            if(it==null || it ==0){
                Utils.showToast(requireContext(),"Something went wrong")
            } else {
                viewModel.getStudentList()
            }
        }
    }

    private fun getStudentModelFromStudent(studentList: List<Student>): MutableList<StudentModel> {
        val studentModelList : ArrayList<StudentModel>  = arrayListOf()
        for (currentStudent in studentList) {
            val studentModel = StudentModel(
                currentStudent.studentId,
                currentStudent.studentName,
                currentStudent.studentLastName,
                currentStudent.studentPhone,
                currentStudent.studentCourse,
                currentStudent.studentImg
            )
            studentModelList.add(studentModel)
        }
        return studentModelList
    }

    private fun initView() {
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        studentListAdapter = StudentListAdapter(this)
        binding.rvStudentList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentListAdapter
//            studentListAdapter.submitList(quoteListModel.results)
        }
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when(p0?.id) {
                tvAddNewStudent.id -> {
                    (requireActivity() as StudentActivity).addFragmentToBackStack(AddStudentFragment())
                }
                tvLogOut.id -> {
                    SharedPreferences.setBoolean(requireContext(), Constants.IS_LOGGED_IN,false)
                    val intent = Intent(requireActivity().application,MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    override fun onDeleteClick(student: StudentModel) {
        viewModel.deleteStudentById(student.studentId)
    }

}