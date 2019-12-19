package com.linkhand.lajiaoshipin.bean;

import java.util.List;

public class UserCollectionBean {


    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"30","user_id":"12","title":"dsadassads23","url":{"action":"portal/Article/index","param":{"id":"45"}},"description":"dsadassads23","table_name":"portal/20191021/5513167b56bd4751ac8b9aea2961a8ae.jpg","object_id":"45","create_time":"1575339456","post_hits":"31","more":{"thumbnail":"portal/20191021/5513167b56bd4751ac8b9aea2961a8ae.jpg","template":"","files":[{"url":"portal/20191021/4bf4921bc52c3bcd65339b69c5a2db37.m3u8","name":"demo.m3u8"}]}}]
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
         * id : 30
         * user_id : 12
         * title : dsadassads23
         * url : {"action":"portal/Article/index","param":{"id":"45"}}
         * description : dsadassads23
         * table_name : portal/20191021/5513167b56bd4751ac8b9aea2961a8ae.jpg
         * object_id : 45
         * create_time : 1575339456
         * post_hits : 31
         * more : {"thumbnail":"portal/20191021/5513167b56bd4751ac8b9aea2961a8ae.jpg","template":"","files":[{"url":"portal/20191021/4bf4921bc52c3bcd65339b69c5a2db37.m3u8","name":"demo.m3u8"}]}
         */

        private String id;
        private String user_id;
        private String title;
        private UrlBean url;
        private String description;
        private String table_name;
        private String object_id;
        private String create_time;
        private String post_hits;
        private MoreBean more;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public UrlBean getUrl() {
            return url;
        }

        public void setUrl(UrlBean url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTable_name() {
            return table_name;
        }

        public void setTable_name(String table_name) {
            this.table_name = table_name;
        }

        public String getObject_id() {
            return object_id;
        }

        public void setObject_id(String object_id) {
            this.object_id = object_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPost_hits() {
            return post_hits;
        }

        public void setPost_hits(String post_hits) {
            this.post_hits = post_hits;
        }

        public MoreBean getMore() {
            return more;
        }

        public void setMore(MoreBean more) {
            this.more = more;
        }

        public static class UrlBean {
            /**
             * action : portal/Article/index
             * param : {"id":"45"}
             */

            private String action;
            private ParamBean param;

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public ParamBean getParam() {
                return param;
            }

            public void setParam(ParamBean param) {
                this.param = param;
            }

            public static class ParamBean {
                /**
                 * id : 45
                 */

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

        public static class MoreBean {
            /**
             * thumbnail : portal/20191021/5513167b56bd4751ac8b9aea2961a8ae.jpg
             * template :
             * files : [{"url":"portal/20191021/4bf4921bc52c3bcd65339b69c5a2db37.m3u8","name":"demo.m3u8"}]
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
                 * url : portal/20191021/4bf4921bc52c3bcd65339b69c5a2db37.m3u8
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
