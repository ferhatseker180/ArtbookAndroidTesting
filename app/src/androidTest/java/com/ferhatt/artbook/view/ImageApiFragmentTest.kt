package com.ferhatt.artbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.ferhatt.artbook.R
import com.ferhatt.artbook.adapter.ImageRecyclerAdapter
import com.ferhatt.artbook.getOrAwaitValue
import com.ferhatt.artbook.launchFragmentInHiltContainer
import com.ferhatt.artbook.repo.FakeArtRepositoryAndroidTest
import com.ferhatt.artbook.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {

    @get:Rule
    var instantTaskExecuterRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun selectImageTest(){
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "ferhatseker.com"
        val testViewModel = ArtViewModel(FakeArtRepositoryAndroidTest())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory,
        ){
            Navigation.setViewNavController(requireView(),navController)
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(0,click())
        )

        Mockito.verify(navController).popBackStack()

        assertThat(testViewModel.selectedUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)

    }


}