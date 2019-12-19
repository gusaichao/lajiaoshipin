package com.linkhand.lajiaoshipin.bean;

import java.util.List;

public class FindvideoBean {


    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"45","parent_id":"0","post_type":"1","post_format":"1","user_id":"1","post_status":"1","comment_status":"1","is_top":"0","recommended":"0","post_hits":"5","post_like":"0","comment_count":"0","create_time":"1574740095","update_time":"1574740095","published_time":"1574748281","delete_time":"0","post_title":"dsadassads23","post_keywords":"","post_excerpt":"asdsa","post_source":"","post_content":null,"post_content_filtered":null,"more":{"thumbnail":"","template":"","files":[{"url":"portal/20191126/59a636e690a30d3f4f65458be24a4ae5.m3u8","name":"demo.m3u8"}]},"post_play_time":"00:04:47"},{"id":"44","parent_id":"0","post_type":"1","post_format":"1","user_id":"1","post_status":"1","comment_status":"1","is_top":"1","recommended":"0","post_hits":"6","post_like":"0","comment_count":"0","create_time":"1574735855","update_time":"1574740064","published_time":"1574735880","delete_time":"0","post_title":"fsfaffas","post_keywords":"","post_excerpt":"sfdfad ","post_source":"","post_content":null,"post_content_filtered":null,"more":{"thumbnail":"","template":"","files":[{"url":"portal/20191126/59a636e690a30d3f4f65458be24a4ae5.m3u8","name":"demo.m3u8"}]},"post_play_time":"00:04:47"},{"id":"42","parent_id":"0","post_type":"1","post_format":"1","user_id":"1","post_status":"1","comment_status":"1","is_top":"1","recommended":"1","post_hits":"7","post_like":"0","comment_count":"0","create_time":"1566359287","update_time":"1573108815","published_time":"1566359220","delete_time":"0","post_title":"birds","post_keywords":"","post_excerpt":"","post_source":"","post_content":null,"post_content_filtered":null,"more":{"thumbnail":"portal/20191021/5513167b56bd4751ac8b9aea2961a8ae.jpg","template":"","files":[{"url":"portal/20191107/e62bef4c4752461ca3b6179e4ecb9a79.m3u8","name":"birds.m3u8"}]},"post_play_time":"00:02:37"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 45
         * parent_id : 0
         * post_type : 1
         * post_format : 1
         * user_id : 1
         * post_status : 1
         * comment_status : 1
         * is_top : 0
         * recommended : 0
         * post_hits : 5
         * post_like : 0
         * comment_count : 0
         * create_time : 1574740095
         * update_time : 1574740095
         * published_time : 1574748281
         * delete_time : 0
         * post_title : dsadassads23
         * post_keywords :
         * post_excerpt : asdsa
         * post_source :
         * post_content : null
         * post_content_filtered : null
         * more : {"thumbnail":"","template":"","files":[{"url":"portal/20191126/59a636e690a30d3f4f65458be24a4ae5.m3u8","name":"demo.m3u8"}]}
         * post_play_time : 00:04:47
         */

        private int id;
        private String parent_id;
        private String post_type;
        private String post_format;
        private String user_id;
        private String post_status;
        private String comment_status;
        private String is_top;
        private String recommended;
        private String post_hits;
        private String post_like;
        private String comment_count;
        private String create_time;
        private String update_time;
        private String published_time;
        private String delete_time;
        private String post_title;
        private String post_keywords;
        private String post_excerpt;
        private String post_source;
        private Object post_content;
        private Object post_content_filtered;
        private MoreBean more;
        private String post_play_time;
        private String category_id;
        private String name;
        private int type;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getPost_type() {
            return post_type;
        }

        public void setPost_type(String post_type) {
            this.post_type = post_type;
        }

        public String getPost_format() {
            return post_format;
        }

        public void setPost_format(String post_format) {
            this.post_format = post_format;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPost_status() {
            return post_status;
        }

        public void setPost_status(String post_status) {
            this.post_status = post_status;
        }

        public String getComment_status() {
            return comment_status;
        }

        public void setComment_status(String comment_status) {
            this.comment_status = comment_status;
        }

        public String getIs_top() {
            return is_top;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }

        public String getRecommended() {
            return recommended;
        }

        public void setRecommended(String recommended) {
            this.recommended = recommended;
        }

        public String getPost_hits() {
            return post_hits;
        }

        public void setPost_hits(String post_hits) {
            this.post_hits = post_hits;
        }

        public String getPost_like() {
            return post_like;
        }

        public void setPost_like(String post_like) {
            this.post_like = post_like;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getPublished_time() {
            return published_time;
        }

        public void setPublished_time(String published_time) {
            this.published_time = published_time;
        }

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_keywords() {
            return post_keywords;
        }

        public void setPost_keywords(String post_keywords) {
            this.post_keywords = post_keywords;
        }

        public String getPost_excerpt() {
            return post_excerpt;
        }

        public void setPost_excerpt(String post_excerpt) {
            this.post_excerpt = post_excerpt;
        }

        public String getPost_source() {
            return post_source;
        }

        public void setPost_source(String post_source) {
            this.post_source = post_source;
        }

        public Object getPost_content() {
            return post_content;
        }

        public void setPost_content(Object post_content) {
            this.post_content = post_content;
        }

        public Object getPost_content_filtered() {
            return post_content_filtered;
        }

        public void setPost_content_filtered(Object post_content_filtered) {
            this.post_content_filtered = post_content_filtered;
        }

        public MoreBean getMore() {
            return more;
        }

        public void setMore(MoreBean more) {
            this.more = more;
        }

        public String getPost_play_time() {
            return post_play_time;
        }

        public void setPost_play_time(String post_play_time) {
            this.post_play_time = post_play_time;
        }

        public static class MoreBean {
            /**
             * thumbnail :
             * template :
             * files : [{"url":"portal/20191126/59a636e690a30d3f4f65458be24a4ae5.m3u8","name":"demo.m3u8"}]
             */

            private String thumbnail;
            private String template;
            private List<FilesBean> files;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getTemplate() {
                return template;
            }

            public void setTemplate(String template) {
                this.template = template;
            }

            public List<FilesBean> getFiles() {
                return files;
            }

            public void setFiles(List<FilesBean> files) {
                this.files = files;
            }

            public static class FilesBean {
                /**
                 * url : portal/20191126/59a636e690a30d3f4f65458be24a4ae5.m3u8
                 * name : demo.m3u8
                 */

                private String url;
                private String name;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
