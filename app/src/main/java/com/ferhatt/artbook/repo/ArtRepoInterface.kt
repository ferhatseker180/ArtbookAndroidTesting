package com.ferhatt.artbook.repo

import androidx.lifecycle.LiveData
import com.ferhatt.artbook.model.ImageResponse
import com.ferhatt.artbook.roomdb.Art
import com.ferhatt.artbook.util.Resource

interface ArtRepoInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}