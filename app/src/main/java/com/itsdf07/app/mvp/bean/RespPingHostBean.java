package com.itsdf07.app.mvp.bean;

import java.util.List;

/**
 * @Description:
 * @Author itsdf07
 * @Date 2019/11/25
 */
public class RespPingHostBean extends BaseRespBean {

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * addresses : [{"prCreateDate":1574666635000,"prHost":"tts.baidu.com","prIccid":"5288412","prId":1,"prImei":"123456","prIp":"115.239.211.61","prNet":"4G","prNextHost":"tts.n.shifen.com","prOs":"8.0","prProvider":"Android"}]
         * createTime : 1574411524000
         * group : TTS
         * host : tts.baidu.com
         */

        private long createTime;
        private String group;
        private String host;
        private List<AddressesBean> addresses;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public List<AddressesBean> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<AddressesBean> addresses) {
            this.addresses = addresses;
        }

        public static class AddressesBean {
            /**
             * prCreateDate : 1574666635000
             * prHost : tts.baidu.com
             * prIccid : 5288412
             * prId : 1
             * prImei : 123456
             * prIp : 115.239.211.61
             * prNet : 4G
             * prNextHost : tts.n.shifen.com
             * prOs : 8.0
             * prProvider : Android
             */

            private long prCreateDate;
            private String prHost;
            private String prIccid;
            private int prId;
            private String prImei;
            private String prIp;
            private String prNet;
            private String prNextHost;
            private String prOs;
            private String prProvider;

            public long getPrCreateDate() {
                return prCreateDate;
            }

            public void setPrCreateDate(long prCreateDate) {
                this.prCreateDate = prCreateDate;
            }

            public String getPrHost() {
                return prHost;
            }

            public void setPrHost(String prHost) {
                this.prHost = prHost;
            }

            public String getPrIccid() {
                return prIccid;
            }

            public void setPrIccid(String prIccid) {
                this.prIccid = prIccid;
            }

            public int getPrId() {
                return prId;
            }

            public void setPrId(int prId) {
                this.prId = prId;
            }

            public String getPrImei() {
                return prImei;
            }

            public void setPrImei(String prImei) {
                this.prImei = prImei;
            }

            public String getPrIp() {
                return prIp;
            }

            public void setPrIp(String prIp) {
                this.prIp = prIp;
            }

            public String getPrNet() {
                return prNet;
            }

            public void setPrNet(String prNet) {
                this.prNet = prNet;
            }

            public String getPrNextHost() {
                return prNextHost;
            }

            public void setPrNextHost(String prNextHost) {
                this.prNextHost = prNextHost;
            }

            public String getPrOs() {
                return prOs;
            }

            public void setPrOs(String prOs) {
                this.prOs = prOs;
            }

            public String getPrProvider() {
                return prProvider;
            }

            public void setPrProvider(String prProvider) {
                this.prProvider = prProvider;
            }
        }
    }
}
//{"code":200,"datas":[{"addresses":[{"prCreateDate":1574666635000,"prHost":"tts.baidu.com","prIccid":"5288412","prId":1,"prImei":"123456","prIp":"115.239.211.61","prNet":"4G","prNextHost":"tts.n.shifen.com","prOs":"8.0","prProvider":"Android"}],"createTime":1574411524000,"group":"TTS","host":"tts.baidu.com"},{"addresses":[],"createTime":1574411524000,"group":"TTS","host":"tsn.baidu.com"},{"addresses":[],"createTime":1574411524000,"group":"TTS","host":"vse.baidu.com"},{"addresses":[],"createTime":1574411524000,"group":"TTS","host":"vop.baidu.com"},{"addresses":[],"createTime":1574411524000,"group":"TTS","host":"openapi.baidu.com"},{"addresses":[],"createTime":1574411524000,"group":"TTS","host":"yuyin.baidu.com"},{"addresses":[],"createTime":1574411524000,"group":"TTS","host":"yun.baidu.com"}],"desc":"success"}