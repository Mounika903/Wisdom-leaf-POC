package `in`.co.wisdom_leaf_poc

import `in`.co.wisdom_leaf_poc.adapter.BooksAdapter
import `in`.co.wisdom_leaf_poc.databinding.ActivityMainBinding
import `in`.co.wisdom_leaf_poc.remote.Repository
import `in`.co.wisdom_leaf_poc.remote.RetrofitService
import `in`.co.wisdom_leaf_poc.viewmodel.BooksViewModel
import `in`.co.wisdom_leaf_poc.viewmodel.BooksViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private var pageNumber = 1
    private lateinit var viewModel: BooksViewModel
    private val adapter = BooksAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen() // To launch the Splash screen using the Splashscreen API.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitService.getInstance()
        val repository = Repository(retrofitService)

        viewModel = ViewModelProvider(
            this,
            BooksViewModelFactory(repository)
        ).get(BooksViewModel::class.java)
        binding.recyclerview.adapter = adapter

        viewModel.bookList.observe(this)
        {
            adapter.setBooks(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(
                this,
                "Error loading messages. Please reload this page.",
                Toast.LENGTH_LONG
            ).show()
        }

        viewModel.loading.observe(this) {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
                binding.swipe.isRefreshing = false
            }
        }
        viewModel.getBooks(pageNumber)

        /**
         * Swipe refresh listener to load next page results from the API call
         */
        binding.swipe.setOnRefreshListener {
            pageNumber++
            viewModel.getBooks(pageNumber)
        }
    }

}
