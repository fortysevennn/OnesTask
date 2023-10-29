package com.tugaypamuk.app.onestask.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: OnesRepository
) : ViewModel() {

    fun addMailServer(mailSettingsEntity: MailSettingsEntity){
        viewModelScope.launch {
            repository.addMailSettings(mailSettingsEntity)
        }
    }
    private val _mailServerList = MutableLiveData<MailSettingsEntity?>()
    val mailServerList: LiveData<MailSettingsEntity?>
        get() = _mailServerList
    fun getMailServer(){
        viewModelScope.launch {
            repository.getMailSettings().collect{
                _mailServerList.value  = it
            }
        }
    }
}