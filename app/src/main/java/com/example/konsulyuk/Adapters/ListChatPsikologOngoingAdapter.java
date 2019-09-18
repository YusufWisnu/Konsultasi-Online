package com.example.konsulyuk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.konsulyuk.ChatActivity;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.User;
import com.example.konsulyuk.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListChatPsikologOngoingAdapter extends RecyclerView.Adapter<ListChatPsikologOngoingAdapter.MyViewHolder> {

    Context context;
    ArrayList<ChatMessage> mUserList;
    DatabaseReference reference;

    public ListChatPsikologOngoingAdapter(Context context, ArrayList<ChatMessage> mUserList) {
        this.context = context;
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_chat_psikolog_ongoing, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        //myViewHolder.tv_name.setText(mUserList.get(i).getName_pasien());
        myViewHolder.tv_name.setText(mUserList.get(i).getName_psikolog());
        myViewHolder.tv_text.setText(mUserList.get(i).getText());
        //myViewHolder.tv_read_count.setText(mUserList.get(i).getRead_count());
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("PSI_ID", mUserList.get(i).getFromId());
                intent.putExtra("PSI_NAME", mUserList.get(i).getName_psikolog());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mImage;
        TextView tv_name, tv_text, tv_timestamp, tv_read_count;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_chat_psikolog_ongoing_pasien_name);
            tv_text = itemView.findViewById(R.id.tv_chat_psikolog_ongoing_pasien_text);
            mImage = itemView.findViewById(R.id.image_chat_psikolog_ongoing);
            linearLayout = itemView.findViewById(R.id.linear_layout_chat_psikolog_ongoing);
            tv_timestamp = itemView.findViewById(R.id.timestamp_latest_chat_psikolog);
            tv_read_count = itemView.findViewById(R.id.count_chat_psikolog);

        }
    }
}
