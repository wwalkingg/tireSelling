package core.component_base

import androidx.compose.runtime.Stable

@Stable
sealed interface BooleanAndLoadUIState {
    object True : BooleanAndLoadUIState
    object Loading : BooleanAndLoadUIState
    object False : BooleanAndLoadUIState
}