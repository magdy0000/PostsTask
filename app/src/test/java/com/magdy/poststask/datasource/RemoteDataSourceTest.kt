package com.magdy.poststask.datasource

import com.magdy.poststask.data.datasource.local.LocalDataSource
import com.magdy.poststask.data.datasource.local.MyDao
import com.magdy.poststask.data.datasource.network.ApiCalls
import com.magdy.poststask.data.datasource.network.IRemoteDataSource
import com.magdy.poststask.data.datasource.network.RemoteDataSource
import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.domain.utils.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.exceptions.base.MockitoException


class RemoteDataSourceTest {

    private lateinit var apiCalls: ApiCalls
    private lateinit var remoteDataSource: IRemoteDataSource

    @Before
    fun setup() {
        apiCalls = mock(ApiCalls::class.java)
        remoteDataSource = RemoteDataSource(apiCalls)
    }

    @Test
    fun `getPosts should return success when API call is successful`() {
        runBlocking {
            val mockData =
                listOf(ModelPostsData(body = "asdasd", title = "Local Post"
                    , userId = 1, id = 1))

            `when`(apiCalls.getPost()).thenReturn(mockData)

            val result = remoteDataSource.getPosts()

            assertEquals(Resource.success(mockData), result)
        }
    }

    @Test
    fun `getPosts should return error when API call throws an exception`() {
       runBlocking {
           val exception = MockitoException("Test exception")
           `when`(apiCalls.getPost()).thenThrow(exception)

           val result = remoteDataSource.getPosts()

           assertEquals(exception.localizedMessage, result.getErrorMessage())
       }
    }
}
