package orvnge.wwnje.com.fucknews.utils;

/**
 * Created by wwnje on 2016/9/4.
 * 存储php端信息
 */

public class BlankAPI {
    /**
     * 用户操作
     */
    public static  String Register_Url = "http://www.wwnje.com/FakeNews/Android_Register.php";//帐号注册
    public static  String Login_Url = "http://www.wwnje.com/FakeNews/Android_Login.php";//帐号注册
    public static  String SHARE_NEWS = "http://www.wwnje.com/FakeNews/Android_Share_News.php";//分享文章

    /**
     * 订阅标签和新闻
     */
    public static  String ADD_ITEMS_URL = "http://www.wwnje.com/FakeNews/API_Add_Items.php";//订阅标签或者新闻类别

    /**
     * 加入书签或者喜欢某个新闻
     */
    public static  String ADD_NEWS_ITEM_URL = "http://www.wwnje.com/FakeNews/API_LIKE_OR_BOOKMARK_News_Items.php";

    /**
     * 检查或者新建tags
     */
    public static  String URL_CHECK_OR_NEW_TAGS = "http://www.wwnje.com/FakeNews/API_CHECK_OR_NEW_TAGS.php";

    /**
     * 获取列表
     */
    public static  String GET_TYPE_URL = "http://www.wwnje.com/FakeNews/API_GetType.php";//获取所有内容类别
    public static  String GET_TAGS_URL = "http://www.wwnje.com/FakeNews/API_GetTags.php";//获取所有标签
    public static  String GET_MY_TAGS_URL = "http://www.wwnje.com/FakeNews/API_GetMyTags.php";//获取订阅标签
    public static  String GET_MY_NEWS_TYPE_URL = "http://www.wwnje.com/FakeNews/API_GetMyNews_Type.php";//获取新闻类型标签
    public static  String GET_BOOKMARK_URL = "http://www.wwnje.com/FakeNews/API_GetBookMark.php";//获取所有书签
    public static  String GET_LIKELIST_URL = "http://www.wwnje.com/FakeNews/API_GetLikeList.php";//获取喜欢的列表

    public static  String GET_MY_SHARED_NEWS = "http://www.wwnje.com/FakeNews/API_GetMyNews.php";//获取我所有发布的

    public static  String GET_HISTORY = "http://wwnje.com/FakeNews/API_GetHistory.php";//获取历史消息

    public static  String GET_SYS = "http://wwnje.com/FakeNews/_SYS.php";//退出 执行推荐操作

    /**
     * 获取新闻内容
     */
    public static  String GET_NEWS_URL = "http://www.wwnje.com/FakeNews/API_Get_NEWS.php";

    //知乎
    public static String BASE_URL = "https://zhuanlan.zhihu.com/api/columns/";
    public static String ZHUHU_POST_URL = "https://zhuanlan.zhihu.com/api/posts/";

    //豆瓣
    public static String DOUBAN_BOOK_URL = "https://api.douban.com/v2/book/1220562";
    public static String DOUBAN_NOTE_URL = "https://api.douban.com/v2/note/561055453\n";

}
