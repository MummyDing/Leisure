/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mummyding.app.leisure.api;

import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 2015-11-15.<br>
 * DouBan(www.douban.com) Books Api
 * @author MummyDing
 * @version 1.0
 */
public class ReadingApi {
    public static final int TAG_LEN = 3;
    public static String searchByTag="http://api.douban.com/v2/book/search?tag=";
    public static String searchByText="http://api.douban.com/v2/book/search?q=";
    public static String searchByID="https://api.douban.com/v2/book/";
    public static String readEBook="http://read.douban.com/reader/ebook/";
    public static String Tag_Titles []={"综合","文学","程序员","流行","文化","生活","金融"};
    public static String HomeTag[] ={"科普", "互联网", "科学", "科技","科普","用户体验", "通信", "交互", "旅行","王小波",  "生活", "励志", "成长",  "悬疑", "武侠", "韩寒", "奇幻", "青春文学"};
    public static String LiterTag []={"小说","中国文学", "村上春树", "王小波", "余华", "鲁迅", "米兰·昆德拉", "外国文学", "经典", "童话", "儿童文学", "名著", "外国名著", "杜拉斯", "文学", "散文", "诗歌", "张爱玲", "钱钟书", "诗词", "港台", "随笔", "日本文学", "杂文", "古典文学", "当代文学", "茨威格","米兰·昆德拉","杜拉斯","港台"};
    public static String CoderTag[] ={"编程",  "交互", "设计", "算法", "web", "UE",  "互联网", "用户体验", "通信", "交互", "UCD"};
    public static String PopularTag[] ={"漫画","绘本", "推理", "青春", "言情","科幻", "东野圭吾", "悬疑", "武侠", "韩寒", "奇幻", "日本漫画", "耽美", "亦舒", " 三毛", "安妮宝贝", "网络小说", "郭敬明", "推理小说", "穿越", "金庸", "轻小说", "几米", "阿加莎·克里斯蒂","张小娴", "幾米", "魔幻", "青春文学", "J.K.罗琳", "科幻小说", "高木直子", "古龙", "沧月", "蔡康永", "落落", "张悦然"};
    public static String CultureTag[] ={"历史","心理学","哲学","传记","文化","社会学","艺术","设计","政治","社会","建筑","宗教","电影","数学","政治学","回忆录","思想","国学","中国历史","音乐","人文","戏剧","人物传记","绘画","艺术史","佛教","军事","西方哲学","近代史","二战","自由主义","考古","美术"};
    public static String LifeTag[]={"爱情","旅行", "生活", "励志", "成长", "心理", "摄影", "女性", "职场", "美食", "教育", "游记", "灵修", "情感", "健康", "手工", "养生", "两性", "人际关系", "家居", "自助游"};
    public static String FinancialTag[] ={"经济学", "管理", "经济", "金融", "商业", "投资", "营销", "创业", "理财", "广告", "股票", "企业史", "策划"};
    public static String bookTab_Titles[] ={"内容简介","目录","作者简介"};
    public static String[] getApiTag(int pos){
        switch (pos){
            case 0:
                return HomeTag;
            case 1:
                return LiterTag;
            case 2:
                return CoderTag;
            case 3:
                return PopularTag;
            case 4:
                return CultureTag;
            case 5:
                return LifeTag;
            case 6:
                return FinancialTag;
        }
        return null;
    }

    /**
     * Get book tags randomly
     * @param tag
     * @return tags
     */
    public static String [] getTags(String [] tag){
        int len = tag.length;
        String [] res = new String[TAG_LEN];
        for(int i =0 ;i< TAG_LEN;i++){
            boolean flag = true;
            int tmp = 0;
            while (flag) {
                flag = false;
                 tmp = (int) (Math.random() * len);
                for(int j = 0; j<i;j++)
                    if(res[j].equals(tag[tmp])) {
                        flag = true;
                        break;
                    }
            }
            res[i] = tag[tmp];
        }
        return res;
    }

    /**
     * Get details of book ,it contains Book Summary, Book Contents and Author Introduction<br>
     * if api does provide any info ,it will return a "" string
     * @param position position of tab.
     * @param book
     * @return
     */
    public static String getBookInfo(int position,BookBean book){
        switch (position){
            case 0:
                if(Utils.hasString(book.getSummary()) == false) break;
                return book.getSummary();
            case 1:
                if(Utils.hasString(book.getCatalog()) == false)break;
                return  book.getCatalog();
            case 2:
                if(Utils.hasString(book.getAuthor_intro()) == false)break;
                return book.getAuthor_intro();
        }
        return LeisureApplication.AppContext.getString(R.string.text_noinfo);
    }
}
