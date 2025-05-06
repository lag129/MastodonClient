package net.lag129.mastodon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    fun setServerName(serverName: String) {
        viewModelScope.launch {
            preferencesRepository.saveServerName(serverName)
        }
    }

    private val _serverName = MutableStateFlow<String?>(null)
    val serverName = _serverName.asStateFlow()
    fun getServerName() {
        viewModelScope.launch {
            val serverName = preferencesRepository.readServerName()
            _serverName.emit(serverName.toString())
        }
    }

    fun setBearerToken(bearerToken: String) {
        viewModelScope.launch {
            preferencesRepository.saveBearerToken(bearerToken)
        }
    }

    private val _bearerToken = MutableStateFlow<String?>(null)
    val bearerToken = _bearerToken.asStateFlow()
    fun getBearerToken() {
        viewModelScope.launch {
            val bearerToken = preferencesRepository.readBearerToken()
            _bearerToken.emit(bearerToken.toString())
        }
    }
}