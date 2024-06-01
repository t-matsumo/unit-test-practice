package case2

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchUseCaseTestWithFake {
    private lateinit var testTarget: SearchUseCase
    private lateinit var dataStoreFake: DataStore // DataStoreFakeとして宣言する必要がない

    @BeforeTest
    fun setUp() {
        dataStoreFake = DataStoreFake()
        // テスト用データを準備する。
        // DataStoreFakeは疑似的に実装されているので、DataStoreインターフェースに用意されているメソッドを使う。
        dataStoreFake.save(Data("expected content"))

        testTarget = SearchUseCase(dataStoreFake)
    }

    @Test
    fun testSearch() {
        // テスト対象のメソッドを呼び出す
        val actualData = testTarget.search()

        // 結果を確認する。確認内容はcase1と同じ。
        // 「DataStoreのsearch()メソッドを呼び出したか？」を確認するような、
        // 実装の詳細に踏み込んだテストは不要。
        val expectedData = Data("expected content")
        assertEquals(expectedData, actualData)
    }
}

// DataStoreを疑似的に実装する。
// DataStoreインターフェースが規定するルールは必ず実装する（実装しないと意味のないテストになる可能性がある。）。
// 本番環境で使う実装との乖離を防ぐため、Fakeに対しても本番環境で使う実装と同じユニットテストを書くべき。
// 本番環境で使用するDataStoreの実装に以下のような不都合がなければ、本番環境で使用する実装を使った方が良い。
// # 不都合の例
//   - 処理の実行に、本番環境へ影響するような副作用がある
//   - 本番環境で使用する実装が未完成
//   - HTTPリクエストをするといった理由で実行が遅い
//   - インスタンスの生成が難しい
//   - テスト用データの準備が難しい
private class DataStoreFake: DataStore {
    // 「search()メソッドを呼び出したか？」というフラグや変更可能なデータを持つのではなく、
    // search()メソッドが返却する値をクラス内に保持する。
    private lateinit var data: Data

    override fun search(): Data {
        return data
    }

    // この例ではDataStoreインターフェースにsave(data)メソッドがあるので、外部からテストデータを書き換えられる。
    // 無い場合はテスト用に値をセットするためのメソッドを用意するか、すべてのデータを事前に固定値で定義しておく。
    override fun save(data: Data) {
        this.data = data
    }
}