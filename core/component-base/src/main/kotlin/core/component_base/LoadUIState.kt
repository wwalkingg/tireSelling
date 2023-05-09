package core.component_base

import androidx.compose.runtime.Stable

@Stable
sealed interface LoadUIState<out T> {
    object Loading : LoadUIState<Nothing>
    data class Success<out T>(val data: T) : LoadUIState<T>
    data class Error(val error: Throwable) : LoadUIState<Nothing>
}

@Stable
sealed interface PostUIState {
    object None:PostUIState
    object Loading : PostUIState
    data class Success(val message:String = "") : PostUIState
    data class Error(val error: Throwable) : PostUIState
}