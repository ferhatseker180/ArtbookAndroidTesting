package com.ferhatt.artbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.ferhatt.artbook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecuterRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var dao : ArtDao

    @Before
    fun setup(){
      /*  database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDatabase::class.java).allowMainThreadQueries().build()
     Hilt ile injection yapıldığından bu kodlara burada gerek kalmadı...
       */

        hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlockingTest{
        val exampleart = Art("Mona Lisa","Da Vinci",1503,"test.com",1)
        dao.insertArt(exampleart)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleart)
    }


    @Test
    fun deleteArtTesting() = runBlockingTest{
        val exampleart = Art("Mona Lisa","Da Vinci",1503,"test.com",1)
        dao.insertArt(exampleart)
        dao.deleteArt(exampleart)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleart)
    }

}