package `in`.co.wisdom_leaf_poc.adapter

import `in`.co.wisdom_leaf_poc.databinding.BooksListRowBinding
import `in`.co.wisdom_leaf_poc.model.Author
import android.R
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.internal.ContextUtils.getActivity


class BooksAdapter: RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    private var bookList = mutableListOf<Author>()

    class BookViewHolder(val binding: BooksListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BooksListRowBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.binding.title.text = book.author
        holder.binding.description.text = book.download_url

        val url: String =  book.download_url
        Log.d(TAG, "onBindViewHolder: URL: $url")

        // Load images using Glide library
        Glide.with(holder.itemView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Store images in cache to load faster
            .into(holder.binding.imageview)

        // Display the description dialog
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)

            builder.setTitle( holder.binding.title.text)
            builder.setMessage( holder.binding.description.text)
            builder.setCancelable(true)
            val alert11: AlertDialog = builder.create()
            alert11?.show()
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setBooks(book: List<Author>) {
        this.bookList = book.toMutableList()
        notifyDataSetChanged() // List refresh with the latest results
    }
}