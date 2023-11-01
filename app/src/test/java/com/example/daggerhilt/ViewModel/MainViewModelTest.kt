package com.example.daggerhilt.ViewModel

import com.example.daggerhilt.Model.Post
import com.example.daggerhilt.Repositories.MainRespository
import com.example.daggerhilt.Utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var mainRespository: MainRespository

    private lateinit var mainViewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        mainViewModel = MainViewModel(mainRespository)
    }


    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test finishes
    }

    @Test
    fun `getPost() should update postStateFlow with success data`() = runBlocking {
        // Given
        val testData = listOf(Post("Value1"))
        val successState = ApiState.Success(testData)
        `when`(mainRespository.getPost()).thenReturn(flowOf(testData))

        // When
        mainViewModel.getPost()

        // Then
        val postStateFlowValue = mainViewModel._postStateFlow.first()
        assertEquals(successState, postStateFlowValue)
    }

    @Test
    fun `getPost() should update postStateFlow with error state on repository error`() = runBlocking {
        // Given
        val errorMessage = "Error fetching posts"
        val error = Exception(errorMessage)
        val errorState = ApiState.Failure(error)
        `when`(mainRespository.getPost()).thenReturn(flow { throw error })

        // When
        mainViewModel.getPost()

        // Then
        val postStateFlowValue = mainViewModel._postStateFlow.first()
        assertEquals(errorState, postStateFlowValue)
    }
}



