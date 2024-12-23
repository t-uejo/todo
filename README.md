# タスク管理アプリケーション
本アプリケーションは簡易的なCRUD操作ができるWebアプリケーションであり、主にSpring Bootを利用したバックエンド開発の基本的な実装を学ぶことを目的とした。

さらに、Spring Bootを使用したアプリケーションのCI/CD環境構築に関する練習も含めており、開発からデプロイまでの一連のワークフローを実践した。

## 技術スタック
<img alt="my skills" src="https://skillicons.dev/icons?theme=light&perline=8&i=java,spring,idea,maven,jenkins,docker,postgres" />

- Java: `21`
- Spring Boot: `3.4.0`
- Maven: `3.9.9`
- Postgres: `16`
- Thymeleaf
- Logback
- JUnit / Mockito
- Docker
- Tomat
- Jenkins
- Nexus
- SonarQube

## Dockerコンテナ環境
```
docker
  ├── develop-env
  │   └── docker-compose.yaml
  ├── staging-env
  │   └── docker-compose.yaml
  └── production-env
  │   └── docker-compose.yaml
  └── ci-env
      └── docker-compose.yaml
```

### 詳細
- develop-env：開発環境（PostgresSQL）
- staging-env：検証環境（Tomcat, PostgresSQL）
- production-env：本番環境（Tomcat, PostgresSQL）
- ci-env：CI環境（Jenkins, SonarQube, Nexus）

### 構築手順
それぞれのフォルダへ移動し、docker composeコマンドを実行する。
```
docker compose up -d
```

### ポート一覧

#### PostgresSQL
|コンテナ名|ホストポート:コンテナポート|環境| 
| ---------- | -------- | ---- | 
|postgres-develop|`5433`:`5432`|開発| 
|postgres-staging|`5434`:`5432`|検証| 
|postgres-production|`5435`:`5432`|本番| 

#### Web
|コンテナ名|ホストポート:コンテナポート|環境| 
| ---------- | -------- | ---- | 
|tomcat-staging|`8091`:`8080`|検証| 
|tomcat-production|`8092`:`8080`|本番| 
|jenkins|`8181`:`8080`|CI| 
|nexus|`8191`:`8081`|CI| 
 
## DB環境構築
以下のコマンドを実行し、テーブルを作成する。

```
mvn flyway:migrate
```
 
## 環境別ビルド方法
プロファイルを指定することで環境ごとにビルドし、warファイルを生成することができる。

### 検証環境
```
mvn clean package -Pstaging
```

### 本番環境
```
mvn clean package -Pproduction
```

## デプロイ方法
warファイルのデプロイ先は、Tomcatコンテナのwebappsであるが、以下のフォルダにマウントしているため配置することでデプロイ可能。

```
docker
  ├── staging-env
  │   └── production_webapps
  └── production-env
      └── staging_webapps
```

- 検証環境確認URL：http://localhost:8091/todo/
- 本番環境確認URL：http://localhost:8092/todo/


