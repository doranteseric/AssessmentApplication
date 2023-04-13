package com.doranteseric.assessmentapplication

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactsScreen(question: TriviaQuestion, navController: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Contacts") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            elevation = 8.dp
        )

        val context = LocalContext.current
        val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)

        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }

        if (permissionState.hasPermission) {
            // Create the ContactsRepository and ContactsViewModelFactory instances
            val contactsRepository = ContactsRepositoryImpl(context)
            val viewModelFactory = ContactsViewModelFactory(contactsRepository)

            // Use the viewModelFactory to obtain an instance of ContactsViewModel
            val contactsViewModel: ContactsViewModel = viewModel(factory = viewModelFactory)
            val contacts = contactsViewModel.contacts.value

            LazyColumn {
                itemsIndexed(contacts) { index, contact ->
                    ContactListItem(contact) {
                        // Handle contact click here or call a function from the ViewModel
                        // For example:
                        // contactsViewModel.onContactClicked(contact)
                    }
                }
            }
        } else {
            Text("Permission to read contacts is not granted.")
        }

    }
}

@Composable
fun ContactListItem(contact: Contact, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 0.dp
                )
                .sizeIn(
                    minWidth = 200.dp,
                    minHeight = 50.dp
                )
        ) {
            Text(
                text = contact.name,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}


