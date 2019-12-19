package com.linkhand.lajiaoshipin.bean;

import java.util.List;

public class CatBean {


    /**
     * code : 200
     * msg : 请求成功
     * data : [{"name":"全部"},{"id":"1","name":"测试100"},{"id":"2","name":"测试12"},{"id":"6","name":"国产影片"},{"id":"7","name":"美剧"},{"id":"8","name":"迪士尼"},{"id":"9","name":"日剧"},{"id":"10","name":"韩剧"},{"id":"11","name":"测试1影片"},{"id":"12","name":"测试2影片"}]
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
         * name : 全部
         * id : 1
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
