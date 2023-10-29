package com.tugaypamuk.app.onestask.domain.use_cases.approve

import com.tugaypamuk.app.onestask.data.remote.dto.BaseResult
import com.tugaypamuk.app.onestask.domain.repository.OnesRepository
import com.tugaypamuk.app.onestask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

import javax.inject.Inject

class ApproveAccessLogUseCase @Inject constructor(
    private val repository: OnesRepository
) {
    operator fun invoke(parameters : HashMap<String,String>) : Flow<Resource<Response<Void>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.approveAccessLog(parameters)
            if (response.isSuccessful){
                emit(Resource.Success(response))
            }
            else {
                emit(Resource.Error("Fail"))
            }
        }catch (e : Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }
}