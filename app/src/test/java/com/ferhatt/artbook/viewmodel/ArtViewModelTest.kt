package com.ferhatt.artbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ferhatt.artbook.MainCoroutineRule
import com.ferhatt.artbook.getOrAwaitValueTest
import com.ferhatt.artbook.repo.FakeArtRepository
import com.ferhatt.artbook.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel : ArtViewModel

    @Before
    fun setup(){

        // Test Doubles  : Fake Repository test edilir yani kopyasını
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){

        viewModel.makeArt("Monalisa","Da Vinci","")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        // Yukarıdaki işlemlerle Livedata formatından kurtarıp Main Thread üzerinde test edilir hale getirdik.
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){

        viewModel.makeArt("","Da Vinci","1650")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){

        viewModel.makeArt("Monalisa","","1650")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

}