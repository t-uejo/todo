# タスク管理アプリケーション

## 技術スタック

- Java: `21`
- Spring Boot: `3.4.0`
- Maven: `3.9.9`
- Postgres: `16`
- Thymeleaf
- Logback
- JUnit / Mockito
- Docker

## DB環境構築
以下のコマンドを実行する。
```
docker compose up -d
```

上記を実行すると以下のPostgres環境を構築できる。

- 開発環境
    - コンテナ名：postgres-develop
    - ポート番号：5433
- 検証環境用
    - コンテナ名：postgres-staging
    - ポート番号：5434
- 本番環境用
    - コンテナ名：postgres-production
    - ポート番号：5435
 
## warファイル生成
環境ごとにビルドし、warファイルを生成することができる。

### 検証環境
```
mvn package -Pstaging spring-boot:repackage
```

### 検証環境
```
mvn package -Pproduction spring-boot:repackage
```
