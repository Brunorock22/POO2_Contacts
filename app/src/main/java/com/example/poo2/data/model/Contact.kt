package com.example.poo2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(val name: String, val email: String, val phone: String){
    @PrimaryKey(autoGenerate = true)
    var contactId: Int = 0
}