package case2

data class Data(val content: String)

interface DataStore {
    fun search(): Data
    fun save(data: Data)
}

// Case2：UseCaseが依存先クラスの処理結果を使う場合
class SearchUseCase(
    private val dataStore: DataStore
) {
    fun search(): Data {
        return dataStore.search()
    }
}