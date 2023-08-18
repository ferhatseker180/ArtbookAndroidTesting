package com.ferhatt.artbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatt.artbook.model.ImageResponse
import com.ferhatt.artbook.repo.ArtRepoInterface
import com.ferhatt.artbook.roomdb.Art
import com.ferhatt.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository : ArtRepoInterface
) : ViewModel() {

    // Art Fragment İçin Kullanılacak Model
    val artList = repository.getArt()


    // Image Api Fragment İçin Kullanılacak Model
    private val images = MutableLiveData<Resource<ImageResponse>>()
    // images'a dışarıdan set yapılamaz ama aşağıdaki değişkenle get yapılabilir.
    val imageList : LiveData<Resource<ImageResponse>>
    get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedUrl : LiveData<String>
    get() = selectedImage


    // Art Detail Fragment İçin Kullanılacak Model
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
    get() = insertArtMsg

    fun resetInsertMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name : String,artistName : String,year : String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name, artist name, year",null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e : Exception){
            insertArtMsg.postValue(Resource.error("Year Should Be Number",null))
            return
        }
        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString : String){
        if (searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }


    }


}