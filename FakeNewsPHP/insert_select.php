<?php
/*
* 登陆判断
*/
header("Content-type:text/html;charset=utf-8");


require 'config.php';


$title=$_POST['title'];
$desc=$_POST['desc'];
$content_url=$_POST['content_url'];
$pic_url=$_POST['pic_url'];
$type=$_POST['type'];

//增加
$sql="insert into `news`(`title`,`desc`,`content_url`,`pic_url`,`type`)values('$title','$desc','$content_url','$pic_url','$type')";

$set=mysql_query($sql);

if($set)
{
	//echo "insert success";
	echo $title;

	echo "<script>alert('insert success');location='index.php';</script>";
	//重定向浏览器
	//header("Location: main.php");
//确保重定向后，后续代码不会被执行
	//exit;


}else{
	echo "insert error";
}
?>

