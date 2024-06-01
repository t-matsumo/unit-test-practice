package case3

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchUseCaseTestWithMock {
    private lateinit var testTarget: SearchUseCase
    private lateinit var dataStoreMock: DataStoreMock

    @BeforeTest
    fun setUp() {
        dataStoreMock = DataStoreMock()
        // テスト用データを準備する。
        dataStoreMock.data = List(3) { Data(Data.Tag("tag_2"), "expected content_${it}") }

        testTarget = SearchUseCase(dataStoreMock)
    }

    @Test
    fun testSearch() {
        // テスト対象のメソッドを呼び出す
        val query = DataStore.Query(Data.Tag("tag_2"))
        val actualData = testTarget.search(query)

        // 結果を確認する。
        // DataStoreMockにどんなQueryを渡しても同じ値が返ってくるので、「queryがMockに渡されること」の確認をしたくなる。
        assertEquals(query, dataStoreMock.searchQuery)
        val expectedData = List(3) { Data(Data.Tag("tag_2"), "expected content_${it}") }
        assertEquals(expectedData, actualData)
    }
}

private class DataStoreMock: DataStore {
    lateinit var data: List<Data>
    lateinit var searchQuery: DataStore.Query

    override fun searchBy(query: DataStore.Query): List<Data> {
        searchQuery = query
        return data
    }

    override fun save(data: Data) {
        error("今回のテストでは使わないので実装しない")
    }
}