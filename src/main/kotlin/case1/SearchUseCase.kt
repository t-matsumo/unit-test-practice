package case1

data class Data(val content: String)

// Case1：UseCase内で処理が完結している場合
class SearchUseCase {
    fun search(): Data {
        return Data("expected content")
    }
}