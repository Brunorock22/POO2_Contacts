package com.example.poo2.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.poo2.data.ContactDAO
import com.example.poo2.data.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val dao: ContactDAO
) : ViewModel() {
    private var _contacts = MutableLiveData<List<Contact>>()
    var contacts: LiveData<List<Contact>> = _contacts
    var selectContactPosition: Int? = null

    fun getContacts(){
        viewModelScope.launch {
        _contacts.postValue(dao.loadAllContacts())
        }

    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            dao.insertContact(contact)
            withContext(Dispatchers.Main) {
                _contacts.value = dao.loadAllContacts()
            }
        }
    }

    fun removeContact(contact: Contact) {
        viewModelScope.launch {
            dao.deleteContact(contact)
            withContext(Dispatchers.Main) {
                _contacts.value = dao.loadAllContacts()
            }
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            dao.updateContact(contact)
            withContext(Dispatchers.Main) {
                _contacts.value = dao.loadAllContacts()
            }
        }
    }

}