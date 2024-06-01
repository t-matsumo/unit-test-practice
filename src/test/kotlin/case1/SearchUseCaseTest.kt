package case1

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchUseCaseTest {
    private lateinit var testTarget: SearchUseCase

    @BeforeTest
    fun setUp() {
        testTarget = SearchUseCase()
    }

    @Test
    fun testSearch() {
        // テスト対象のメソッドを呼び出す
        val actualData = testTarget.search()

        // 結果を確認する
        val expectedData = Data("expected content")
        assertEquals(expectedData, actualData)
    }
}