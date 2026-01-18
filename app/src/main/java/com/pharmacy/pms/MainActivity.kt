package com.pharmacy.pms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.repository.*
import com.pharmacy.pms.ui.navigation.NavGraph
import com.pharmacy.pms.ui.navigation.Screen
import com.pharmacy.pms.ui.theme.PharmacyManagementTheme
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val tokenManager = TokenManager(this)
        val authRepository = AuthRepository(tokenManager)
        val drugRepository = DrugRepository(tokenManager)
        val stockRepository = StockRepository(tokenManager)
        val orderRepository = OrderRepository(tokenManager)
        
        setContent {
            PharmacyManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination = if (authRepository.getToken() != null) {
                        Screen.Dashboard.route
                    } else {
                        Screen.Login.route
                    }
                    
                    NavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
