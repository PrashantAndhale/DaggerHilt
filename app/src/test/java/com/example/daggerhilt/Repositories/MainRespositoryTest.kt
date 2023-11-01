package com.example.daggerhilt.Repositories

import com.example.daggerhilt.Model.Post
import com.example.daggerhilt.Network.ApiServiceImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    private lateinit var mainRepository: MainRespository

    @Mock
    private lateinit var apiServiceImp: ApiServiceImp

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        mainRepository = MainRespository(apiServiceImp)
    }

    @Test
    fun `getPost() should emit list of posts`() {
        // Given
        val testData = listOf(Post("Test Post 1"), Post("Test Post 2"))
        runBlocking {
            `when`(apiServiceImp.getPost()).thenReturn(testData)
            // When
            val result = mainRepository.getPost().first()
            // Then
            assertEquals(testData, result)
        }
    }


}

