<%--
  @author  xcyb
  @date  2022/10/18 15:57
  isErrorPage="true" 这句可以让put请求进入
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isErrorPage="true" %>
<html>
<head>
    <script type="text/javascript" src="<%=request.getContextPath()%>/src/main/webapp/js/jquery-3.2.1.js"></script>
    <script type="text/javascript">

    </script>
    <title>Add</title>
</head>
<body>

<!-- modelAtribute 是request 里面传输过来的属性 默认叫command  multipart/form-data 加上这个才能上传二进制文件-->
<form:form action="${pageContext.request.contextPath}/SaveAdd.do" modelAttribute="user" method="post" enctype="multipart/form-data">
    <c:if test="${user.path != null}">
        <c:set value="${user.path}" var="path"   scope="request"/><%--把值存入request中--%>
        <img width="75" height="75" src="<%=request.getContextPath()%>/ResponseEntityHead?group=${user.group}&path=<%=java.net.URLEncoder.encode((String) request.getAttribute("path"))%>"/><br/>
    </c:if>
    <fmt:message key="index.head"/>:<input type="file" name="testfile"/><br/>

    <!-- 如果有ID 就表示修改，修改是put请求  而且名字是不能修改的-->
    <c:if test="${user.id == 0}">
        <fmt:message key="index.name"/>:<form:input path="name"/><font color="red"><form:errors path="name"></form:errors></font><br/>
    </c:if>
    <c:if test="${user.id != 0}">
        <form:hidden path="id"/>
        <input type="hidden" name="_method" value="PUT"/>
    </c:if>

    <fmt:message key="index.password"/>:<form:input path="password"/><font color="red"><form:errors path="password"></form:errors></font><br/>
    <fmt:message key="index.sex"/>:<form:radiobuttons path="sex" items="${map1}"/><font color="red"><form:errors path="sex"></form:errors></font><br/><!-- 单选框  Map 会自动是把key 当成 Value 值当成Label-->
    <fmt:message key="index.age"/>:<form:input path="age"/><font color="red"><form:errors path="age"></form:errors></font><br/>
    <fmt:message key="index.userCity"/>:<form:select path="userCity.id" items="${addr}" itemValue="id" itemLabel="city"/><br/>
    <!-- 多选框  而当返回的是一个List 集合时就需要手动设置 ,实体类需要一个Addr对象时，这里新增userCity.id 就会接受这个对象，新增需要id新增就可以了 -->
    <fmt:message key="index.birth"/>:<form:input path="birth"/><font color="red"><form:errors path="birth"></form:errors></font><br/>
    <fmt:message key="index.salary"/>:<form:input path="salary"/><font color="red"><form:errors path="salary"></form:errors></font><br/>
    <fmt:message key="index.Email"/>:<form:input path="email"/><font color="red"><form:errors path="email"></form:errors></font><br/>
    <input type="submit" value="提交"/>
</form:form>



</body>
</html>
