package com.tugaypamuk.app.onestask.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import com.tugaypamuk.app.onestask.domain.model.AccessLog
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import com.tugaypamuk.app.onestask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    private val repository: OnesRepository

) : ViewModel() {

    private val _logs = MutableLiveData<List<OnesLogEntitiy>>()
    val logs : LiveData<List<OnesLogEntitiy>>
        get() = _logs

    fun getLogsFromDatabase(){
        viewModelScope.launch {
            repository.getAccessLogFromDatabase().collect{ logs ->
                _logs.value = logs
            }
        }
    }

}