<?php
/*
* 登陆判断
*/
require 'config.php';

header("Content-type:text/html;charset=utf-8");

$username=$_POST['username'];
$password=md5($_POST['password']);
$md=$_POST['md'];

if($md == 'orvnge'){
	//增加
	$sql="insert into `login`(`username`,`password`)values('$username','$password')";
	$set=mysql_query($sql);
}else{
	echo "<script>alert('注册码 error');location='index.php';</script>";
}


if($set)
{
	echo "register success";
	//echo "<script>alert('login success');location='main.php';</script>";
	//重定向浏览器
	header("Location: index.php");
//确保重定向后，后续代码不会被执行
	exit;

}else{
	echo "register error";
}
?>

