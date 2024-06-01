# ユニットテストにパスさせたままコードを壊す方法の例
※仕様変更も「壊す」と表現しています。

## 分岐処理を追加して返却する値を変える
```kotlin
val flag = true // 複雑な処理の結果としてのフラグやグローバル変数。ユニットテスト実行時はたまたまtrueだったとする。

class SearchUseCase {
    fun search(): Data {
        return if (flag) Data("expected content") else Data("expected content other")
    }
}
```
#### 壊れたことを検知する方法の例
- メソッドを変更したらユニットテストも追加する
- 不要な処理なら削除する。※不要ならユニットテストは書かれないはずという前提

---

## 新しい副作用を追加する
```kotlin
class SearchUseCase {
    fun search(): Data {
        println("てすとおおおおーーーー") // これを追加。本番環境のDBを変更する処理だったら大事故
        return Data("expected content")
    }
}
```
#### 壊れたことを検知する方法の例
- メソッドを変更したらユニットテストも追加する
- 不要な処理なら削除する。※不要ならユニットテストは書かれないはずという前提

---

## 返却されるデータのequalsを変更する
※以下の変更でも意図的な仕様変更の可能性があります。Mapのキーにするなどしてるとかなりの影響がありますけど。。。
```kotlin
data class Data(val content: String) {
    // すべてのインスタンスが等価扱いになる
    override fun equals(other: Any?): Boolean {
        return true
    }
}

// Case1：UseCase内で処理が完結している場合
class SearchUseCase {
    fun search(): Data {
        return Data("expected content222") // contentをどんな値にしても合格するようになる
    }
}
```
#### 壊れたことを検知する方法の例
- メソッドを変更したらユニットテストも追加する
- 不要な処理なら削除する。※不要ならユニットテストは書かれないはずという前提
- 影響調査を頑張る