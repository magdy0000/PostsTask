package com.magdy.poststask.datasource

import com.magdy.poststask.data.datasource.local.LocalDataSource
import com.magdy.poststask.data.datasource.local.MyDao
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


class LocalDataSourceTest {

    private lateinit var myDao: MyDao
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        myDao = mock(MyDao::class.java)
        localDataSource = LocalDataSource(myDao)
    }

    @Test
    fun `getPosts returns success with data when data is available`() {
        runBlocking {
            val mockData =
                listOf(ModelPostsData(body = "asdasd", title = "Local Post"
                    , userId = 1, id = 1))

            `when`(myDao.getData()).thenReturn(mockData)

            val result = localDataSource.getPosts()

            assertEquals(Resource.success(mockData), result)
        }
    }

    @Test
    fun `getPosts returns error when no data is available`() {
       runBlocking {
           val testData = emptyList<ModelPostsData>()
           `when`(myDao.getData()).thenReturn(testData)

           val result = localDataSource.getPosts()

           assert(!result.isSuccess())

           assertEquals(result.getErrorMessage(),"No data found")

       }
    }

    @Test
    fun `getPosts returns error when an exception occurs`() {
       runBlocking {
           val exception = MockitoException("Test exception")
           `when`(myDao.getData()).thenThrow(exception)

           val result = localDataSource.getPosts()

           assertEquals(exception.localizedMessage, result.getErrorMessage())
       }
    }
}
