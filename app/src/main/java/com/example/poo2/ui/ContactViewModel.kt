package com.example.poo2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poo2.data.ContactDAO
import com.example.poo2.data.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel(private val dao: ContactDAO) : ViewModel() {
    private var _contacts = MutableLiveData<List<Contact>>()
    var contacts: LiveData<List<Contact>> = _contacts
    var selectContactPosition: Int? = null

    init {
        CoroutineScope(Dispatchers.Main).launch {
            _contacts.value = dao.loadAllContacts()
        }
    }

    fun addContact(contact: Contact) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertContact(contact)
            withContext(Dispatchers.Main) {
                _contacts.value = dao.loadAllContacts()
            }
        }
    }

    fun removeContact(contact: Contact, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteContact(contact)
            withContext(Dispatchers.Main){
                _contacts.value = dao.loadAllContacts()
                onSuccess.invoke()
            }
        }
    }

    fun updateContact(contact: Contact){
        CoroutineScope(Dispatchers.IO).launch{
            dao.updateContact(contact)
            withContext(Dispatchers.Main){
                _contacts.value = dao.loadAllContacts()
            }
        }
    }

}