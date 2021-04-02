package com.example.poo2.ui

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poo2.adapter.ContactAdapter
import com.example.poo2.databinding.FragmentContactsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContactFragment : Fragment() {
    private val contactViewModel by sharedViewModel<ContactViewModel>()
    lateinit var contactAdapter: ContactAdapter

    lateinit var binding: FragmentContactsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        contactViewModel.contacts.observe(viewLifecycleOwner, Observer {
            if (it.size == 0){
                binding.imageEmptyContacts.visibility = View.VISIBLE
            }else{
                binding.imageEmptyContacts.visibility = View.GONE
            }
        })

        binding.floatAddContact.setOnClickListener {
            contactViewModel.selectContactPosition = null
            goToDetails()
        }
    }

    private fun initRecyclerView() {
        contactAdapter = ContactAdapter()
        contactAdapter.apply {
            gotDeleteItemClickListener = { position ->
                openDeleteDialog(position)
            }
            gotItemClickListener = { position ->
                contactViewModel.selectContactPosition = position
                goToDetails()
            }
        }
        val contactList = contactViewModel.contacts.value
        contactAdapter.submitList(contactList)
        contactAdapter.notifyDataSetChanged()

        binding.recyclerContacts.adapter = ConcatAdapter(contactAdapter)
        binding.recyclerContacts.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

    }

    private fun openDeleteDialog(position: Int){
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener {
                        _, _ ->
                        removeContact(position)
                    })
                setNegativeButton(R.string.cancel
                ) { _, _ ->
                    // User cancelled the dialog
                }
                setTitle("Atenção")
                setMessage("Deseja deletar esse contato?")
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun removeContact(position: Int) {
        contactViewModel.contacts.value?.removeAt(position)
        contactViewModel.contacts.postValue(contactViewModel.contacts.value)


        contactAdapter.notifyItemRemoved(position)
        contactViewModel.contacts.value?.size?.let {
            contactAdapter.notifyItemRangeChanged(position,
                it
            )
        }
    }

    private fun goToDetails() {
        val contactDetailsFragment = ContactDetailsFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.content, contactDetailsFragment, "param1")
            ?.addToBackStack(null)
            ?.commit()
    }
}