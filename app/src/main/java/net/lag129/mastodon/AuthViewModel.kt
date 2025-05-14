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

    private val _serverName = MutableStateFlow<String?>(null)
    val serverName = _serverName.asStateFlow()

    private val _bearerToken = MutableStateFlow<String?>(null)
    val bearerToken = _bearerToken.asStateFlow()

    init {
        getServerName()
        getBearerToken()
    }

    fun setServerName(serverName: String) {
        viewModelScope.launch {
            preferencesRepository.saveServerName(serverName)
        }
    }

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

    fun getBearerToken() {
        viewModelScope.launch {
            val bearerToken = preferencesRepository.readBearerToken()
            _bearerToken.emit(bearerToken.toString())
        }
    }
}