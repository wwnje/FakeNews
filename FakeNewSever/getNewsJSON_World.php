﻿<?php
//header("Content-type: text/html; charset=utf-8"); 

/*
 * 获得JSON数据
 * 返回值：title desc time content_url pic_url
 */
 
 require 'mysql_connect.php';


$obj = file_get_contents('php://input');

$obj=json_decode($obj);

$limit = $obj->limit;
$offset = $obj ->offset;


$sql ="select * from news where type = 'World' order by id desc limit $limit offset $offset"; //SQL


$result =mysql_query($sql);//执行SQL


$json ="";
$data =array(); //定义好一个数组.PHP中array相当于一个数据字典.
//定义一个类,用到存放从数据库中取出的数据.

class User 
{
public $title ;
public $desc ;
public $time ;
public $content_url ;
public $pic_url ;
public $type ;
 public $finder ;
}

//
while ($row= mysql_fetch_array($result, MYSQL_ASSOC))
{
$user =new User();
$user->title = $row["title"];
$user->desc = $row["desc"];
$user->time = $row["time"];
$user->content_url = $row["content_url"];
$user->pic_url = $row["pic_url"];
$user->type = $row["type"];
 $user->finder = $row["finder"];


$data[]=$user;
}
$json = json_encode($data);//把数据转换为JSON数据.
echo "{".'"user"'.":".$json."}";



?>