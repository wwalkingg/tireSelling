package core.component_base

sealed interface LoadUIState<out T> {
    object Loading : LoadUIState<Nothing>
    data class Loaded<out T>(val data: T) : LoadUIState<T>
    data class Error(val error: Throwable) : LoadUIState<Nothing>
}