package com.example.poo2.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poo2.model.Contact

class ContactViewModel : ViewModel() {
    var contacts = MutableLiveData<ArrayList<Contact>>()
    var selectContactPosition: Int? = null
    init {
        contacts.value = arrayListOf(

        )
    }

}