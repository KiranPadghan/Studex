package com.example.studex.modules.loginModule.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.studex.databinding.FragmentLoginBinding
import com.example.studex.modules.viewmodel.LoginViewModel
import com.example.studex.utils.Constants
import com.example.studex.utils.SharedPreferences
import com.example.studex.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.loginSuccessResponse.observe(viewLifecycleOwner){
            if (it){
                SharedPreferences.setBoolean(requireContext(),Constants.IS_LOGGED_IN,true)
                openStudentActivity()
            } else {
                Utils.showToast(requireContext(),"Invalid Credentials")
            }
        }
        viewModel.saveUserResponse.observe(viewLifecycleOwner){
            if (it==null || it ==-1L){

            } else {
                binding.etUsername.setText("")
                binding.etPassword.setText("")
            }
        }
    }

    private fun setListeners() {
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        binding.tvLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.cbShowPassword.setOnCheckedChangeListener{ _, _ ->
            if (binding.cbShowPassword.isChecked){
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }
    }

    private fun openStudentActivity(){
        val intent = Intent(requireActivity().application, StudentActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun isValid(): Boolean{
        binding.apply {
            if (etUsername.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please enter username")
                return false
            }
            if (etPassword.text.toString().isEmpty()){
                Utils.showToast(requireContext(),"Please enter password")
                return false
            }
            return true
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.tvLogin.id -> {
                if (isValid()){
                    viewModel.doLogin(binding.etUsername.text.toString(),binding.etPassword.text.toString())
                }
            }
            binding.tvRegister.id -> {
                (requireActivity() as MainActivity).addFragmentToBackStack(RegisterUserFragment())
            }
        }
    }

}