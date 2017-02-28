<?php
/**
 * Created by PhpStorm.
 * User: wwnje
 * Date: 2017/2/19
 * Time: 下午6:33
 * 返回所有书签
 * bookmark_id,
 */

require 'mysql_connect.php';


$obj = file_get_contents('php://input');
$obj = json_decode($obj);

/**
 * 请求数据
 */
$limit = $obj->limit;
$offset = $obj->offset;
$book_version = $obj->book_version;
$finder_id = $obj->finder_id;
//$limit = 100;
//$offset = 0;
//$book_version = 0;
//$finder_id = 1;

$json = "";
$data = array(); //定义好一个数组.PHP中array相当于一个数据字典.


class BookMark
{
    public $bookmark_id;//书签号  用于之后删除
    /**
     * @var 内容本身信息
     */
    public $news_title;
    public $news_desc;
    public $news_content_url;
    public $news_pic_url;
    public $type;//暂时定一个大类  数量最多的

    /**
     * @var finder信息 用于好友关系
     */
    public $finder_id;//推荐的人id
    public $finder_name;//推荐人的姓名

    public $book_version;//申请版本 用于更新
}

//获得该finder的所有 bookmark_id, news_id, time;
$sql1 = "select * from finder_bookmark WHERE finder_id = $finder_id limit $limit offset $offset"; //SQL
$result1 = mysql_query($sql1);//执行SQL

while ($row1 = mysql_fetch_assoc($result1))//将result结果集中查询结果取出一条
{
    $bookmark_id = $row1["bookmark_id"];

    $sql = "select * from news WHERE news_id = '" . $row1['news_id'] . "'"; //查找该条news的信息还有推荐人的id
    $result = mysql_query($sql);//执行SQL

    while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {

        $book = new BookMark();

        $book->bookmark_id = $bookmark_id;
        $book->news_title = $row["title"];
        $book->news_desc = $row["desc"];
        $book->news_content_url = $row["content_url"];
        $book->news_pic_url = $row["pic_url"];
        $book->type = $row["type"];
        $book->finder_id = $row["finder_id"];

        //查找推荐人的信息
        $sql_finder = "select name from finder WHERE finder_id = '" . $row["finder_id"] . "'"; //查找finder
        $result_finder = mysql_query($sql_finder);//执行SQL

        while ($row_finder = mysql_fetch_array($result_finder, MYSQL_ASSOC)) {
            $book->finder_name = $row_finder["name"];
        }

        $book->book_version = $book_version;
        $data[] = $book;
    }
}
$json = json_encode($data);//把数据转换为JSON数据.
echo "{" . '"bookmarks"' . ":" . $json . "}";
?>


