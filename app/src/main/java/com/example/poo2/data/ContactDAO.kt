package com.example.poo2.data

import androidx.room.*
import com.example.poo2.data.model.Contact

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(vararg contact: Contact)

    @Update
    suspend fun updateContact(vararg contact: Contact)

    @Delete
    suspend fun deleteContact(vararg contact: Contact)

    @Query("SELECT * FROM contact")
    suspend fun loadAllContacts(): List<Contact>
}