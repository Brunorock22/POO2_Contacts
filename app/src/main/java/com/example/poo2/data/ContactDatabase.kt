package com.example.poo2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.poo2.data.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase:  RoomDatabase() {
    abstract val contactDAO : ContactDAO
}