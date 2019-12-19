package com.linkhand.lajiaoshipin.bean;

import java.util.List;

public class IndexMenu {


    /**
     * code : 200
     * msg : 请求成功
     * info : 请求成功
     * data : {"menu":[{"id":36,"name":"入侵人妻","href":"33","icon":"admin/20191021/2fd3ca497c0fd657b61d27522f9c72ab.png"},{"id":33,"name":"角色扮演","href":"31","icon":"portal/20191018/7e86f335a730d8c2458ee2d7bc178dbc.png"},{"id":24,"name":"制服誘惑","href":"22","icon":"portal/20191018/96b7c8a006799f07f9376c2e24976119.png"},{"id":23,"name":"當紅女優","href":"21","icon":"portal/20191018/bb3eb2c42dd559a218e96eb01c6bab30.png"},{"id":19,"name":"翹臀美穴","href":"17","icon":"portal/20191018/061bcd6c26130cd50db235874ab1a12c.png"},{"id":22,"name":"AV劇情","href":"20","icon":"portal/20191018/cb95c1df59a8cd4cd09ab7a392ef6e7f.png"},{"id":4,"name":"高清無碼","href":"1","icon":"portal/20191018/59b4a506861eaf4213230e77024047fe.png"},{"id":35,"name":"全部","href":"0","icon":"admin/20191021/7e2b9e14fc747ffbdf4f9ca531f5cc90.jpg"}],"adv":[{"a_img":"advertisement/20191205/b031cbd9d9af8bf846cdd34f3eded3d4.jpg","a_url":"123"}],"img":[{"a_img":"advertisement/20191018/eda2f9a06080d468af2979ec9f108b1b.png","a_url":"https://www.baidu.com"},{"a_img":"advertisement/20191018/eda2f9a06080d468af2979ec9f108b1b.png","a_url":"www.baidu.com"},{"a_img":"advertisement/20191018/0691450a33852e4238e6d40d49d1c200.gif","a_url":"www.baidu.com"}]}
     */

    private int code;
    private String msg;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MenuBean> menu;
        private List<AdvBean> adv;
        private List<ImgBean> img;

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBean> menu) {
            this.menu = menu;
        }

        public List<AdvBean> getAdv() {
            return adv;
        }

        public void setAdv(List<AdvBean> adv) {
            this.adv = adv;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class MenuBean {
            /**
             * id : 36
             * name : 入侵人妻
             * href : 33
             * icon : admin/20191021/2fd3ca497c0fd657b61d27522f9c72ab.png
             */

            private int id;
            private String name;
            private String href;
            private String icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        public static class AdvBean {
            /**
             * a_img : advertisement/20191205/b031cbd9d9af8bf846cdd34f3eded3d4.jpg
             * a_url : 123
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

        public static class ImgBean {
            /**
             * a_img : advertisement/20191018/eda2f9a06080d468af2979ec9f108b1b.png
             * a_url : https://www.baidu.com
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
}
