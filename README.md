# 闲暇(Leisure)
Leisure is an Android App containing Zhihu Daily,Guokr Scientific,XinhuaNet News and Douban Books. 

  　　　
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


#版本更新
**2015-12-12 初版(1.0)完成**<br>
**2015-12-24 Version 1.1 版发布**<br>
0.初版发布之后收到了很多朋友的反馈,在此表示感谢!<br>
由于最近比较忙,在第一版中日报数据服务器不稳定等问题准备放在第三个版本中.<br>
1.这版的更新主要是修复一个严重的bug:数据冗余,刷新后之前数据没有被清除,新老数据出现重复.下载更新版本后,手动刷新即可获取正确数据.
2.软件开发靠开发者一人之力是不够的,MummyDing希望各位也能参与进来,让"闲暇"越来越好!<br>
3.Bug反馈即可在评论区留言,同时也可到项目主页发布issue:https://github.com/MummyDing/Leisure/issues<br>
本项目为开源项目,技术交流可以通过邮箱联系:MummyDing@outlook.com<br>
**2016-01-03 Version 2.0 版发布**<br>
上次的更新比较失败,导致不少新bug.这次经过更严谨的测试,主要做了以下改进:<br>
0. 日报数据更加可靠,响应速度明显提升,内容更加丰富.<br>
1. 日报页面更加美观,体验更好<br>
2. 修复列表缓存不全bug<br>
3. 修复收藏bug<br>
4. 各模块列表UI做了优化<br>
5. 优化夜间模式<br>
本次更新更侧重于修复之前的bug,UI上的美化将放在之后的版本中.<br>
个人开发者业余开发,更新不及时请见谅,感谢支持.<br>
项目地址: https://github.com/MummyDing/Leisure<br>
**2016-02-19 Version 2.1 版发布**<br>
新版来了，让大家久等了。上个礼拜还有同学发邮件给我反馈bug，不过这个寒假真的忙<br>
忙忙，凌晨一点+睡觉已成日常。断断续续地修复了些bug，想想拖的太久还是早点发出来。<br>
如果你发现之前反馈的bug在这个版本还没修复还请原谅，评论区的留言我都会看，已经<br>
修复的bug我一般都会新版发布的时候在评论区回复提醒。UI上的更新主要是当前和KevinWu<br>
合作项目中改进的，废话到此为止,看看这次的更新吧！<br>
0.修复日报详情页无图模式<br>
1.修复日报部分卡片FC bug<br>
2.修复详情页(日报、科学、新闻)内部链接FC bug<br>
3.优化日报、科学模块列表 <br>
4.优化日报列表图片清晰度<br>
5.优化日报、科学详情页布局<br>
6.优化夜间模式图标&文字 提示<br>
7.增加滑动返回功能:支持左部滑动&任意位置滑动<br>
8.关闭日报、科学 详情页夜间模式(这个是因为目前效果不好，等以后优化好了再恢复)<br>
9.默认关闭摇晃返回<br>
10.增加日报、科学 文章分享功能<br>
11.本想重绘个logo，画了半天还是各种丑，无奈又写了个字，丑就让它丑到家吧<br>
**2016-09-11 Version 2.2 版发布**<br>
0. 修复6.0以上设备"科学"详情页在网络正常的情况下出现"网络错误"图标  
1. 修复6.0以上设备“新闻”详情页滑动时出现网络错误页
#源码打包下载
1. [Version 1.0][7]
2. [Version 1.1][8]
3. [Version 2.0][9]
4. [Version 2.1][10]


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
[6]: http://app.mi.com/detail/130045
[7]: https://github.com/MummyDing/Leisure/archive/v1.0.zip
[8]: https://github.com/MummyDing/Leisure/archive/v1.1.zip
[9]: https://github.com/MummyDing/Leisure/archive/v2.0.zip
[10]: https://github.com/MummyDing/Leisure/archive/v2.1.zip
