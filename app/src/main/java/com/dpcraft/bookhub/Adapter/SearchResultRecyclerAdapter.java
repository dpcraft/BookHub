package com.dpcraft.bookhub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.Activity.BookDetailsActivity;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DPC on 2017/2/11.
 */
public class SearchResultRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;
    private LayoutInflater mInflater;
    private int itemNum;
    private boolean hasMore = true;
    private Context mContext;
    private List<BookPreview> mBookList = new ArrayList<>();

    public SearchResultRecyclerAdapter(Context context){
        mContext = context;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position){
        if( position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;

        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    //item显示类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(viewType == ITEM_TYPE_FOOTER){
            FooterViewHolder footerViewHolder = new FooterViewHolder(mInflater.inflate(R.layout.item_sell_recycler_footer,parent,false));
            return footerViewHolder;

        } else {
            final ContentViewHolder contentViewHolder = new ContentViewHolder(mInflater.inflate(R.layout.item_sell_recycler,parent,false));
            contentViewHolder.bookListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //*获取书籍详细信息*
                    BookDetailsActivity.actionStart(mContext, contentViewHolder.getBookId() + "", "data2");


                }
            });
            return contentViewHolder;
        }
    }
    //数据绑定显示
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        //holder.item_tv.setText(mTitle[position]);
        if(holder instanceof FooterViewHolder){

            if(!hasMore ){
                Log.i("mBooklist.size=====",mBookList.size() + "");

                if(mBookList.size() == 0){
                    ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.GONE);
                    ((FooterViewHolder)holder).footerText.setText("没有搜索到相关书籍");
                }else {
                    ((FooterViewHolder) holder).footerProgressBar.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).footerText.setText("没有更多书籍");
                }

            }else if(!MyApplication.getInstance().isNetWorkConnected()){
                Log.i("网络未连接", "SellRecyclerAdapter ");
                ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.GONE);
                ((FooterViewHolder)holder).footerText.setText(R.string.network_connection_unavailable);
            }else{
                ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.VISIBLE);
                ((FooterViewHolder)holder).footerText.setText(R.string.request_more_list);
            }
        }
        else{
            BookPreview bookPreview = mBookList.get(position);
            //为书名作者等赋值
            String imageUrl = Server.getServerAddress() + "book/image?bookid=" + bookPreview.getId() + "&reduce=true";
            Glide.with(mContext).load(imageUrl).error(R.drawable.load_error).into(((ContentViewHolder)holder).bookPreviewCover);
            ((ContentViewHolder)holder).bookPreviewName.setText(bookPreview.getName());
            ((ContentViewHolder)holder).bookPreviewAuthor.setText(bookPreview.getAuthor());
            ((ContentViewHolder)holder).bookPreviewPublishingHouse.setText(bookPreview.getPublishHouse());
            ((ContentViewHolder)holder).setBookId(bookPreview.getId()); ;
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
        //return itemNum;
        if(mBookList == null){
            return 1;
        }else
        return mBookList.size() + 1;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{

        View bookListView;
        ImageView bookPreviewCover,bookPreviewDealType;
        int bookId;
        TextView bookPreviewName,bookPreviewAuthor,bookPreviewPublishingHouse,bookPreviewPrice;

        public ContentViewHolder(View view){
            super(view);
            bookListView = view;
            bookPreviewCover = (ImageView)view.findViewById(R.id.book_preview_cover);
            bookPreviewName = (TextView)view.findViewById(R.id.tv_book_preview_name);
            bookPreviewAuthor = (TextView)view.findViewById(R.id.tv_book_preview_author);
            bookPreviewPublishingHouse = (TextView)view.findViewById(R.id.tv_book_preview_publishing_house);
            bookPreviewDealType = (ImageView)view.findViewById(R.id.iv_book_preview_dealtype);
            bookPreviewPrice = (TextView)view.findViewById(R.id.tv_book_preview_price);

        }

        public void setBookId(int bookId){
            this.bookId = bookId;
        }

        public int getBookId(){
            return bookId;
        }

    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public TextView footerText;
        public ProgressBar footerProgressBar;
        public FooterViewHolder(View view){
            super(view);
            footerText = (TextView)view.findViewById(R.id.tv_loading_more);
            footerProgressBar = (ProgressBar)view.findViewById(R.id.pb_loading_more);

        }
    }

    public void addBookList(List<BookPreview> bookList){

        mBookList.addAll(bookList);
        if (bookList.size() < 5){
            hasMore = false;
        }else {
            hasMore = true;
        }


        Log.i("addBookList==========","done"+ hasMore);
        notifyDataSetChanged();

    }
    public void clearBookList(){

        mBookList.clear();
        hasMore = true;
    }

    public List<BookPreview> getmBookList() {

        return mBookList;
    }

    public void setmBookList(List<BookPreview> mBookList) {
        this.mBookList = mBookList;
        notifyDataSetChanged();
    }


}
