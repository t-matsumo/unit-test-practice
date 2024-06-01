package case3

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchUseCaseTestWithFake {
    private lateinit var testTarget: SearchUseCase
    private lateinit var dataStoreFake: DataStore

    @BeforeTest
    fun setUp() {
        dataStoreFake = DataStoreFake()
        // テスト用データを準備する。
        repeat(5){ tagId ->
            repeat(3) { contentId ->
                dataStoreFake.save(Data(
                    Data.Tag("tag_${tagId}"),
                    "expected content_${contentId}"
                ))
            }
        }

        testTarget = SearchUseCase(dataStoreFake)
    }

    @Test
    fun testSearch() {
        // テスト対象のメソッドを呼び出す
        val query = DataStore.Query(Data.Tag("tag_2"))
        val actualData = testTarget.search(query)

        // 結果を確認する。
        // 「queryがDataStoreへ渡されたこと」と「DataStoreから受け取ったデータを使っていること」の確認は不要になる。
        val expectedData = List(3) { Data(Data.Tag("tag_2"), "expected content_${it}") }
        assertEquals(expectedData, actualData)
    }
}

// DataStoreを実装しているので、内部にデータを保持しなければ多くのテストで使いまわせる。
private class DataStoreFake: DataStore {
    // 検索をシミュレートできるように、mapなどで保持する
    private val dataSource: MutableMap<DataStore.Query, List<Data>> = hashMapOf()

    // queryが一つではなく複数の場合でも、工夫すればHashMapのようなCollectionに保持できるはず。
    override fun searchBy(query: DataStore.Query): List<Data> {
        // DataStoreインターフェースが、ソートやバリデーションのような追加処理を要求する場合は、忘れずに実装する
        return dataSource[query]?.sortedBy { it.content } ?: emptyList()
    }

    override fun save(data: Data) {
        dataSource.merge(DataStore.Query(data.tag), listOf(data), List<Data>::plus)
    }
}