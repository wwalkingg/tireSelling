package com.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.example.android.navigation.RootComponent
import com.example.android.navigation.RootContent
import com.example.android.ui.theme.AndroidTheme
import com.example.core.design_system.LocalRootSnackBarHostState

class MainActivity : ComponentActivity() {

    private val snackBarHostState = SnackbarHostState()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = RootComponent(defaultComponentContext())
        setContent {
            AndroidTheme {
                CompositionLocalProvider(LocalRootSnackBarHostState provides snackBarHostState) {
                    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
                        Surface(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            RootContent(component = root)
                        }
                    }
                }
            }
        }
    }
}