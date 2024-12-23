# タスク管理アプリケーション
本アプリケーションは簡易的なCRUD操作ができるWebアプリケーションであり、主にSpring Bootを利用したバックエンド開発の基本的な実装を学ぶことを目的とした。

さらに、Spring Bootを使用したアプリケーションのCI/CD環境構築に関する練習も含めており、開発からデプロイまでの一連のワークフローを実践した。

本ドキュメントは環境構築からデプロイまでの簡単な手順を示しているが、Jenkinsの設定などCI環境の構築手順については触れていない。<br>

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

## 割り当てポート一覧

### PostgresSQL
|コンテナ名|ホストポート:コンテナポート|環境| 
| ---------- | -------- | ---- | 
|postgres-develop|`5433`:`5432`|開発| 
|postgres-staging|`5434`:`5432`|検証| 
|postgres-production|`5435`:`5432`|本番| 

### Web
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

## デプロイ手順

> [!NOTE]
> デプロイ手順は[3.5. 開発プロジェクトのビルド - TERASOLUNA Server Framework for Java (5.x) Development Guideline](https://terasolunaorg.github.io/guideline/current/ja/ImplementationAtEachLayer/CreateProject.html#createwebapplicationprojectbuilddeploycontinueddeployment)を参考としている。検証環境と本番環境でフローは異なるが、大きく分けて以下のフローをたどる。
> 1. パッケージリポジトリ（Nexus）へのアップロード
> 2. パッケージリポジトリから対象のアーティファクト（warファイル）を取得し、APサーバ（Tomcat）にデプロイ

### 1. パッケージリポジトリ（Nexus）へのアップロード

環境ごとのアップロード先は以下となる。
- 検証環境：snapshotリポジトリ
- 本番環境：releaseリポジトリ

#### 検証環境の場合
①warファイルをNexusのsnapshotリポジトリへアップロード
```
mvn clean deploy -Pstaging
```

#### 本番環境の場合
> [!Tip]
> TERESOLUNAのドキュメントに記載されているが、releaseリポジトリの場合、同じバージョンのアーティファクトは1度しか上げれない。そのため、[maven-release-plugin](https://maven.apache.org/maven-release/maven-release-plugin/index.html)を使用してバージョニングとタグ付けなどを行っている。

①maven-release-pluginを使用し、pom.xmlの`<version>`タグの更新とtag付けを行う
```
mvn release:prepare
```

②maven-release-pluginを使用し、Nexusのreleaseリポジトリへアップロード
```
mvn release:perform
```

> [!IMPORTANT]
> - deployゴールでNexusへアップロードするには、ローカルリポジトリである`.m2`フォルダ内にsettings.xmlを作成し設定を行う必要がある。
> - `mvn release:perform`を使用すると`deploy`と`site-deploy`がゴールとして実行される。production用のプロファイルを指定してビルドし、warファイルを生成する必要がある。
> - 設定方法はmaven-release-pluginの[releaseProfiles](https://maven.apache.org/maven-release/maven-release-plugin/usage/perform-release.html#perform-a-release)を参照すること。


### 2. APサーバ（Tomcat）にデプロイ
warファイルのデプロイ先はTomcatコンテナのwebappsであるが、以下のフォルダにマウントしているため配置することでデプロイ可能。

```
docker
  ├── staging-env
  │   └── production_webapps
  └── production-env
      └── staging_webapps
```

- 検証環境確認URL：http://localhost:8091/todo/
- 本番環境確認URL：http://localhost:8092/todo/

> [!Tip]
> warファイルをNexusから自動で取得する場合はREST APIを使用するとよい。Nexusの[公式ドキュメント](https://help.sonatype.com/en/search-api.html#downloading-the-latest-version-of-an-asset)にsnapshot版リポジトリの最新版を取得するcurlコマンドが紹介されている。本アプリケーションで使用する場合の例は以下。
> ```
> curl -L -X GET "http://localhost:8191/service/rest/v1/search/assets/download?sort=version&repository=maven-snapshots&maven.groupId=com.example&maven.artifactId=todo&maven.extension=war" -H "accept: application/json" --output "todo.war"
> ```
