package com.dpcraft.bookhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpcraft.bookhub.DataClass.BookPreview;

import java.util.List;

/**
 * Created by DPC on 2017/2/11.
 */
public class SellRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;

    private LayoutInflater mInflater;
    private int itemNum;
    private Context mContext;
    private List<BookPreview> mBookList;

    public SellRecyclerAdapter(Context context,List<BookPreview> bookList){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        mBookList = bookList;
        itemNum = bookList.size() + 1;
    }
    //判断当前Item是否为HeadView
    public boolean isHeadView(int position){
        return position == 0;
    }
    @Override
    public int getItemViewType(int position){
        if(isHeadView(position)){
            return ITEM_TYPE_HEADER;
        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    //item显示类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(viewType == ITEM_TYPE_HEADER){
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(mInflater.inflate(R.layout.item_sell_recycler_header,parent,false));

            return headerViewHolder;
        }else {
            ContentViewHolder contentViewHolder = new ContentViewHolder(mInflater.inflate(R.layout.item_sell_recycler,parent,false));
            contentViewHolder.bookListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //*获取书籍详细信息*


                    BookDetailsActivity.actionStart(mContext, "data1", "data2");
                }
            });
            return contentViewHolder;
        }
    }
    //数据绑定显示
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        //holder.item_tv.setText(mTitle[position]);
        if (holder instanceof HeaderViewHolder) {

        }
        else{
            BookPreview bookPreview = mBookList.get(position - 1);

            //为书名作者等赋值
            ((ContentViewHolder)holder).bookPreviewName.setText(bookPreview.getName());
            ((ContentViewHolder)holder).bookPreviewAuthor.setText(bookPreview.getAuthor());
            ((ContentViewHolder)holder).bookPreviewPublishingHouse.setText(bookPreview.getPublishHouse());
            if(bookPreview.getSell()) {
                ((ContentViewHolder) holder).bookPreviewDealType.setImageResource(R.drawable.ic_sell);
            }else {
                ((ContentViewHolder) holder).bookPreviewDealType.setImageResource(R.drawable.ic_rent);

            }
            ((ContentViewHolder) holder).bookPreviewPrice.setText(bookPreview.getPrice());
            //((ContentViewHolder) holder).textView.setText(texts[position - mHeaderCount]);
             }
    }

    //每次加载的item数
    @Override
    public int getItemCount(){
        return itemNum;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{

        View bookListView;
        ImageView bookPreviewImage,bookPreviewDealType;
        TextView bookPreviewName,bookPreviewAuthor,bookPreviewPublishingHouse,bookPreviewPrice;
        public ContentViewHolder(View view){
            super(view);
            bookListView = view;
            bookPreviewImage = (ImageView)view.findViewById(R.id.book_preview_image);
            bookPreviewName = (TextView)view.findViewById(R.id.tv_book_preview_name);
            bookPreviewAuthor = (TextView)view.findViewById(R.id.tv_book_preview_author);
            bookPreviewPublishingHouse = (TextView)view.findViewById(R.id.tv_book_preview_publishing_house);
            bookPreviewDealType = (ImageView)view.findViewById(R.id.iv_book_preview_dealtype);
            bookPreviewPrice = (TextView)view.findViewById(R.id.tv_book_preview_price);

        }
    }
    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        // public TextView item_tv;
        public HeaderViewHolder(View view){
            super(view);
            // item_tv =(TextView)view.findViewById(R.id.item_tv);
        }
    }

}
