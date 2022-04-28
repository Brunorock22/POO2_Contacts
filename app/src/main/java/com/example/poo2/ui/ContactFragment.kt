package com.example.poo2.ui

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poo2.adapter.ContactAdapter
import com.example.poo2.databinding.FragmentContactsBinding

class ContactFragment : Fragment() {
    private val contactViewModel: ContactViewModel by activityViewModels()

    private lateinit var contactAdapter: ContactAdapter

    lateinit var binding: FragmentContactsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        contactViewModel.contacts.observe(viewLifecycleOwner, {
            binding.run {
                if (it.isNullOrEmpty()) {
                    imageEmptyContacts.visibility = View.VISIBLE
                    recyclerContacts.visibility = View.GONE
                } else {
                    recyclerContacts.visibility = View.VISIBLE
                    imageEmptyContacts.visibility = View.GONE
                    contactAdapter.submitList(it)
                }
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

        binding.recyclerContacts.adapter = ConcatAdapter(contactAdapter)
        binding.recyclerContacts.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

    }

    private fun openDeleteDialog(position: Int) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.ok
                ) { _, _ ->
                    removeContact(position)
                }
                setNegativeButton(
                    R.string.cancel
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
        contactViewModel.contacts.value?.getOrNull(position)?.let {
            contactViewModel.removeContact(it)
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