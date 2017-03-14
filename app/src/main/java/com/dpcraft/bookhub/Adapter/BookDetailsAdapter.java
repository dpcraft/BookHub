package com.dpcraft.bookhub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dpcraft.bookhub.DataClass.BookDetails;
import com.dpcraft.bookhub.R;

/**
 * Created by DPC on 2017/3/14.
 */
public class BookDetailsAdapter extends RecyclerView.Adapter<BookDetailsAdapter.ViewHolder> {

    private  int itemNum ;
    private BookDetails mBookDetails;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookInfoItem;
        public ViewHolder(View view){
            super(view);
            bookInfoItem = (TextView)view.findViewById(R.id.tv_bookInfo_item);
        }
    }

    public BookDetailsAdapter(BookDetails bookDetails, Context context){
        mContext = context;
        mBookDetails = bookDetails;
        if(bookDetails.getSell()){
            itemNum = 8;
        }
        else {
            itemNum = 9;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookdetails_recycler,parent,false);
        ViewHolder holder = new ViewHolder(view);
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
                    str = "类别: " + mBookDetails.getType();
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
                    str = "原价: " + mBookDetails.getOriginPrice();
                    break;
                case 6:
                    str = "ISBN: " + mBookDetails.getISBN();
                    break;
                case 7:
                    str = "联系卖家";
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
                    str = "可租至:" + mBookDetails.getEndtime();
                    break;
                case 2:
                    str = "类别: " + mBookDetails.getType();
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
                    str = "原价: " + mBookDetails.getOriginPrice();
                    break;
                case 7:
                    str = "ISBN: " + mBookDetails.getISBN();
                    break;
                case 8:
                    str = "联系卖家";
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
}
