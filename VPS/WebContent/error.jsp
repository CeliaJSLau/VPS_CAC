<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" http-equiv="Content-Type"  content="width=device-width, initial-scale=1.0">
<link href="css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<style>

.content {
	 max-width: 1000px;
  margin: auto;
  background: white;
  padding: 0px 10px 10px 10px;
}

</style>
<title>Customer Appreciation Card</title>
</head>
<body>
<br/><br/>

		<div class="header" style="text-align:center">
			<h4 style="text-align: center;font-size:1.5em;margin-top:0px">顧客讚賞卡  Customer Appreciation Card</h4>
		</div>

		<div class="content">
<h4 style="font-size:1.2em;">錯誤 Error:</h4>
<%if (response.getStatus() == 404) {%>
無效訪問 Invalid Access <br><br>
<%} else {%>
內部錯誤， 請稍後再試     Internal error， please try again later 
<%}%>
</div>

</body>
</html>