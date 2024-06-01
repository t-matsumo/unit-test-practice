# ユニットテストにパスさせたままコードを壊す方法の例
※仕様変更も「壊す」と表現しています。

他のcaseに記載したものと同じ壊し方は省略しています。

## dataStore.search()の結果を使わずに期待値どおりの値を返す
```kotlin
class SearchUseCase(
    private val dataStore: DataStore
) {
    fun search(): Data {
        dataStore.search()
        return Data("expected content")
    }
}
```
#### 壊れたことを検知する方法の例
- 不要な処理なら削除する。※不要ならユニットテストは書かれないはずという前提
- 複数のテストパターンを用意する。

---

以下は、仮にDataStoreMockのisSearchMethodCalledがなかった場合（search()を呼び出したことの確認がなかった場合）

## dataStore.search()を呼ばずに、期待値通りの値を返す
```kotlin
class SearchUseCase(
    private val dataStore: DataStore
) {
    fun search(): Data {
        return Data("expected content")
    }
}

// または以下のようにする

class SearchUseCase(
    private val dataStore: DataStore
) {
    fun search(): Data {
        if (たまたまtrueになる) {
            return Data("expected content")
        }
        return dataStore.search()
    }
}
```
#### 壊れたことを検知する方法の例
- 不要な処理なら削除する。※不要ならユニットテストは書かれないはずという前提
- 複数のテストパターンを用意する。
- search()を呼び出したことの確認をする。