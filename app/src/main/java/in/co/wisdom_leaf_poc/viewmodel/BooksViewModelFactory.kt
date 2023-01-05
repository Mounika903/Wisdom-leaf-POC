package `in`.co.wisdom_leaf_poc.viewmodel

import `in`.co.wisdom_leaf_poc.remote.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BooksViewModelFactory  constructor(private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BooksViewModel::class.java)){
            BooksViewModel(this.repository)as T
        }else{
            throw IllegalArgumentException("View model not found")
        }
    }
}