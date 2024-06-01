# 概要
ユニットテストについて考えるためのリポジトリです。
case1, case2...といったパッケージごとにテスト対象の処理を変えてます。

## ユニットテスト対象の概要
- Case1：UseCase内で処理が完結している場合
- Case2：UseCaseが依存先クラスの処理結果を使う場合
- Case3：UseCaseが依存先クラスへデータを渡す場合