<%--
  Created by IntelliJ IDEA.
  User: pengz
  Date: 2023/10/16
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--直接请求CustomerFurnServlet，获取网站首页要显示的分页数据--%>
<jsp:forward page="/customerFurnServlet?action=pageByName&pageNo=1"></jsp:forward>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  </body>
</html>
