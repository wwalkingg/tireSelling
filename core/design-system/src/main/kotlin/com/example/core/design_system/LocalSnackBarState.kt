package com.example.core.design_system

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalRootSnackBarHostState = compositionLocalOf { SnackbarHostState() }
val LocalSnackBarHostState = compositionLocalOf { SnackbarHostState() }