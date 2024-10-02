package com.example.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    companion object CorrectAnswers {
        const val FIRST_QUESTION: String = "6"
        const val SECOND_QUESTION: String = "4"
        const val THIRD_QUESTION: String = "1864"
    }

    private val _firstQuestionAnswer = MutableLiveData<String>()

    private val _secondQuestionAnswer = MutableLiveData<String>()

    private val _thirdQuestionAnswer = MutableLiveData<String>()

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> get() = _surname

    private val _result = MutableLiveData<Int>()
    val result: LiveData<Int> get() = _result

    private val _combinedNameSurname = MediatorLiveData<Boolean>()
    val combinedNameSurname: LiveData<Boolean> get() = _combinedNameSurname

    private val _combinedAnswers = MediatorLiveData<Boolean>()
    val combinedAnswers: LiveData<Boolean> get() = _combinedAnswers


    init {
        _combinedNameSurname.addSource(_name) {
            _combinedNameSurname.value = combineValues(_name, _surname)
        }

        _combinedNameSurname.addSource(_surname) {
            _combinedNameSurname.value = combineValues(_name, _surname)
        }
        _combinedAnswers.addSource(_firstQuestionAnswer) {
            _combinedAnswers.value =
                combineAnswers(_firstQuestionAnswer, _secondQuestionAnswer, _thirdQuestionAnswer)
                compareAnswers(_firstQuestionAnswer, _secondQuestionAnswer, _thirdQuestionAnswer)
        }
        _combinedAnswers.addSource(_secondQuestionAnswer) {
            _combinedAnswers.value =
                combineAnswers(_firstQuestionAnswer, _secondQuestionAnswer, _thirdQuestionAnswer)
                compareAnswers(_firstQuestionAnswer, _secondQuestionAnswer, _thirdQuestionAnswer)
        }
        _combinedAnswers.addSource(_thirdQuestionAnswer) {
            _combinedAnswers.value =
                combineAnswers(_firstQuestionAnswer, _secondQuestionAnswer, _thirdQuestionAnswer)
                compareAnswers(_firstQuestionAnswer, _secondQuestionAnswer, _thirdQuestionAnswer)
        }
    }

    private fun combineValues(
        value1: MutableLiveData<String>,
        value2: MutableLiveData<String>
    ): Boolean {
        return !value1.value.isNullOrBlank() && !value2.value.isNullOrBlank()
    }

    private fun combineAnswers(
        value1: MutableLiveData<String>,
        value2: MutableLiveData<String>,
        value3: MutableLiveData<String>
    ): Boolean {
        return !value1.value.isNullOrBlank() && !value2.value.isNullOrBlank() && !value3.value.isNullOrBlank()
    }

    private fun compareAnswers(
        value1: MutableLiveData<String>,
        value2: MutableLiveData<String>,
        value3: MutableLiveData<String>
    ) {
        var result = 0

        if (value1.value == FIRST_QUESTION) {
            result++
        }
        if (value2.value == SECOND_QUESTION) {
            result++
        }
        if (value3.value == THIRD_QUESTION) {
            result++
        }
        setResult(result)
    }

    fun wipeLiveDataName() {
        _name.value = ""
        _surname.value = ""
    }

    fun wipeLiveDataAnswers() {
        _firstQuestionAnswer.value = ""
        _secondQuestionAnswer.value = ""
        _thirdQuestionAnswer.value = ""
    }

    fun setFirstQuestionAnswer(string: String) {
        _firstQuestionAnswer.value = string
    }

    fun setSecondQuestionAnswer(string: String) {
        _secondQuestionAnswer.value = string
    }

    fun setThirdQuestionAnswer(string: String) {
        _thirdQuestionAnswer.value = string
    }

    fun setName(string: String) {
        _name.value = string
    }

    fun setSurname(string: String) {
        _surname.value = string
    }

    private fun setResult(int: Int) {
        _result.value = int
    }
}