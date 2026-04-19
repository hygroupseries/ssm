<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>图书管理 - 列表</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="margin-top: 20px;">
    <h2>图书管理</h2>

    <!-- 搜索 + 新增按钮 -->
    <div class="row" style="margin-bottom: 15px;">
        <div class="col-md-6">
            <form method="get" action="${pageContext.request.contextPath}/bookinfo/list" class="form-inline">
                <div class="input-group">
                    <input type="text" name="name" class="form-control"
                           placeholder="按书名模糊查询" value="${name}">
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-primary">查询</button>
                    </span>
                </div>
            </form>
        </div>
        <div class="col-md-6 text-right">
            <a href="${pageContext.request.contextPath}/bookinfo/add" class="btn btn-success">
                <span class="glyphicon glyphicon-plus"></span> 新增图书
            </a>
        </div>
    </div>

    <!-- 图书列表 -->
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>书名</th>
            <th>作者</th>
            <th>价格</th>
            <th>出版日期</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty page.list}">
                <tr><td colspan="6" class="text-center">暂无数据</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="book" items="${page.list}">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.name}</td>
                        <td>${book.author}</td>
                        <td>
                            <c:if test="${book.price != null}">
                                ¥<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${book.publishDate != null}">
                                <fmt:formatDate value="${book.publishDate}" pattern="yyyy-MM-dd"/>
                            </c:if>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/bookinfo/edit/${book.id}"
                               class="btn btn-xs btn-warning">编辑</a>
                            <%-- 删除使用 POST 表单，防止 CSRF 与意外触发 --%>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/bookinfo/delete"
                                  style="display:inline;"
                                  onsubmit="return confirm('确认删除《${book.name}》？')">
                                <input type="hidden" name="id" value="${book.id}">
                                <button type="submit" class="btn btn-xs btn-danger">删除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <!-- 分页导航（最多显示 7 个页码） -->
    <c:if test="${page.totalPages > 1}">
        <c:set var="winStart" value="${page.pageNo - 3 > 1 ? page.pageNo - 3 : 1}"/>
        <c:set var="winEnd"   value="${page.pageNo + 3 < page.totalPages ? page.pageNo + 3 : page.totalPages}"/>
        <nav>
            <ul class="pagination">
                <li class="${page.hasPrev ? '' : 'disabled'}">
                    <a href="${pageContext.request.contextPath}/bookinfo/list?name=${name}&pageNo=${page.pageNo - 1}">
                        &laquo;
                    </a>
                </li>
                <c:if test="${winStart > 1}">
                    <li><a href="${pageContext.request.contextPath}/bookinfo/list?name=${name}&pageNo=1">1</a></li>
                    <c:if test="${winStart > 2}"><li class="disabled"><a>…</a></li></c:if>
                </c:if>
                <c:forEach begin="${winStart}" end="${winEnd}" var="i">
                    <li class="${i == page.pageNo ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/bookinfo/list?name=${name}&pageNo=${i}">${i}</a>
                    </li>
                </c:forEach>
                <c:if test="${winEnd < page.totalPages}">
                    <c:if test="${winEnd < page.totalPages - 1}"><li class="disabled"><a>…</a></li></c:if>
                    <li><a href="${pageContext.request.contextPath}/bookinfo/list?name=${name}&pageNo=${page.totalPages}">${page.totalPages}</a></li>
                </c:if>
                <li class="${page.hasNext ? '' : 'disabled'}">
                    <a href="${pageContext.request.contextPath}/bookinfo/list?name=${name}&pageNo=${page.pageNo + 1}">
                        &raquo;
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>

    <p class="text-muted">
        共 ${page.total} 条记录，第 ${page.pageNo} / ${page.totalPages} 页（每页 ${page.pageSize} 条）
    </p>
</div>
</body>
</html>
