package com.example.konsulyuk.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.Psikolog;
import com.example.konsulyuk.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.konsulyuk.Models.ChatMessage.FROM_ROW;
import static com.example.konsulyuk.Models.ChatMessage.TO_ROW;

public class ListChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChatMessage> mListChat;
    Context context;
    Date date;
    SimpleDateFormat sfd;
    private int chat_read = 0;

    public ListChatAdapter(ArrayList<ChatMessage> mListChat, Context context) {
        this.mListChat = mListChat;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mListChat.get(position).getView_type()){
            case 0:
                return FROM_ROW;
            case 1:
                return TO_ROW;
        }
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case FROM_ROW:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_from_row, viewGroup, false);
                return new ListChatFromRowViewHolder(view);
            case TO_ROW:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_to_row, viewGroup, false);
                return new ListChatToRowViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        date = new Date(mListChat.get(i).getTimestamp() * 1000);
        sfd = new SimpleDateFormat("HH:mm");
        switch (mListChat.get(i).getView_type()){
            case FROM_ROW:
                ((ListChatFromRowViewHolder) viewHolder).tv_text_from_row.setText(mListChat.get(i).getText());
                ((ListChatFromRowViewHolder) viewHolder).tv_time_from_row.setText(sfd.format(date));
//                if(!((ListChatFromRowViewHolder) viewHolder).tv_read_chat.getText().equals(0)){
//                    ((ListChatFromRowViewHolder) viewHolder).tv_read_chat.setVisibility(View.GONE);
//                }else{
//                    ((ListChatFromRowViewHolder) viewHolder).tv_read_chat.setVisibility(View.VISIBLE);
//                }

                break;
            case TO_ROW:
                ((ListChatToRowViewHolder) viewHolder).tv_text_to_row.setText(mListChat.get(i).getText());
                ((ListChatToRowViewHolder) viewHolder).tv_time_to_row.setText(sfd.format(date));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mListChat.size();
    }

    class ListChatFromRowViewHolder extends RecyclerView.ViewHolder{
        TextView tv_text_from_row, tv_time_from_row, tv_read_chat;
        public ListChatFromRowViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text_from_row = itemView.findViewById(R.id.textview_chat_from_row);
            tv_time_from_row = itemView.findViewById(R.id.timestamp_chat_from_row);
            tv_read_chat = itemView.findViewById(R.id.tv_read_chat);
        }
    }

    class ListChatToRowViewHolder extends RecyclerView.ViewHolder{
        TextView tv_text_to_row, tv_time_to_row;
        CircleImageView imageView;
        public ListChatToRowViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text_to_row = itemView.findViewById(R.id.textview_chat_to_row);
            imageView = itemView.findViewById(R.id.image_chat_to_row);
            tv_time_to_row = itemView.findViewById(R.id.timestamp_chat_to_row);
        }
    }

}
