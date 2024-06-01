package case2

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchUseCaseTestWithMock {
    private lateinit var testTarget: SearchUseCase
    private lateinit var dataStoreMock: DataStoreMock

    @BeforeTest
    fun setUp() {
        dataStoreMock = DataStoreMock()
        testTarget = SearchUseCase(dataStoreMock)
    }

    @Test
    fun testSearch() {
        // テスト用データを準備する。Mockに用意した変数へ直接代入する。
        dataStoreMock.data = Data("expected content")

        // テスト対象のメソッドを呼び出す
        val actualData = testTarget.search()

        // 結果を確認する。
        // 「DataStoreのsearch()メソッドを呼び出したか？」というフラグを用意して、内部実装に踏み込んだ確認をしている。
        // このようなフラグを用意しての確認は避けたいが、「search()メソッドを呼ばないこと」をどうしてもテストしないといけない場合は必要。
        assertTrue { dataStoreMock.isSearchMethodCalled }
        val expectedData = Data("expected content")
        assertEquals(expectedData, actualData)
    }
}

private class DataStoreMock: DataStore {
    lateinit var data: Data

    // 「search()メソッドを呼び出したか？」という変数を持つ
    var isSearchMethodCalled: Boolean = false

    override fun search(): Data {
        isSearchMethodCalled = true
        return data
    }

    override fun save(data: Data) {
        error("今回のテストでは使わないので未実装")
    }
}