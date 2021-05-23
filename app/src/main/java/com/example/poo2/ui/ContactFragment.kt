package com.example.poo2.ui

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poo2.adapter.ContactAdapter
import com.example.poo2.databinding.FragmentContactsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContactFragment : Fragment() {
    private val contactViewModel by sharedViewModel<ContactViewModel>()
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
            if (it.isNullOrEmpty()) {
                binding.imageEmptyContacts.visibility = View.VISIBLE
                binding.recyclerContacts.visibility = View.GONE
            } else {
                binding.recyclerContacts.visibility = View.VISIBLE
                binding.imageEmptyContacts.visibility = View.GONE
                contactAdapter.submitList(it)
                contactAdapter.notifyDataSetChanged()
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
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { _, _ ->
                        removeContact(position)
                    })
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
        contactViewModel.contacts.value?.get(position)?.let {
            contactViewModel.removeContact(it) {
                contactViewModel.contacts.value?.size?.let { size ->
                    contactAdapter.notifyItemRangeChanged(
                        position,
                        size
                    )
                }
            }
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