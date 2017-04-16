package com.dpcraft.bookhub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dpcraft.bookhub.DataClass.BookDetails;
import com.dpcraft.bookhub.DataClass.BookDetailsIncludeUser;
import com.dpcraft.bookhub.DataClass.UserInfo;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.Dialog;

/**
 * Created by DPC on 2017/3/14.
 */
public class MyIntentionBookDetailsAdapter extends RecyclerView.Adapter<MyIntentionBookDetailsAdapter.ViewHolder> {

    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;
    private  int itemNum ;
    private BookDetails mBookDetails;
    private BookDetailsIncludeUser mBookDetailsIncludeUser;
    private UserInfo mUserInfo;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookInfoItem;
        public ViewHolder(View view){
            super(view);
            bookInfoItem = (TextView)view.findViewById(R.id.tv_bookInfo_item);
        }
    }

    public MyIntentionBookDetailsAdapter(BookDetailsIncludeUser bookDetailsIncludeUser , Context context){
        mContext = context;
        mBookDetailsIncludeUser = bookDetailsIncludeUser;
        mBookDetails = bookDetailsIncludeUser.getBook();
        mUserInfo = bookDetailsIncludeUser.getUser();
        if(bookDetailsIncludeUser.getBook().getSell()){
            itemNum = 9;
        }
        else {
            itemNum = 10;
        }
    }


    @Override
    public int getItemViewType(int position){
        if( position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;

        }else{
            return ITEM_TYPE_CONTENT;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookdetails_recycler,parent,false);
        ViewHolder holder = new ViewHolder(view);
        if(viewType == ITEM_TYPE_FOOTER) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mUserInfo != null) {
                        String dialogMessage = "昵称：" + mUserInfo.getNickName() + "\n" + "手机：" + mUserInfo.getPhoneNumber();
                        Dialog.showDealerMessageDialog("卖家信息",dialogMessage,mContext);
                    }
                    else{
                        Dialog.showDialog("暂时没有用户有意向" , "" ,mContext);
                    }


                }
            });
        }
        return holder;
    }
    @Override
     public void onBindViewHolder(ViewHolder holder,int position){
        showBookInfo(holder,position);
    }
    @Override
    public int getItemCount(){
        return itemNum;
    }
    protected void showBookInfo(ViewHolder holder,int position){
        String str;
        if(mBookDetails.getSell()) {
            switch (position) {
                case 0:
                    str = "价格(售): ￥" + mBookDetails.getPrice();
                    break;
                case 1:
                    str = "类别: " + getBookTypeName(mBookDetails.getType());
                    break;
                case 2:
                    str = "出版社: " + mBookDetails.getPublishHouse();
                    break;
                case 3:
                    str = "作者：" + mBookDetails.getAuthor();
                    break;
                case 4:
                    str = "出版日期: " + mBookDetails.getPublishDate();
                    break;
                case 5:
                    str = "原价: " + mBookDetails.getOriginPrice() + "元";
                    break;
                case 6:
                    str = "ISBN: " + mBookDetails.getISBN();
                    break;
                case 7:
                    str = "介绍：\n" + mBookDetails.getIntroduction();
                    holder.bookInfoItem.setSingleLine(false);
                    break;
                case 8:
                    if(mUserInfo == null){
                    str = "暂时没有用户有意向";
                }else {
                        str = "联系对方";
                    }
                    holder.bookInfoItem.setGravity(Gravity.CENTER);
                    holder.bookInfoItem.setBackgroundColor(mContext.getResources().getColor(R.color.red_900));
                    holder.bookInfoItem.setTextColor(mContext.getResources().getColor(R.color.white));
                    break;
                default:
                    str = "N/A";
                    break;
            }
        }
        else {
            switch(position){
                case 0:
                    str = "价格(租): ￥" + mBookDetails.getPrice()+" 元/天";
                    break;
                case 1:
                    str = "押金:" + mBookDetails.getDeposit() + "元";
                    break;
                case 2:
                    str = "类别: " + getBookTypeName(mBookDetails.getType());
                    break;
                case 3:
                    str = "出版社: " + mBookDetails.getPublishHouse();
                    break;
                case 4:
                    str = "作者：" + mBookDetails.getAuthor();
                    break;
                case 5:
                    str = "出版日期: " + mBookDetails.getPublishDate();
                    break;
                case 6:
                    str = "原价: " + mBookDetails.getOriginPrice() + "元";
                    break;
                case 7:
                    str = "ISBN: " + mBookDetails.getISBN();
                    break;
                case 8:
                    str = "介绍: \n" + mBookDetails.getIntroduction();
                    holder.bookInfoItem.setSingleLine(false);
                    break;
                case 9:
                        str = "联系对方";
                    holder.bookInfoItem.setGravity(Gravity.CENTER);
                    holder.bookInfoItem.setBackgroundColor(mContext.getResources().getColor(R.color.red_900));
                    holder.bookInfoItem.setTextColor(mContext.getResources().getColor(R.color.white));
                    break;
                default:str = "N/A";
                    break;
            }
        }
        holder.bookInfoItem.setText(str);

    }
    private String getBookTypeName(String indexStr){
        int index = Integer.parseInt(indexStr);
        String[] bookTypeNames = mContext.getResources().getStringArray(R.array.array_book_type);
        String bookTypeName;
        if(index == 0){
            bookTypeName = bookTypeNames[9];
        }else {
            bookTypeName = bookTypeNames[index];
        }
        return bookTypeName;

    }
}
