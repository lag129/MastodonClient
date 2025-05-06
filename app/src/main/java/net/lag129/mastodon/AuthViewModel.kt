package net.lag129.mastodon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val serverName: StateFlow<String?> = dataStoreRepository.getServerName()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val bearerToken: StateFlow<String?> = dataStoreRepository.getBearerToken()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    suspend fun updateServerName(serverName: String) {
        dataStoreRepository.saveServerName(serverName)
    }

    suspend fun updateBearerToken(token: String) {
        dataStoreRepository.saveBearerToken(token)
    }
}