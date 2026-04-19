<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>图书管理 - <c:choose><c:when test="${action == 'add'}">新增</c:when><c:otherwise>编辑</c:otherwise></c:choose></title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="margin-top: 20px; max-width: 600px;">
    <h2>
        <c:choose>
            <c:when test="${action == 'add'}">新增图书</c:when>
            <c:otherwise>编辑图书</c:otherwise>
        </c:choose>
    </h2>

    <c:choose>
        <c:when test="${action == 'add'}">
            <form method="post" action="${pageContext.request.contextPath}/bookinfo/add">
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/bookinfo/edit">
                <input type="hidden" name="id" value="${book.id}">
        </c:otherwise>
    </c:choose>

        <div class="form-group">
            <label for="name">书名 <span class="text-danger">*</span></label>
            <input type="text" id="name" name="name" class="form-control"
                   placeholder="请输入书名" value="${book.name}" required>
        </div>
        <div class="form-group">
            <label for="author">作者</label>
            <input type="text" id="author" name="author" class="form-control"
                   placeholder="请输入作者" value="${book.author}">
        </div>
        <div class="form-group">
            <label for="price">价格</label>
            <input type="number" id="price" name="price" class="form-control"
                   placeholder="请输入价格（如 59.90）" step="0.01" min="0"
                   value="${book.price}">
        </div>
        <div class="form-group">
            <label for="publishDate">出版日期</label>
            <input type="date" id="publishDate" name="publishDate" class="form-control"
                   value="<c:if test="${book.publishDate != null}"><fmt:formatDate value="${book.publishDate}" pattern="yyyy-MM-dd"/></c:if>">
        </div>
        <button type="submit" class="btn btn-primary">
            <c:choose>
                <c:when test="${action == 'add'}">新增</c:when>
                <c:otherwise>保存修改</c:otherwise>
            </c:choose>
        </button>
        <a href="${pageContext.request.contextPath}/bookinfo/list" class="btn btn-default">返回列表</a>
    </form>
</div>
</body>
</html>
