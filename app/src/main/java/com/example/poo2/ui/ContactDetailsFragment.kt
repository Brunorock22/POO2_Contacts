package com.example.poo2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.poo2.databinding.FragmentContactDetailsBinding
import com.example.poo2.data.model.Contact
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ContactDetailsFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailsBinding
    private val contactViewModel: ContactViewModel by sharedViewModel()
    private lateinit var currentContact: Contact


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactViewModel.selectContactPosition?.let {
            feedWhenEdit(it)
        }


        binding.detailsSave.setOnClickListener {
            if (contactViewModel.selectContactPosition != null) {
                    val updatedContact = Contact(
                        name = binding.detailsName.text.toString(),
                        email = binding.detailsEmail.text.toString(),
                        phone = binding.detailsNumber.text.toString()
                    )
                updatedContact.contactId  = currentContact.contactId
                editElementInContact(updatedContact)
            } else {
                addElementInContacts(
                    Contact(
                        binding.detailsName.text.toString(),
                        binding.detailsEmail.text.toString(),
                        binding.detailsNumber.text.toString()
                    )
                )
            }
            val fm: FragmentManager? = activity
                ?.supportFragmentManager
            fm?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

    }

    private fun feedWhenEdit(position: Int) {
        val contact = contactViewModel.contacts.value?.get(position)
        contact?.let {
            currentContact = it
            binding.run {
                detailsName.setText(it.name)
                detailsEmail.setText(it.email)
                detailsNumber.setText(it.phone)
            }
        }
    }

    private fun addElementInContacts(contact: Contact) {
        contactViewModel.addContact(contact)
    }

    private fun editElementInContact(contact: Contact) {
        contactViewModel.updateContact(contact)
    }

}