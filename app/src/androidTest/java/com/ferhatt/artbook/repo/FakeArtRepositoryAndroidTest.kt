package com.ferhatt.artbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferhatt.artbook.model.ImageResponse
import com.ferhatt.artbook.roomdb.Art
import com.ferhatt.artbook.util.Resource

class FakeArtRepositoryAndroidTest : ArtRepoInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {

        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {

        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {

        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {

        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}