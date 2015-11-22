package com.mummyding.app.leisure.model.science;

import java.io.Serializable;

/**
 * Created by mummyding on 15-11-17.
 */
public class ArticleBean implements Serializable{
    private String image;
    private boolean is_replyable;
    private Channel_Subject [] channels;
    class Channel_Subject implements Serializable{
        String url;
        String date_created;
        String name;
        String key;
        int articles_count;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getArticles_count() {
            return articles_count;
        }

        public void setArticles_count(int articles_count) {
            this.articles_count = articles_count;
        }
    }
    private String [] channel_keys;
    private String preface;
    private int id;
    private CharSequence subject;
    private String copyright;
    private Author author;
    class Author implements Serializable{
        String ukey;
        boolean is_title_authorized;
        String nickname;
        String amended_reliability;
        boolean is_exists;
        String title;
        String url;
        String gender;
        int follower_count;
        Avatar avatar;
        class Avatar{
            String large;
            String small;
            String normal;

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getNormal() {
                return normal;
            }

            public void setNormal(String normal) {
                this.normal = normal;
            }
        }
        String resource_url;

        public String getUkey() {
            return ukey;
        }

        public void setUkey(String ukey) {
            this.ukey = ukey;
        }

        public boolean is_title_authorized() {
            return is_title_authorized;
        }

        public void setIs_title_authorized(boolean is_title_authorized) {
            this.is_title_authorized = is_title_authorized;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAmended_reliability() {
            return amended_reliability;
        }

        public void setAmended_reliability(String amended_reliability) {
            this.amended_reliability = amended_reliability;
        }

        public boolean is_exists() {
            return is_exists;
        }

        public void setIs_exists(boolean is_exists) {
            this.is_exists = is_exists;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public Avatar getAvatar() {
            return avatar;
        }

        public void setAvatar(Avatar avatar) {
            this.avatar = avatar;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }
    }
    private String image_description;
    private boolean is_show_summary;
    private String minisite_key;
    private Image_info image_info;
    public class Image_info implements Serializable{
        String url;
        int width;
        int height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
    private String subject_key;
    private Minisite minisite;
    class Minisite implements Serializable{
        String name;
        String url;
        String introduction;
        String key;
        String date_created;
        String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
    private String [] tags;
    private String date_published;
    //private Author[] authors;
    private int replies_count;
    private boolean is_author_external;
    private int recommends_count;
    private String title_hide;
    private String date_modified;
    private String url;
    private String title;
    private String small_image;
    private String summary;
    private String ukey_author;
    private String date_created;
    private String resource_url;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean is_replyable() {
        return is_replyable;
    }

    public void setIs_replyable(boolean is_replyable) {
        this.is_replyable = is_replyable;
    }

    public Channel_Subject[] getChannels() {
        return channels;
    }

    public void setChannels(Channel_Subject[] channels) {
        this.channels = channels;
    }

    public String[] getChannel_keys() {
        return channel_keys;
    }

    public void setChannel_keys(String[] channel_keys) {
        this.channel_keys = channel_keys;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CharSequence getSubject() {
        return subject;
    }

    public void setSubject(CharSequence subject) {
        this.subject = subject;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImage_description() {
        return image_description;
    }

    public void setImage_description(String image_description) {
        this.image_description = image_description;
    }

    public boolean is_show_summary() {
        return is_show_summary;
    }

    public void setIs_show_summary(boolean is_show_summary) {
        this.is_show_summary = is_show_summary;
    }

    public String getMinisite_key() {
        return minisite_key;
    }

    public void setMinisite_key(String minisite_key) {
        this.minisite_key = minisite_key;
    }

    public Image_info getImage_info() {
        return image_info;
    }

    public void setImage_info(Image_info image_info) {
        this.image_info = image_info;
    }

    public String getSubject_key() {
        return subject_key;
    }

    public void setSubject_key(String subject_key) {
        this.subject_key = subject_key;
    }

    public Minisite getMinisite() {
        return minisite;
    }

    public void setMinisite(Minisite minisite) {
        this.minisite = minisite;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public int getReplies_count() {
        return replies_count;
    }

    public void setReplies_count(int replies_count) {
        this.replies_count = replies_count;
    }

    public boolean is_author_external() {
        return is_author_external;
    }

    public void setIs_author_external(boolean is_author_external) {
        this.is_author_external = is_author_external;
    }

    public int getRecommends_count() {
        return recommends_count;
    }

    public void setRecommends_count(int recommends_count) {
        this.recommends_count = recommends_count;
    }

    public String getTitle_hide() {
        return title_hide;
    }

    public void setTitle_hide(String title_hide) {
        this.title_hide = title_hide;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUkey_author() {
        return ukey_author;
    }

    public void setUkey_author(String ukey_author) {
        this.ukey_author = ukey_author;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    @Override
    public String toString() {
        return getAuthor().getNickname()+"  "+getDate_published();
    }
}
