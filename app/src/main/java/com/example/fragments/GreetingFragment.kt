package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class GreetingFragment : Fragment() {

    private lateinit var mViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_greeting, container, false)
        val navController = findNavController()
        mViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val nameEditText = view.findViewById<TextInputEditText>(R.id.input_edit_text_name)
        val surnameEditText = view.findViewById<TextInputEditText>(R.id.input_edit_text_surname)
        val saveButton = view.findViewById<Button>(R.id.button_save)

        nameEditText.addTextChangedListener {
            mViewModel.setName(it.toString())
        }
        surnameEditText.addTextChangedListener {
            mViewModel.setSurname(it.toString())
        }
        mViewModel.combinedNameSurname.observe(requireActivity()) {
            saveButton.isEnabled = it
        }

        saveButton.setOnClickListener {
            navController.navigate(R.id.action_greetingFragment_to_questionsFragment)
        }

        return view
    }
}