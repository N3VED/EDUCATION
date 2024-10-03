package com.example.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.core.Animation
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView

class ResultFragment : Fragment() {

    private lateinit var mViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        val textThird = view.findViewById<TextView>(R.id.text_third)
        val backToStartButton = view.findViewById<Button>(R.id.back_to_start)
        val lottieAnimation = view.findViewById<LottieAnimationView>(R.id.lottie_animation)

        ObjectAnimator.ofFloat(
            backToStartButton,
            "translationY",
            0f,
            -25f
        ).apply {
            duration = 500
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }.start()

        ObjectAnimator.ofArgb(
            textThird,
            "textColor",
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.ripple)
        ).apply {
            duration = 500
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            start()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    back()
                }
            })

        textThird.text = getString(R.string.answer_result_text, mViewModel.result.value.toString())

        backToStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_questionsFragment)
            mViewModel.wipeLiveDataAnswers()
        }

        return view
    }

    private fun back() {
        findNavController().navigate(R.id.action_resultFragment_to_greetingFragment)
        mViewModel.wipeLiveDataName()
        mViewModel.wipeLiveDataAnswers()
    }
}