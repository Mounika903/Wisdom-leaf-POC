package `in`.co.wisdom_leaf_poc.remote

class Repository constructor(private val retrofitService: RetrofitService) {

    suspend fun getBooks(pageNumber: Int) =  retrofitService.getBooks(pageNumber)
}
