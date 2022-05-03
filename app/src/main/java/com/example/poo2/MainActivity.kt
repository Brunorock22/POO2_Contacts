package com.example.poo2

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.poo2.databinding.ActivityMainBinding
import com.example.poo2.ui.ContactFragment
import com.example.poo2.ui.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val contactViewModel : ContactViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactViewModel.getContacts()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showContactsFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                showContactsFragment()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showContactsFragment(){
        val fragment: Fragment = ContactFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }
}