package com.tugaypamuk.app.onestask.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.tugaypamuk.app.onestask.data.local.entitiy.MailSettingsEntity
import com.tugaypamuk.app.onestask.domain.model.AccessLog
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import com.tugaypamuk.app.onestask.domain.use_cases.AccessLogUseCases
import com.tugaypamuk.app.onestask.services.Email
import com.tugaypamuk.app.onestask.services.EmailService
import com.tugaypamuk.app.onestask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accessLogUseCase: AccessLogUseCases,
    private val repository: OnesRepository,
) : ViewModel() {


    private val _approveIsLoading = MutableLiveData<Boolean>()
    val approveIsLoading: LiveData<Boolean>
        get() = _approveIsLoading

    private val _approveError = MutableLiveData<String?>()
    val approveError: LiveData<String?>
        get() = _approveError

    private val _approveStatus = MutableLiveData<String?>()
    val approveStatus: LiveData<String?>
        get() = _approveStatus.distinctUntilChanged()


    fun approveAccessLog(parameters: HashMap<String, String>) {
        viewModelScope.launch {
            accessLogUseCase.approveAccessLogUseCase.invoke(parameters).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _approveIsLoading.value = false
                        _approveStatus.value = "Success"
                    }

                    is Resource.Error -> {
                        _approveIsLoading.value = false
                        _approveError.value = response.message
                    }

                    is Resource.Loading -> {
                        _approveIsLoading.value = true
                    }
                }
            }
        }
    }

    private val _accessLog = MutableLiveData<Resource<AccessLog?>>()
    val accessLog: LiveData<Resource<AccessLog?>>
        get() = _accessLog.distinctUntilChanged()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    fun getAccessLog() {
        viewModelScope.launch {

            accessLogUseCase.getAccessLogUseCase.invoke().collect { data ->
                when (data) {
                    is Resource.Success -> {
                        _accessLog.value = data
                        _isLoading.value = false
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _error.value = data.message
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }

    private val _isMailServerExist = MutableLiveData<Boolean>()
    val isMailServerExist: LiveData<Boolean>
        get() = _isMailServerExist.distinctUntilChanged()

    private val _mailServer = MutableLiveData<MailSettingsEntity?>()


    fun getMailServer() {
        viewModelScope.launch {
            repository.getMailSettings().collect { mailServer ->
                if (mailServer != null) {
                    _mailServer.value = mailServer
                    _isMailServerExist.value = true
                } else {
                    _isMailServerExist.value = false
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendEmail(email: Email) {
        GlobalScope.launch {
            val emailService = EmailService(
                smtpServer = _mailServer.value?.host.toString(),
                username = _mailServer.value?.username.toString(),
                password = _mailServer.value?.password.toString(),
                mailFrom = _mailServer.value?.sendFrom.toString(),
                port = _mailServer.value?.port.toString()
            )
            emailService.sendEmail(email)
        }
    }
}









