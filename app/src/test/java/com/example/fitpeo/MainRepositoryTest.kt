package com.example.fitpeo

import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.network.ApiService
import com.example.fitpeo.respository.MainRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainRepositoryTest {

    lateinit var mainRepository: MainRepository

    @Mock
    lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(UnconfinedTestDispatcher(), apiService)
    }

    @Test
    fun `get all data`() {
        runBlocking {
            Mockito.`when`(apiService.getList()).thenReturn(listOf<ResponseData>())
            val response = mainRepository.getDataList()
            assertEquals(listOf<ResponseData>(), response.data)
        }

    }

}