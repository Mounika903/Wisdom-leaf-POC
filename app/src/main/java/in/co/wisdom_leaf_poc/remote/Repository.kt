package `in`.co.wisdom_leaf_poc.remote

class Repository constructor(private val retrofitSrevice: RetrofitService) {

    suspend fun getBooks() =  retrofitSrevice.getBooks()
}
