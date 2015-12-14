# 闲暇(Leisure)
Leisure is an Android App,it contains Zhihu Daily,Guokr Scientific,XinhuaNet News and Douban Books 

  　　　
![alt text](./logo.png)   

酷安应用市场下载地址:[http://coolapk.com/apk/com.mummyding.app.leisure][5]<br>
豌豆荚应用下载地址:  [http://www.wandoujia.com/apps/com.mummyding.app.leisure][1]<br>
小米应用商店下载地址: [http://app.mi.com/detail/130045][6]<br>
360应用市场下载地址: [http://m.app.so.com/detail/index?pname=com.mummyding.app.leisure&id=3167599][4]<br>
视频(Video): [http://v.youku.com/v_show/id_XMTQwOTU4MzE5Mg==.html][2]<br>
Blog:[http://blog.csdn.net/mummyding/article/details/50266203][3]<br>

-----------


　　　闲暇(Leisure)是一款集"知乎日报"、“果壳科学人”、“新华网新闻”以及“豆瓣图书”于一体的阅读类Android应用。
果壳、知乎和豆瓣在国内拥有大量用户，这些社区的用户每天都产生很多高质量内容。闲暇以其简介的风格将这几大社区
的优质内容整合于一体，使得用户能有效地获取这些内容，大大节省了用户的时间。   
它主要分为以下几个模块:   
1. 日报: 数据来源于知乎日报RSS，内容精选自知乎优质答案。   
2. 阅读:接入豆瓣图书API，将图书进行分类展现并且支持搜索功能，部分图书还提供了电子书。   
3. 新闻:数据来源于新华网RSS，第一时间获取最新资讯。其内容简洁真实权威，相对于国内一些娱乐化新闻平台，更加严肃务实。   
4. 科学:接入果壳网API，果壳网作为一个开放、多元的泛科技兴趣社区，吸引了百万名有意思、爱知识、乐于分享的年轻人聚集在这里，用知识创造价值，为生活添加智趣。   
5. 收藏:用户收藏内容，方便用户保存优质内容。   
6. 个性化设置:
  - 夜间模式: 方便用户在光线较弱或是黑暗环境下阅读，减弱屏幕光线对眼睛的刺激
  - 搜索:图书搜索功能提供关键字搜索和类别搜索模式，用户可以自行选择
  - 摇晃返回:用户可以通过轻轻晃动手机触发页面返回。此功能可由用户自行设置是否开启
  - 退出确认:该功能默认开启，按两次返回键退出应用，防止用户误触返回键退出应用
  - 无图模式:仅在WIFI模式下加载图片，节省用户数据流量
  - 自动刷新:开启该功能后，在WIFI下自动刷新，获取最新内容
  - 语言:　支持繁简中文和英文，满足用户语言习惯
  - 清除缓存:用户可以手动清除缓存，节约手机存储空间
  
  
MainActivity: 入口界面。做一些初始化的工作，加载应用整体框架，负责基本子界面的切换。<br>
BaseListFragment：列表界面基类。定制性强，可自定义是否启用头部导航布局　下拉刷新等组件<br>
AsbtopNavigationFragment:　头部导航布局基类,继承它即可管理头部导航布局。<br>
BaseAdapter: RecyclerView　的Adapter基类<br>
       -- BaseCache 主模块数据缓存基类　<br>
ICache---　缓存接口<br>
       -- BaseCollectionCache　收藏模块缓存基类<br>


# 应用截图

<img src="/ScreenShots/home.png" width="200" height="380"/> 
<img src="/ScreenShots/science.png" width="200" height="380"/> 
<img src="/ScreenShots/ebook.png" width="200" height="380"/> 
<img src="/ScreenShots/settings.png" width="200" height="380"/> 
<img src="/ScreenShots/about.png" width="200" height="380"/> 
[1]:(http://www.wandoujia.com/apps/com.mummyding.app.leisure)
[2]:(http://v.youku.com/v_show/id_XMTQwOTU4MzE5Mg==.html)
[3]:(http://blog.csdn.net/mummyding/article/details/50266203)
[4]:(http://m.app.so.com/detail/index?pname=com.mummyding.app.leisure&id=3167599)
[5]:(http://coolapk.com/apk/com.mummyding.app.leisure)
[6]:http://app.mi.com/detail/130045
