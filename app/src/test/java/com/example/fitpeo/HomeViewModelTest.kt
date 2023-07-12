package com.example.fitpeo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.network.ApiService
import com.example.fitpeo.network.NetworkResult
import com.example.fitpeo.respository.MainRepository
import com.example.fitpeo.viewmodel.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var mainViewModel: HomeViewModel

    lateinit var mainRepository: MainRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        val context = mock(Context::class.java)
        //mainRepository = mock(MainRepository::class.java)
        mainRepository = MainRepository(UnconfinedTestDispatcher(), apiService)
        mainViewModel =
            HomeViewModel(UnconfinedTestDispatcher(), testDispatcher, mainRepository, context)
    }

    @Test
    fun getAllDataTest() {
        runBlocking {
            Mockito.`when`(mainRepository.getDataList())
                .thenReturn(
                    NetworkResult.success(
                        listOf<ResponseData>(
                            ResponseData(
                                1,
                                1,
                                "accusamus beatae ad facilis cum similique qui sunt",
                                "https://via.placeholder.com/600/92c952",
                                "https://via.placeholder.com/150/92c952"
                            )
                        )
                    )
                )
            mainViewModel.getData()
            val result = mainViewModel.dataResponse().getOrAwaitValue()
            assertEquals(
                NetworkResult.success(
                    listOf<ResponseData>(
                        ResponseData(
                            1,
                            1,
                            "accusamus beatae ad facilis cum similique qui sunt",
                            "https://via.placeholder.com/600/92c952",
                            "https://via.placeholder.com/150/92c952"
                        )
                    )
                ), result.data
            )
        }
    }


    @Test
    fun `empty list test`() {
        runBlocking {
            Mockito.`when`(mainRepository.getDataList())
                .thenReturn(NetworkResult.success((listOf<ResponseData>())))
            mainViewModel.getData()
            val result = mainViewModel.dataResponse().getOrAwaitValue()
            assertEquals(NetworkResult.success((listOf<ResponseData>())), result.data)
        }
    }
}