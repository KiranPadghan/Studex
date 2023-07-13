package com.example.studex.modules.loginModule.ui

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.studex.databinding.FragmentRegisterUserBinding
import com.example.studex.modules.viewmodel.LoginViewModel
import com.example.studex.roomDb.entities.User
import com.example.studex.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUserFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentRegisterUserBinding
    lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.saveUserResponse.observe(viewLifecycleOwner){
            if (it == null){
                Utils.showToast(requireContext(),"Something went wrong")
            } else {
                openLoginFragment()
            }
        }
    }

    private fun setListeners() {
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        binding.tvRegister.setOnClickListener(this)
        binding.cbShowPassword.setOnCheckedChangeListener{ _, _ ->
            if (binding.cbShowPassword.isChecked){
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            if (binding.etPassword.hasFocus()){
                binding.etPassword.setSelection(binding.etPassword.text.length)
            } else {
                binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
            }
        }

    }

    private fun isValidData() : Boolean{
        binding.apply {
            if (etFirstName.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please Enter first Name")
                return false
            }
            if (etUsername.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please Enter username")
                return false
            }
            if (etPassword.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please Enter password")
                return false
            }
            if (etPassword.text.toString() != etConfirmPassword.text.toString()){
                Utils.showToast(requireContext(),"Please make sure password and confirm password are same")
                return false
            }
            return true
        }
    }

    private fun openLoginFragment(){
        parentFragmentManager.popBackStack()
//        (requireActivity() as MainActivity).addFragmentToBackStack(LoginFragment())
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.tvRegister.id -> {
                if (isValidData()){
                    val user = User(0L,binding.etUsername.text.toString(),binding.etFirstName.text.toString(),binding.etPassword.text.toString())
                    viewModel.saveUser(user)
                }
            }
        }
    }

}