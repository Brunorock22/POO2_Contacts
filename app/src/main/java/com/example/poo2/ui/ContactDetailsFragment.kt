package com.example.poo2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.poo2.databinding.FragmentContactDetailsBinding
import com.example.poo2.model.Contact
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ContactDetailsFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailsBinding
    private val contactViewModel: ContactViewModel by sharedViewModel()


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
                editElementInContact(
                    Contact(
                        binding.detailsName.text.toString(),
                        binding.detailsEmail.text.toString(),
                        binding.detailsNumber.text.toString()
                    )
                )
            } else {
                addElemnentInContacts(
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

            binding.run {
                detailsName.setText(it.name)
                detailsEmail.setText(it.email)
                detailsNumber.setText(it.phone)
            }
        }
    }

    private fun addElemnentInContacts(contact: Contact) {
        val newList = contactViewModel.contacts.value
        newList?.add(contact)
        contactViewModel.contacts.value = newList
    }

    private fun editElementInContact(contact: Contact) {
        contactViewModel.selectContactPosition?.let {
            contactViewModel.contacts.value?.set(
                it,
                contact
            )
        }
    }

}