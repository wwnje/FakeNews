<?php

//session_start();
/*
*登陆界面
*/
//require 'config.php';
header("Content-type:text/html;charset=utf-8");
?>

<html>
<head>
<title>register</title>
</head>

<body>
<center>

<table>
<col width=150>
<col width=200>

	<form action="register_select.php" method="post">
		<tr>
		<td>register<br /></td><tr>
		<tr><td>username：<input type="text" name="username"><br /></td></tr>
		<tr><td>password：<input type="password" name="password"> <br /></td></tr>
		<tr><td>注册码：<input type="text" name="md"> <br /></td></tr>
		<tr><td><input type="submit" name="sub" value="submit"></td></tr>
	</form>
	</table>
</center>
</body>

</html>