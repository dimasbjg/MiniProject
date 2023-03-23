package com.dimdimbjg.miniproject.data

import com.dimdimbjg.miniproject.data.source.remote.network.ResponseApi
import kotlinx.coroutines.flow.*

@Suppress("UNCHECKED_CAST")
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dataFromDb = populateDataFromDb().first()
        if (shouldFetch(dataFromDb)) {
            emit(Resource.Loading())
            when (val response = networkCall().first()) {
                is ResponseApi.Success -> {
                    saveCallResult(response.data)
                    emitAll(populateDataFromDb().map {
                        Resource.Success(it)
                    })
                }

                is ResponseApi.Empty -> {
                    emitAll(populateDataFromDb().map {
                        Resource.Success(it)
                    })
                }
                is ResponseApi.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        } else {
            emitAll(populateDataFromDb().map {
                Resource.Success(
                    it
                )
            })
        }
    }

    fun build() = result

    protected abstract fun populateDataFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun networkCall(): Flow<ResponseApi<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)
}