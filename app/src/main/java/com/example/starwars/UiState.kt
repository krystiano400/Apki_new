// klasa UiState.kt
data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
)