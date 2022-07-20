<%--
  Created by IntelliJ IDEA.
  User: 86157
  Date: 2022/7/17
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

$.ajax({
url : "",
data: {

},
type: "",
dataType: "",
success: function () {

}
})


// 当前系统时间
// 当前登录的用户
String createTime = DateTimeUtil.getSysTime();
String createBy = ((User)request.getSession().getAttribute("user")).getName();

$(".time").datetimepicker({
minView: "month",
language:  'zh-CN',
format: 'yyyy-mm-dd',
autoclose: true,
todayBtn: true,
pickerPosition: "bottom-left"
});
</body>
</html>
