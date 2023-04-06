<%--
  @author  xcyb
  @date  2022/10/18 15:57
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Query</title>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.1.js"></script>
    <script type="text/javascript">

        $(function(){
            $(".Delete").click(function(){
                var href = $(this).attr("href");//获取属性
                $("#Delete_form").attr("action",href).submit();//设置属性
                return false;//阻止原请求
            });

            /**
             * change 内容改变事件
             * 发送超链接，
             * 第一次获取不到request里的值，会从浏览器获取
             * SessionLocalResolver 会把选定的保存到session中，
             */
            $("#lang").change(function (){
                window.location.href="GetQuery?locale="+$(this).val();
            });


            $("#pageSize").change(function (){
                window.location.href="GetQuery?pageNo=${page.pageNo -1}&pageSize="+$(this).val();
            });
        });
    </script>
</head>
<body>
<select id="lang">
    <option value="zh_CN" <c:if test="${requestScope.locale == 'zh_CN'}">selected</c:if> >简体中文</option>
    <option value="en_US" <c:if test="${requestScope.locale == 'en_US'}">selected</c:if> >English</option>
</select>





<form action="" id="Delete_form" method="post">
    <input type="hidden" name="_method" value="Delete"/>
</form>

先后顺序 page,request,session,application 11 22 33

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <td>ID</td>
        <td></td>
        <td><fmt:message key="index.name"/></td>
        <td><fmt:message key="index.password"/></td>
        <td><fmt:message key="index.sex"/></td>
        <td><fmt:message key="index.age"/></td>
        <td><fmt:message key="index.userCity"/></td>
        <td><fmt:message key="index.birth"/></td>
        <td><fmt:message key="index.salary"/></td>
        <td><fmt:message key="index.Email"/></td>
        <td><fmt:message key="index.Edit"/></td>
        <td><fmt:message key="index.Delete"/></td>
    </tr>


    <c:forEach items="${requestScope.page.list}" var="list">
        <tr>
            <td>${list.id}</td>
            <c:set value="${list.path}" var="path" scope="request"/>
            <td><c:if test="${list.path != null}"><img width="75" height="75" src="<%=request.getContextPath()%>/ResponseEntityHead?group=${list.group}&path=<%=java.net.URLEncoder.encode((String) request.getAttribute("path"))%>"/></c:if></td>
            <td>${list.name}</td>
            <td>${list.password}</td>
            <td>${list.sex == 0 ? '女':'男'}</td>
            <td>${list.age}</td>
            <%--<td>${list.userCity == 1 ? '武汉':'深圳'}</td>--%>
            <td>${list.userCity.city}</td>
            <td>${list.birth}</td>
            <td>${list.salary}</td>
            <td>${list.email}</td>
            <td><a href="Edit/${list.id}">Edit</a></td>
            <td><a class="Delete" href="Delete.do/${list.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<!-- 在表达式里面 requestScope 可以默认不写 -->
    <a href="<%=request.getContextPath()%>/GetQuery?pageNo=1&pageSize=${page.pageSize}">首页</a>
    <c:if test="${page.pageNo -1 != 0}">
        <a href="<%=request.getContextPath()%>/GetQuery?pageNo=${page.pageNo -1}&pageSize=${page.pageSize}">上一页</a>
    </c:if>
    <c:if test="${page.pageNo +1 <= page.pageCount}">
        <a href="<%=request.getContextPath()%>/GetQuery?pageNo=${page.pageNo +1}&pageSize=${page.pageSize}">下一页</a>
    </c:if>
    <a href="<%=request.getContextPath()%>/GetQuery?pageNo=${page.pageCount}&pageSize=${page.pageSize}">尾页</a>

<select id="pageSize">
    <option value="3" <c:if test="${page.pageSize == 3}">selected</c:if> >3</option>
    <option value="4" <c:if test="${page.pageSize == 4}">selected</c:if> >4</option>
    <option value="5" <c:if test="${page.pageSize == 5}">selected</c:if> >5</option>
</select>


<a href="GetAdd"><fmt:message key="index.Add"/></a>

</body>
</html>
