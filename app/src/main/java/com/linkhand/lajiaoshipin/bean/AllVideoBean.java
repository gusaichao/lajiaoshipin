package com.linkhand.lajiaoshipin.bean;

import java.util.List;

public class AllVideoBean {




    private int code;
    private String msg;
    private String info;
    private List<DataBean> data;
    private List<AdvBean> adv;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<AdvBean> getAdv() {
        return adv;
    }

    public void setAdv(List<AdvBean> adv) {
        this.adv = adv;
    }

    public static class DataBean {
        /**
         * post_hits : 6
         * post_play_time : 01:28:32
         * more : {"thumbnail":"portal/20191205/b83b484c9702a68a0876c7bfdfed5486.jpg","template":"","files":[{"url":"portal/20191205/9187e980d90c5a55efdea1ff5e08c7ac.m3u8","name":"13244.m3u8"}]}
         * id : 21566
         * category_id : 32
         * post_title : 【香港】三级电影《大内密探之零零性性》
         */

        private int post_hits;
        private String post_play_time;
        private MoreBean more;
        private int id;
        private int category_id;
        private String post_title;

        public int getPost_hits() {
            return post_hits;
        }

        public void setPost_hits(int post_hits) {
            this.post_hits = post_hits;
        }

        public String getPost_play_time() {
            return post_play_time;
        }

        public void setPost_play_time(String post_play_time) {
            this.post_play_time = post_play_time;
        }

        public MoreBean getMore() {
            return more;
        }

        public void setMore(MoreBean more) {
            this.more = more;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public static class MoreBean {
            /**
             * thumbnail : portal/20191205/b83b484c9702a68a0876c7bfdfed5486.jpg
             * template :
             * files : [{"url":"portal/20191205/9187e980d90c5a55efdea1ff5e08c7ac.m3u8","name":"13244.m3u8"}]
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
                 * url : portal/20191205/9187e980d90c5a55efdea1ff5e08c7ac.m3u8
                 * name : 13244.m3u8
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

    public static class AdvBean {
        /**
         * a_img : advertisement/20191205/b031cbd9d9af8bf846cdd34f3eded3d4.jpg
         * a_url : http://www.baidu.com
         */

        private String a_img;
        private String a_url;

        public String getA_img() {
            return a_img;
        }

        public void setA_img(String a_img) {
            this.a_img = a_img;
        }

        public String getA_url() {
            return a_url;
        }

        public void setA_url(String a_url) {
            this.a_url = a_url;
        }
    }
}
