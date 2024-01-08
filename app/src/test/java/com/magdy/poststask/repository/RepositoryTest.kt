package com.magdy.poststask.repository

import com.magdy.poststask.data.datasource.local.ILocalDataSource
import com.magdy.poststask.data.datasource.network.IRemoteDataSource
import com.magdy.poststask.data.mappers.toDomain
import com.magdy.poststask.data.models.ModelPostsData
import com.magdy.poststask.data.repository.Repository
import com.magdy.poststask.domain.utils.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RepositoryTest {

    @Mock
    private lateinit var localDataSource: ILocalDataSource

    @Mock
    private lateinit var remoteDataSource: IRemoteDataSource

    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = Repository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getPosts should emit data from localDataSource when available`() = runBlocking {

        val mockData =
            listOf(ModelPostsData(body = "asdasd", title = "Local Post", userId = 1, id = 1))
        `when`(localDataSource.getPosts()).thenReturn(Resource.success(mockData))
        `when`(remoteDataSource.getPosts()).thenReturn(Resource.success(mockData))

        val result = repository.getPosts()

        result.take(1).collect { posts ->
            val expected = mockData.mapNotNull { it.toDomain() }
            assertEquals(Resource.success(expected), posts)
        }
    }

    @Test
    fun `getPosts emits remote data when available`() = runBlocking {
        val mockData =
            listOf(ModelPostsData(body = "asdasd", title = "Local Post", userId = 1, id = 1))
        `when`(localDataSource.getPosts()).thenReturn(Resource.success(mockData))
        `when`(remoteDataSource.getPosts()).thenReturn(Resource.success(mockData))

        val result = repository.getPosts()

        result.drop(1).collect { posts ->
            val expected = mockData.mapNotNull { it.toDomain() }
            assertEquals(Resource.success(expected), posts)
        }
        verify(localDataSource).cachePosts(mockData)


    }

    @Test
    fun `getPosts should emit error when remote data is not available`() = runBlocking {

        val mockData =
            listOf(ModelPostsData(body = "asdasd", title = "Local Post", userId = 1, id = 1))
        `when`(localDataSource.getPosts()).thenReturn(Resource.success(mockData))

        val errorMessage = "Remote data error message"
        `when`(remoteDataSource.getPosts()).thenReturn(Resource.error(Exception(errorMessage)))

        val result = repository.getPosts()

        result.drop(1).collect { posts ->
            assert(!posts.isSuccess())
            assertEquals(errorMessage, posts.getErrorMessage())
        }

    }


}
