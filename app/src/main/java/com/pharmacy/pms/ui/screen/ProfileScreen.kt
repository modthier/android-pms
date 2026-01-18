package com.pharmacy.pms.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.repository.AuthRepository
import com.pharmacy.pms.ui.navigation.Screen
import com.pharmacy.pms.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = remember(context) {
            object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    val tokenManager = TokenManager(context)
                    val authRepository = AuthRepository(tokenManager)
                    @Suppress("UNCHECKED_CAST")
                    return AuthViewModel(authRepository, tokenManager) as T
                }
            }
        }
    )
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.isAuthenticated) {
        if (!uiState.isAuthenticated) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.userEmail != null) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = uiState.userEmail,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            
            Button(
                onClick = { viewModel.logout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Logout")
            }
        }
    }
}
