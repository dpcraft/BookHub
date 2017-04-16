package com.dpcraft.bookhub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dpcraft.bookhub.Activity.BookDetailsActivity;
import com.dpcraft.bookhub.Activity.ClassificationActivity;
import com.dpcraft.bookhub.Activity.MyUploadActivity;
import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DPC on 2017/2/11.
 */
public class SellRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;

    private LayoutInflater mInflater;
    private int itemNum = 0;
    private boolean hasMore = true;
    private Context mContext;
    private List<BookPreview> mBookList = new ArrayList<>();

    public SellRecyclerAdapter(Context context,List<BookPreview> bookList){
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        mBookList = bookList;
        if(bookList != null) {
            itemNum = bookList.size() + 2;
        }
    }
    public SellRecyclerAdapter(Context context){
        mContext = context;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return ITEM_TYPE_HEADER;
        }
        else if( position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;

        }else{
            return ITEM_TYPE_CONTENT;
        }
    }

    //item显示类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(viewType == ITEM_TYPE_HEADER){
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(mInflater.inflate(R.layout.item_sell_recycler_header,parent,false));


            headerViewHolder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"1" ,"");

                }
            });
            headerViewHolder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"2" ,"");

                }
            });
            headerViewHolder.button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"3" ,"");

                }
            });
            headerViewHolder.button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"4" ,"");

                }
            });
            headerViewHolder.button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"5" ,"");

                }
            });
            headerViewHolder.button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"6" ,"");

                }
            });
            headerViewHolder.button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"7" ,"");

                }
            });
            headerViewHolder.button8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassificationActivity.actionStart(mContext ,"8" ,"");

                }
            });


            return headerViewHolder;
        } else if(viewType == ITEM_TYPE_FOOTER){
            FooterViewHolder footerViewHolder = new FooterViewHolder(mInflater.inflate(R.layout.item_sell_recycler_footer,parent,false));
            return footerViewHolder;

        } else {
            final ContentViewHolder contentViewHolder = new ContentViewHolder(mInflater.inflate(R.layout.item_sell_recycler,parent,false));
            contentViewHolder.bookListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //*获取书籍详细信息*



                        BookDetailsActivity.actionStart(mContext, contentViewHolder.getBookId() + "", "data2");




                    Log.i("previewBookId=======",contentViewHolder.getBookId() + "");

                }
            });
            return contentViewHolder;
        }
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_1:
                Toast.makeText(mContext,"Click btn 1 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_2:
                Toast.makeText(mContext,"Click btn 2 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_3:
                Toast.makeText(mContext,"Click btn 3 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_4:
                Toast.makeText(mContext,"Click btn 4 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_5:
                Toast.makeText(mContext,"Click btn 5 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_6:
                Toast.makeText(mContext,"Click btn 6 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_7:
                Toast.makeText(mContext,"Click btn 7 ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_8:
                Toast.makeText(mContext,"Click btn 8 ",Toast.LENGTH_SHORT).show();
                break;

        }
    }
    //数据绑定显示
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        //holder.item_tv.setText(mTitle[position]);
        if (holder instanceof HeaderViewHolder) {

        }else if(holder instanceof FooterViewHolder){
            if(!hasMore){
                ((FooterViewHolder)holder).footerProgressBar.setVisibility(View.GONE);
                ((FooterViewHolder)holder).footerText.setText("没有更多书籍");
                //((FooterViewHolder)holder).footerText.setTextColor(R.color.red_900);

                //mContext.
            }

        }
        else{
            BookPreview bookPreview = mBookList.get(position - 1);
            //为书名作者等赋值
            String imageUrl = Server.getServerAddress() + "book/image?bookid=" + bookPreview.getId() + "&reduce=true";
            Glide.with(mContext).load(imageUrl).error(R.drawable.load_error).into(((ContentViewHolder)holder).bookPreviewCover);
            Log.i("imageUrl============",imageUrl);
            ((ContentViewHolder)holder).bookPreviewName.setText(bookPreview.getName());
            ((ContentViewHolder)holder).bookPreviewAuthor.setText(bookPreview.getAuthor());
            ((ContentViewHolder)holder).bookPreviewPublishingHouse.setText(bookPreview.getPublishHouse());
            ((ContentViewHolder)holder).setBookId(bookPreview.getId());
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
            return 2;
        }else
        return mBookList.size() + 2;
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

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public Button button1 , button2 , button3 , button4 ,
                button5 , button6 ,button7 , button8 ;
        public HeaderViewHolder(View view){
            super(view);
            button1 = (Button)view.findViewById(R.id.btn_1);
            button2 = (Button)view.findViewById(R.id.btn_2);
            button3 = (Button)view.findViewById(R.id.btn_3);
            button4 = (Button)view.findViewById(R.id.btn_4);
            button5 = (Button)view.findViewById(R.id.btn_5);
            button6 = (Button)view.findViewById(R.id.btn_6);
            button7 = (Button)view.findViewById(R.id.btn_7);
            button8 = (Button)view.findViewById(R.id.btn_8);
            // item_tv =(TextView)view.findViewById(R.id.item_tv);
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
        if (bookList.size() == 0){
            hasMore = false;
        }


        Log.i("addBookList==========","done"+ hasMore);
        notifyDataSetChanged();

    }
    public void clearBooklist(){
        mBookList.clear();
    }
    public List<BookPreview> getmBookList() {
        return mBookList;
    }

    public void setmBookList(List<BookPreview> mBookList) {
        this.mBookList = mBookList;
        notifyDataSetChanged();
    }


}
