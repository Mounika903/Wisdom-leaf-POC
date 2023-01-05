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
    lateinit var viewmodel: BooksViewModel
    private val adapter = BooksAdapter()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen() // To launch the Splash screen using the Splashscreen API.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitService.getInstance()
        val repository = Repository(retrofitService)

        viewmodel = ViewModelProvider(
            this,
            BooksViewModelFactory(repository)
        ).get(BooksViewModel::class.java)
        binding.recyclerview.adapter = adapter

        viewmodel.bookList.observe(this)
        {
            adapter.setBooks(it)
        }

        viewmodel.errorMessage.observe(this) {
            Toast.makeText(
                this,
                "Error loading messages. Please reload this page.",
                Toast.LENGTH_LONG
            ).show()
        }

        viewmodel.loading.observe(this) {
            if (it)
                binding.progressDialog.visibility = View.VISIBLE
            else
                binding.progressDialog.visibility = View.GONE
        }
        viewmodel.getBooks()
    }
}
