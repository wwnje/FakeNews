<?php
header("Content-type: text/html; charset=utf-8"); 

require 'mysql_connect.php';

echo "测试";
$title = $_POST['title'];
$desc = $_POST['desc'];
$content_url = $_POST['news_link'];
$pic_url = $_POST['news_img_link'];
$type = $_POST['tag'];
$finder_id = $_POST['finder_id'];

//增加
$sql="insert into `news`(`title`,`desc`,`content_url`,`pic_url`,`type`,`finder_id`)values('$title','$desc','$content_url','$pic_url','$type', '$finder_id')";

$set=mysql_query($sql);

if($set)
{
	//echo "insert success";
	echo "200";
}else{
	echo "插入错误".$title;
}
?>