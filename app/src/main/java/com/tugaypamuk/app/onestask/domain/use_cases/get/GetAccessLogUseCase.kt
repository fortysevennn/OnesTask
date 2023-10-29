package com.tugaypamuk.app.onestask.domain.use_cases.get

import com.tugaypamuk.app.onestask.data.remote.dto.toAccessLog
import com.tugaypamuk.app.onestask.data.remote.dto.toAccessLogEntity
import com.tugaypamuk.app.onestask.domain.model.AccessLog
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import com.tugaypamuk.app.onestask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccessLogUseCase @Inject constructor(
    private val repository: OnesRepository
) {
    operator fun invoke() : Flow<Resource<AccessLog?>> = flow {
        emit(Resource.Loading())
        try {
            val logs = repository.getAccessLog()
            repository.setAccessLog(logs.toAccessLogEntity())
            emit(Resource.Success(logs.toAccessLog()))
        }catch (e : Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }
}