package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class ResultFragment : Fragment() {

    private lateinit var mViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    back()
                }
            })

        val textThird = view.findViewById<TextView>(R.id.text_third)
        val backToStartButton = view.findViewById<Button>(R.id.back_to_start)

        textThird.text = getString(R.string.answer_result_text, mViewModel.result.value.toString())

        backToStartButton.setOnClickListener {
            back()
        }

        return view
    }

    private fun back() {
        findNavController().navigate(R.id.action_resultFragment_to_greetingFragment)
        mViewModel.wipeLiveData()
    }
}