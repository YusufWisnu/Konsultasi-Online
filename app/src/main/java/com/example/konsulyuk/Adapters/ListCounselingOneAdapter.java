package com.example.konsulyuk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konsulyuk.ChatActivity;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.LatestMessage;
import com.example.konsulyuk.Pasien.CounselingOneFragment;
import com.example.konsulyuk.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListCounselingOneAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ChatMessage> latestChat;
    public ListCounselingOneAdapter(FragmentActivity activity, ArrayList<ChatMessage> cm){
        this.context = activity;
        this.latestChat = cm;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListCounselingOneViewHolder(LayoutInflater.from(context).inflate(R.layout.list_counseling_one,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final ListCounselingOneViewHolder listCounselingOneViewHolder = (ListCounselingOneViewHolder) viewHolder;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date date = new Date(latestChat.get(i).getTimestamp() * 1000);

        listCounselingOneViewHolder.mItemText.setText(latestChat.get(i).getText());
        listCounselingOneViewHolder.mItemName.setText(latestChat.get(i).getName_psikolog());
        //listCounselingOneViewHolder.mCountChat.setText(String.valueOf(latestChat.get(i).getRead_count()));

        if(DateUtils.isToday(date.getTime() + DateUtils.DAY_IN_MILLIS)){
            listCounselingOneViewHolder.mTimeStamp.setText(sdf1.format(date));
        }else if(DateUtils.isToday(date.getTime() - DateUtils.DAY_IN_MILLIS)){
            listCounselingOneViewHolder.mTimeStamp.setText("Kemarin");
        }else{
            listCounselingOneViewHolder.mTimeStamp.setText(sdf.format(date));

        }


        listCounselingOneViewHolder.mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("PSI_ID", latestChat.get(i).getToId());
                intent.putExtra("PSI_NAME", latestChat.get(i).getName_psikolog());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return latestChat.size();
    }


    class ListCounselingOneViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mItemImage;
        TextView mItemName, mItemText, mTimeStamp, mCountChat;
        LinearLayout mLinear;
        public ListCounselingOneViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.imgview_list_counseling_one);
            mItemName = itemView.findViewById(R.id.txt_list_name_counseling_one);
            mItemText = itemView.findViewById(R.id.txt_list_isi_teks_counseling_one);
            mLinear = itemView.findViewById(R.id.linear_layout_list_counseling_one);
            mTimeStamp = itemView.findViewById(R.id.timestamp_latest_chat);
            mCountChat = itemView.findViewById(R.id.count_chat);


            context = itemView.getContext();
        }
    }
}
