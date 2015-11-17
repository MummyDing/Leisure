package com.mummyding.app.leisure.model.science;


/**
 * Created by mummyding on 15-11-17.
 */
public class ArticleContentBean {
    private String now;
    private boolean ok;
    private ArticleDetail result;
    class ArticleDetail{
        String image;
        boolean is_replyable;
        ArticleBean.Channel_Subject channel;
        String [] channel_keys;
        String preface;
        int id;
        ArticleBean.Channel_Subject subject;
        String copyright;
        ArticleBean.Author author;
        String image_description;
        String content;
        boolean is_show_summary;
        String minisite_key;
        ArticleBean.Image_info image_info;
        String subject_key;
        ArticleBean.Minisite minisite;
        String [] tags;
        String date_published;
        ArticleBean.Author authors;
        int replies_count;
        boolean is_author_external;
        int recommends_count;
        String title_hide;
        String date_modified;
        String url;
        String title;
        String small_image;
        String summary;
        String ukey_author;
        String date_created;
        String resource_url;

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

        public ArticleBean.Channel_Subject getChannel() {
            return channel;
        }

        public void setChannel(ArticleBean.Channel_Subject channel) {
            this.channel = channel;
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

        public ArticleBean.Channel_Subject getSubject() {
            return subject;
        }

        public void setSubject(ArticleBean.Channel_Subject subject) {
            this.subject = subject;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public ArticleBean.Author getAuthor() {
            return author;
        }

        public void setAuthor(ArticleBean.Author author) {
            this.author = author;
        }

        public String getImage_description() {
            return image_description;
        }

        public void setImage_description(String image_description) {
            this.image_description = image_description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public ArticleBean.Image_info getImage_info() {
            return image_info;
        }

        public void setImage_info(ArticleBean.Image_info image_info) {
            this.image_info = image_info;
        }

        public String getSubject_key() {
            return subject_key;
        }

        public void setSubject_key(String subject_key) {
            this.subject_key = subject_key;
        }

        public ArticleBean.Minisite getMinisite() {
            return minisite;
        }

        public void setMinisite(ArticleBean.Minisite minisite) {
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

        public ArticleBean.Author getAuthors() {
            return authors;
        }

        public void setAuthors(ArticleBean.Author authors) {
            this.authors = authors;
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
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArticleDetail getResult() {
        return result;
    }

    public void setResult(ArticleDetail result) {
        this.result = result;
    }
}
