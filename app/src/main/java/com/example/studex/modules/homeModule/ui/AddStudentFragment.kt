package com.example.studex.modules.homeModule.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.studex.databinding.FragmentAddStudentBinding
import com.example.studex.modules.viewmodel.HomeViewModel
import com.example.studex.roomDb.entities.Student
import com.example.studex.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class AddStudentFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentAddStudentBinding
    private var selectedImg : Bitmap? = null

    lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.studentInsertResponse.observe(viewLifecycleOwner){
            if (it == null) {
                Utils.showToast(requireContext(),"Something went wrong")
            } else {
                Utils.showToast(requireContext(),"Student added successfully")
                viewModel.getStudentList()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setListeners() {
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.tvUploadProfile.setOnClickListener(this)
        binding.tvAddStudent.setOnClickListener(this)

        CoroutineScope(Dispatchers.Main).launch {
            println("Dispatchers Main ${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.Default).launch {
            println("Dispatchers DEF ${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.IO).launch {
            println("Dispatchers IO ${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.Unconfined).launch {
            println("Dispatchers UNCONFINED ${Thread.currentThread().name}")
        }

        GlobalScope.launch {
            println("Dispatchers glob ${Thread.currentThread().name}")
        }

        lifecycleScope.launch {
            println("Dispatchers lifecycle ${Thread.currentThread().name}")
        }
    }

    private val launcherActivity: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    try {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)        // check new method
                        binding.ivProfileImg.setImageBitmap(bitmap)
                        selectedImg = bitmap
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            }
        }


    @SuppressLint("IntentReset")
    private fun getImage() {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"
            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"
            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
            launcherActivity.launch(chooserIntent)
    }


    private fun isValidData(): Boolean {
        binding.apply {
            if (etFirstName.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please enter first name")
                return false
            }
            if (etLastName.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please enter last name")
                return false
            }
        }
        return true
    }



//    private fun getCompImg(img: Bitmap?): Bitmap? {
//       if (img != null){
//           val stream = ByteArrayOutputStream()
//           img.compress(Bitmap.CompressFormat.PNG, 100, stream)
//           var getImg =  stream.toByteArray()
//           while (getImg.size > 500000) {
//               val bitmap = BitmapFactory.decodeByteArray(getImg, 0, getImg.size)
//               val resized = Bitmap.createScaledBitmap(
//                   bitmap,
//                   (bitmap.width * 0.8).toInt(),
//                   (bitmap.height * 0.8).toInt(),
//                   true
//               )
//               val streamImg = ByteArrayOutputStream()
//               resized.compress(Bitmap.CompressFormat.PNG, 100, streamImg)
//               getImg = streamImg.toByteArray()
//           }
//           return BitmapFactory.decodeByteArray(getImg, 0, getImg.size)
//       }
//       return null
//
//    }

    override fun onClick(p0: View?) {
            binding.apply {
                when(p0?.id) {
                    tvUploadProfile.id -> {
                        getImage()
                    }
                    tvAddStudent.id -> {
                        if (isValidData()){
                            viewModel.addStudentModel(Student(0L,etFirstName.text.toString().trimEnd(),etLastName.text.toString().trimEnd(),etPhoneNumber.text.toString().trimEnd(),etCourse.text.toString().trimEnd(), selectedImg))
                        }
                    }
                }
        }
    }


}