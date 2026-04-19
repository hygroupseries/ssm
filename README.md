# SSM 项目 — Java 17 + Oracle XE 11g 改造版

基于 Spring 5.3 + SpringMVC + MyBatis 3.5 的 SSM 框架项目，以**图书（Book）管理**为业务模块，演示：分页、增加、修改、删除、查询（全查 + 书名模糊查询）。

---

## 技术栈

| 层次 | 技术 |
|------|------|
| JDK  | **Java 17** |
| 应用服务器 | **Tomcat 9.x**（javax.servlet 体系） |
| 框架 | Spring 5.3 + SpringMVC + MyBatis 3.5（SSM） |
| 数据库 | **Oracle XE 11g** |
| 连接池 | c3p0 0.9.5.5 |
| 构建工具 | Maven 3.6+ |

---

## 运行环境要求

- **JDK 17**（不低于 Java 17）
- **Tomcat 9.x**（推荐 9.0.x；勿用 Tomcat 10，因其使用 jakarta.* 命名空间）
- **Oracle XE 11g** 已安装并启动（默认监听 1521 端口，SID = `XE`）
- Maven 3.6+

---

## Oracle XE 11g 初始化步骤

### 1. 创建专用用户（可选，推荐）

以 `SYSTEM` 用户连接（SQL*Plus 或 SQL Developer），执行：

```sql
CREATE USER ssm_user IDENTIFIED BY ssm_password;
GRANT CONNECT, RESOURCE, CREATE TABLE, CREATE SEQUENCE TO ssm_user;
```

### 2. 执行建表脚本

以 `ssm_user`（或 `SYSTEM`）登录后，执行：

```
src/main/sql/oracle/book.sql
```

该脚本将创建：
- `book` 表（字段：id / name / author / price / publish_date）
- `seq_book_id` 序列（主键自增）
- 4 条初始测试数据

### 3. 配置 jdbc.properties

编辑 `src/main/resources/jdbc.properties`，填写实际用户名和密码：

```properties
jdbc.driver=oracle.jdbc.OracleDriver
jdbc.url=jdbc:oracle:thin:@localhost:1521:XE
jdbc.username=ssm_user
jdbc.password=ssm_password
```

> 若 Oracle XE 11g 监听端口或 SID 与默认值不同，请同步修改 `jdbc.url`。

---

## Oracle JDBC 驱动说明

本项目使用 Maven 坐标引入 Oracle JDBC 驱动：

```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <version>21.7.0.0</version>
    <scope>runtime</scope>
</dependency>
```

`ojdbc11` 版本号中的 "11" 指支持 **Java 11+**（包含 Java 17），可正常连接 Oracle XE **11g** 数据库。  
该驱动已发布到 Maven Central，无需手动安装。  
若本地网络无法拉取，可从 [Oracle 官网](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html) 下载后手动安装：

```bash
mvn install:install-file -Dfile=ojdbc11.jar \
    -DgroupId=com.oracle.database.jdbc \
    -DartifactId=ojdbc11 \
    -Dversion=21.7.0.0 \
    -Dpackaging=jar
```

---

## 编译与部署

```bash
# 编译打包
mvn clean package -DskipTests

# 将 target/ssm.war 部署到 Tomcat 9 的 webapps 目录，启动 Tomcat 即可
```

---

## 图书模块功能与访问路径

| 功能 | 方法 | 路径 |
|------|------|------|
| 图书列表（分页） | GET | `/bookinfo/list` |
| 图书列表（书名模糊查询 + 分页） | GET | `/bookinfo/list?name=关键词&pageNo=1` |
| 跳转新增表单 | GET | `/bookinfo/add` |
| 提交新增 | POST | `/bookinfo/add` |
| 跳转编辑表单 | GET | `/bookinfo/edit/{id}` |
| 提交修改 | POST | `/bookinfo/edit` |
| 删除图书 | POST | `/bookinfo/delete` (表单参数 `id`) |

### 入口示例

部署到 Tomcat 9 后，访问：

```
http://localhost:8080/ssm/bookinfo/list
```

---

## 分页 SQL 说明（Oracle XE 11g）

Oracle 11g 不支持 `OFFSET…FETCH` 语法，本项目使用 **ROWNUM 二层分页**：

```sql
SELECT id, name, author, price, publish_date
FROM (
    SELECT t.*, ROWNUM rn
    FROM (
        SELECT id, name, author, price, publish_date
        FROM book
        WHERE name LIKE '%' || #{name} || '%'   -- 可选模糊过滤
        ORDER BY id DESC
    ) t
    WHERE ROWNUM <= #{endRow}
)
WHERE rn >= #{startRow}
```

- `startRow = (pageNo - 1) * pageSize + 1`
- `endRow   = pageNo * pageSize`

---

## 项目结构（新增部分）

```
src/
├── main/
│   ├── java/com/soecode/lyf/
│   │   ├── entity/BookInfo.java          # 图书实体（5 字段）
│   │   ├── dao/BookInfoMapper.java        # DAO 接口
│   │   ├── service/BookInfoService.java   # Service 接口
│   │   ├── service/impl/BookInfoServiceImpl.java
│   │   ├── web/BookInfoController.java    # Controller
│   │   └── dto/PageResult.java           # 分页结果 DTO
│   ├── resources/
│   │   ├── jdbc.properties               # Oracle XE 11g 连接配置
│   │   └── mapper/BookInfoMapper.xml     # MyBatis SQL（Oracle 11g）
│   ├── sql/oracle/book.sql               # Oracle 建表脚本 + 初始数据
│   └── webapp/WEB-INF/jsp/book/
│       ├── list.jsp                      # 列表页（分页 + 模糊查询）
│       └── form.jsp                      # 新增 / 编辑表单
```

