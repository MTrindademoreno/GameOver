package com.marciotrindade.gameover.auth

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marciotrindade.gameover.R
import com.marciotrindade.gameover.databinding.LoginFragmentBinding
import com.marciotrindade.gameover.network.Network
import com.marciotrindade.gameover.utils.Constants.login.CB_MODE
import com.marciotrindade.gameover.utils.Constants.login.EMAIL
import com.marciotrindade.gameover.utils.Constants.login.PASSWORD
import com.marciotrindade.gameover.utils.Validation

class LoginFragment : Fragment() {
    private val db by lazy {
        Firebase.firestore
    }
    private val auth by lazy {
        Firebase.auth
    }


    private val memail by lazy {
        binding.edtEmailLogin
    }

    private val mpassword by lazy {
        binding.edtPasswordLogin
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var viewModel: AuthViewModel
    private val network by lazy {
        Network(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root

        //loadGameCurrentUser()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)




        networkMonitor()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)



        activity?.let {

            sharedPreferences = it.getSharedPreferences("user", MODE_PRIVATE)
            initPreferences()

            with(binding) {
                tvCreateAccount.setOnClickListener {
                    findNavController().navigate(R.id.registerFragment)
                }
                setupObservable()

                btnLogin.setOnClickListener {
                   val email = memail.editableText.toString()
                    val password = mpassword.editableText.toString()
                    if (verifyInput()) {
                        viewModel.signIn(email, password)

                    }
                    return@setOnClickListener
                }
                edtPasswordLogin.setOnFocusChangeListener { _, _ ->
                    cbLogin.isEnabled = true
                }



                cbLogin.setOnCheckedChangeListener { _, isChecked ->
                    if (verifyInput()) {

                        sharedPreferences.edit {
                            putString(EMAIL, binding.edtEmailLogin.editableText.toString())
                            putString(PASSWORD, binding.edtPasswordLogin.editableText.toString())
                            putBoolean(CB_MODE, isChecked)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Preencha os dados antes de selecionar",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        return@setOnCheckedChangeListener
                    }

                }

                btnContinue.setOnClickListener {
                    loadGames()
                }
                btnLogout.setOnClickListener {
                    Firebase.auth.signOut()
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
    }



    @SuppressLint("ResourceAsColor")
    private fun networkMonitor() {


        network.observe(viewLifecycleOwner, { isconnected ->
            if (isconnected) {
                Toast.makeText(requireContext(), "Conectado", Toast.LENGTH_LONG).show()
                binding.btnLogin.isClickable = true
               binding.imgNetwork.visibility =View.GONE

            } else {
                Toast.makeText(requireContext(), "Desconectado", Toast.LENGTH_LONG).show()
                binding.imgNetwork.visibility = View.VISIBLE

                binding.btnLogin.isClickable = false


            }

        })
    }

    private fun setupObservable() {
      viewModel.authenticationState.observe(viewLifecycleOwner, Observer {
          if(it!=null){
              loadGames()
          }else {
              Toast.makeText(requireContext(),"Falha ao logar",Toast.LENGTH_LONG).show()
          }

      })

    }


    override fun onResume() {
        super.onResume()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.loginLayout.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
            binding.btnLogout.visibility = View.VISIBLE
        }
    }

    private fun verifyInput(): Boolean {
        val edtEmail = memail
        val tilEmail = binding.tilEmailLogin
        val edtPassword = mpassword
        val tilPassword = binding.tilPasswordLogin

        Validation(requireContext()).apply {
            return isEmailValid(edtEmail, tilEmail)
                    && isEditTextFilled(edtPassword, tilPassword, getString(R.string.password))


        }
    }

    private fun initPreferences() {
        val cbMode = sharedPreferences.getBoolean(CB_MODE, true)
        binding.cbLogin.isChecked = cbMode
        if (binding.cbLogin.isChecked) {
            val sEmail = sharedPreferences.getString(EMAIL, "")
            val sPassword = sharedPreferences.getString(PASSWORD, "")
            binding.edtEmailLogin.setText(sEmail)
            binding.edtPasswordLogin.setText(sPassword)


        }

    }

    private fun loadGames() {

        findNavController().navigate(R.id.listActivity)
    }



}