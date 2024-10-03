package com.example.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class QuestionsFragment : Fragment() {

    private lateinit var mViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        val textNameSurname = view.findViewById<TextView>(R.id.name_surname_text)
        val buttonAnswer = view.findViewById<Button>(R.id.button_answer)
        val buttonBack = view.findViewById<Button>(R.id.button_back)
        val firstQuestionRadioGroup = view.findViewById<RadioGroup>(R.id.first_question_radio_group)
        val secondQuestionRadioGroup =
            view.findViewById<RadioGroup>(R.id.second_question_radio_group)
        val thirdQuestionRadioGroup = view.findViewById<RadioGroup>(R.id.third_question_radio_group)

        animateRadioGroup(firstQuestionRadioGroup)
        animateRadioGroup(secondQuestionRadioGroup)
        animateRadioGroup(thirdQuestionRadioGroup)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    back()
                }
            })

        textNameSurname.text =
            getString(R.string.name_surname_string, mViewModel.surname.value, mViewModel.name.value)

        firstQuestionRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val selectedRadioButton = radioGroup.findViewById<RadioButton>(i)
            mViewModel.setFirstQuestionAnswer(selectedRadioButton.text.toString())
        }

        secondQuestionRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val selectedRadioButton = radioGroup.findViewById<RadioButton>(i)
            mViewModel.setSecondQuestionAnswer(selectedRadioButton.text.toString())
        }

        thirdQuestionRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val selectedRadioButton = radioGroup.findViewById<RadioButton>(i)
            mViewModel.setThirdQuestionAnswer(selectedRadioButton.text.toString())
        }

        mViewModel.combinedAnswers.observe(requireActivity()) {
            buttonAnswer.isEnabled = it
        }

        buttonBack.setOnClickListener {
            back()
        }

        buttonAnswer.setOnClickListener {
            findNavController().navigate(R.id.action_questionsFragment_to_resultFragment)
        }

        return view
    }

    private fun animateRadioGroup(radioGroup: RadioGroup) {
        radioGroup.animate().apply {
            duration = 1000
            alpha(1F)
        }
    }

    private fun back() {
        findNavController().navigate(R.id.action_questionsFragment_to_greetingFragment)
        mViewModel.wipeLiveDataName()
        mViewModel.wipeLiveDataAnswers()
    }
}