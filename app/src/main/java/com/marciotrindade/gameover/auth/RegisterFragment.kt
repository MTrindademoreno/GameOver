package com.marciotrindade.gameover.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marciotrindade.gameover.R
import com.marciotrindade.gameover.databinding.FragmentRegisterBinding
import com.marciotrindade.gameover.model.User
import com.marciotrindade.gameover.utils.Validation


class RegisterFragment : Fragment() {
    private val auth by lazy {
        Firebase.auth
    }

    private val db by lazy {
        Firebase.firestore
    }
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentRegisterBinding
    private var currentUser: FirebaseUser? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        verifyUserAuth()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel.createUserState.observe(viewLifecycleOwner, Observer {
            if (it != null) {


                saveUserInFirebase()

                findNavController().navigate(R.id.loginFragment)

            } else {
                Toast.makeText(requireContext(), "Erro ao criar usuário", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun verifyUserAuth() {
        currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(requireContext(), "usuário logado:${currentUser?.email}", Toast.LENGTH_LONG).show()
        } else {

            activity?.let {
                binding.btnCreateAccount.setOnClickListener {
                    register()
                }
            }
        }
    }

    private fun register() {

        if (verifyInput()) {
            val mail = binding.edtEmailRegister.editableText.toString()
            val password = binding.edtPasswordRegister.editableText.toString()
            viewModel.createUser(mail, password)
            Toast.makeText(requireContext(), "clique", Toast.LENGTH_LONG).show()

        }
    }


    private fun saveUserInFirebase() {
        val name = binding.edtNameRegister.editableText.toString()
        val email = binding.edtEmailRegister.editableText.toString()
        val id = auth.currentUser?.uid.toString()
        val user = User(name,email,id)

        viewModel.saveUserInFirebase(user)
//
//        db.collection("users")
//            .document(auth.currentUser?.uid ?: "")
//            .set(user)
//            .addOnSuccessListener {
//                Log.d("firebase", "Usuario salvo")
//
//            }
//            .addOnFailureListener {
//                Log.d("firebase", "falha ao salvar usuario")
//            }
    }


    private fun verifyInput(): Boolean {
        val edtUserName = binding.edtNameRegister
        val tilUserName = binding.tilNameRegister
        val edtEmail = binding.edtEmailRegister
        val tilEmail = binding.tilEmailRegister
        val edtPassword = binding.edtPasswordRegister
        val tilPassword = binding.tilPasswordRegister
        val edtRepPassword = binding.edtPasswordRegisterConfirm
        val tilRepPassword = binding.tilPasswordRegisterConfirm

        Validation(requireContext()).apply {
            return isEditTextFilled(edtUserName, tilUserName, getString(R.string.user_name))
                    && isEmailValid(edtEmail, tilEmail)
                    && isEditTextFilled(edtPassword, tilPassword, getString(R.string.password))
                    && isEditTextFilled(
                edtRepPassword,
                tilRepPassword,
                getString(R.string.repeat_password)
            )
                    && isPasswordsEquals(edtPassword, edtRepPassword, tilRepPassword)
        }
    }

}