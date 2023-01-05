package `in`.co.wisdom_leaf_poc.adapter

import `in`.co.wisdom_leaf_poc.databinding.BooksListRowBinding
import `in`.co.wisdom_leaf_poc.model.Author
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    var booklist = mutableListOf<Author>()

    class BookViewHolder(val binding: BooksListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BooksListRowBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = booklist[position]
        holder.binding.title.text = book.author
        holder.binding.description.text = book.download_url
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500" + booklist[position])
            .into(holder.binding.imageview)
    }

    override fun getItemCount(): Int {
        return booklist.size
    }

    fun setBooks(book: List<Author>) {
        this.booklist = book.toMutableList()
        notifyDataSetChanged()
    }
}