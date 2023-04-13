package com.doranteseric.assessmentapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doranteseric.assessmentapplication.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsViewModel(
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    val contacts = mutableStateOf(listOf<Contact>())

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            contacts.value = contactsRepository.getContacts()
        }
    }
}

class ContactsViewModelFactory(private val contactsRepository: ContactsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(contactsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

