# ユニットテストにパスさせたままコードを壊す方法の例
※仕様変更も「壊す」と表現しています。

他のcaseに記載したものと同じ壊し方は省略しています。

以下は`assertEquals(query, dataStoreMock.searchQuery`が無かった場合。

## SearchUseCaseからqueryをDataStoreへ渡さない
```kotlin
class SearchUseCase(
    private val dataStore: DataStore
) {
    fun search(query: DataStore.Query): List<Data> {
        return dataStore.searchBy(DataStore.Query(Data.Tag("other tag")))
    }
}
```
#### 壊れたことを検知する方法の例
- 不要な処理なら削除する。※不要ならユニットテストは書かれないはずという前提
- UseCaseがほかの依存先へ渡す処理をテストする。
