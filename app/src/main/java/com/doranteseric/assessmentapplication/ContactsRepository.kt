package com.doranteseric.assessmentapplication

import android.content.Context
import android.provider.ContactsContract
import retrofit2.Retrofit
import javax.inject.Inject

interface ContactsRepository {
    suspend fun getContacts(): List<Contact>
}

class ContactsRepositoryImpl(
    private val context: Context
) : ContactsRepository {
    override suspend fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if (cursor != null && cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            if (idIndex != -1 && nameIndex != -1 && phoneNumberIndex != -1) {
                do {
                    val id = cursor.getLong(idIndex)
                    val name = cursor.getString(nameIndex)
                    val phoneNumber = cursor.getString(phoneNumberIndex)

                    contacts.add(Contact(id, name, phoneNumber))
                } while (cursor.moveToNext())
            }

            cursor.close()
        }

        return contacts
    }
}
