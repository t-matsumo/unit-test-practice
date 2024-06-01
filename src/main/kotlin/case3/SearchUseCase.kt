package case3

data class Data(
    val tag: Tag,
    val content: String
) {
    @JvmInline
    value class Tag(val value: String)
}

interface DataStore {
    // case2までが単純すぎたので、Queryを満たすDataをすべて取得するインターフェースへ変更
    fun searchBy(query: Query): List<Data>
    fun save(data: Data)

    data class Query(
        val tag: Data.Tag
    )
}

// Case3：UseCaseが依存先クラスへデータを渡す場合
class SearchUseCase(
    private val dataStore: DataStore
) {
    fun search(query: DataStore.Query): List<Data> {
        return dataStore.searchBy(query)
    }
}