package `in`.co.wisdom_leaf_poc.viewmodel

import `in`.co.wisdom_leaf_poc.model.Author
import `in`.co.wisdom_leaf_poc.remote.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class BooksViewModel constructor(private val repository: Repository) : ViewModel() {

    val bookList = MutableLiveData<List<Author>>()
    var handlejob: Job? = null
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(
            "Exception :${throwable.localizedMessage}"
        )
    }

    private fun onError(error: String) {
        errorMessage.value =  "$error - Error occurred. Try again."
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        handlejob?.cancel()
    }

    /**
     * To fetch the books from the AP call using Coroutines
     */
    fun getBooks() {
        handlejob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getBooks()
            withContext(Dispatchers.Main)
            {
                if (response.isSuccessful) {
                    bookList.postValue(response.body())
                    loading.value = false
                }else{
                    onError("Error: ${response.message()}")
                }
            }
        }
    }
}