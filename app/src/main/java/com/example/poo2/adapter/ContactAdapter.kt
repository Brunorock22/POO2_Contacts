package com.example.poo2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.poo2.databinding.ItemContactBinding
import com.example.poo2.data.model.Contact

class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(DIFF_CALLBACK) {
    var gotDeleteItemClickListener: ((position: Int) -> Unit)? = null
    var gotItemClickListener: ((position: Int) -> Unit)? = null

    class ContactViewHolder(
        private val binding: ItemContactBinding,
        private val gotDeleteItemClickListener: ((position: Int) -> Unit)?,
        private val gotItemClickListener: ((position: Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, position: Int, itemCount: Int) {
            binding.run {
                contactName.text = contact.name
                contactEmail.text = contact.email
                contactDelete.setOnClickListener {
                    gotDeleteItemClickListener?.invoke(position)
                }
                container.setOnClickListener {
                    gotItemClickListener?.invoke(position)
                }
            }

        }

        companion object {
            fun create(
                parent: ViewGroup,
                gotDeleteItemClickListener: ((position: Int) -> Unit)?,
                gotItemClickListener: ((position: Int) -> Unit)?
            ): ContactViewHolder {
                val itemBinding = ItemContactBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ContactViewHolder(itemBinding, gotDeleteItemClickListener,gotItemClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        return ContactViewHolder.create(parent, gotDeleteItemClickListener,gotItemClickListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position), position, itemCount)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}