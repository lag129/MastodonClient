package net.lag129.mastodon

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {
    private val _data = mutableStateOf<List<Status>>(emptyList())
    private var maxId: String? = null
    val data: State<List<Status>> = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val result = ApiClient.apiService.fetchData(maxId)
                if (result.isNotEmpty()) {
                    maxId = result.last().id
                    _data.value = _data.value + result
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchNextPage() {
        fetchData()
    }
}
