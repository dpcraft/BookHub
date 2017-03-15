package com.dpcraft.bookhub.DataClass;

import com.dpcraft.bookhub.NetModule.Server;

/**
 * Created by DPC on 2017/3/15.
 */
public class BookGetRequestInformation {

    /*
     *keyword长度不能超过30，默认为空
     *ISBN必须为13位的数字，默认为空
     *type必须在0~4之间，默认为-1，即全部type
     *sell的可选值为0，1，2。0为不区分是销售还是租借；1为只搜索售卖书籍；2为只搜索租借书籍。默认为0
     *order的可选值为0，1，2。0为默认按照bookid进行排序；1为按照价格从小到大排序；2为按照价格从大到小排序。默认为0
     *from为从第几个开始搜索，默认为第一个，即from==0。
     *length为搜索多少个，默认为5个，上限为20条数据，即最多可一次性返回20条数据
     * http://112.74.19.3:8080/BooksServer/bookupload.jsp
     */
    private String keyWord = "";
    private String ISBN = "";
    private String type = "";
    private String sell = "";
    private String order = "";
    private String from = "";
    private String length = "";

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String generateURL(){

        return Server.getServerAddress() +"book?"+"keyword=" + keyWord +
                "&type=" + type + "&isbn=" + ISBN + "&sell="+ sell + "&order=" +
                order + "&from=" + from+"&length="+length;
    }
}
