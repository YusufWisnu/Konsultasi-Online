package com.example.konsulyuk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konsulyuk.ChatActivity;
import com.example.konsulyuk.Models.ChatMessage;
import com.example.konsulyuk.Models.Psikolog;
import com.example.konsulyuk.Pasien.InfoPsikologActivity;
import com.example.konsulyuk.Pasien.SelectPsikologActivity;
import com.example.konsulyuk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListSelectPsikologAdapter extends RecyclerView.Adapter<ListSelectPsikologAdapter.ListSelectPsikologViewHolder> {

    Context context;
    ArrayList<Psikolog> mListPsi;
    DatabaseReference reference;

    public ListSelectPsikologAdapter(Context context, ArrayList<Psikolog> mListPsi) {
        this.context = context;
        this.mListPsi = mListPsi;
    }

    @NonNull
    @Override
    public ListSelectPsikologViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListSelectPsikologAdapter.ListSelectPsikologViewHolder(LayoutInflater.from(context).inflate(R.layout.list_select_psikolog,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListSelectPsikologViewHolder listSelectPsikologViewHolder, final int i) {
        listSelectPsikologViewHolder.mItemName.setText(mListPsi.get(i).getName());
        listSelectPsikologViewHolder.mItemCategory.setText(mListPsi.get(i).getKategori());
        listSelectPsikologViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("PSI_ID", mListPsi.get(i).getPsi_id());
                    intent.putExtra("PSI_NAME", mListPsi.get(i).getName());
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Log.d("ID SI PSI", mListPsi.get(i).getPsi_id());
                ((SelectPsikologActivity) context).finish();
                    context.startActivity(intent);
            }
        });

        listSelectPsikologViewHolder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoPsikologActivity.class);
                intent.putExtra("PSI ID", mListPsi.get(i).getPsi_id());
                intent.putExtra("PSI NAME", mListPsi.get(i).getName());
                ((SelectPsikologActivity) context).finish();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListPsi.size();
    }

    class ListSelectPsikologViewHolder extends RecyclerView.ViewHolder{

        TextView mItemName, mItemCategory;
        Button btn_info;
        RelativeLayout linearLayout;
        public ListSelectPsikologViewHolder(@NonNull final View itemView) {
            super(itemView);
            reference = FirebaseDatabase.getInstance().getReference("Psikolog");
            mItemName = itemView.findViewById(R.id.txt_list_name_select_psikolog);
            mItemCategory = itemView.findViewById(R.id.txt_list_isi_teks_select_psikolog);
            linearLayout = itemView.findViewById(R.id.linear_layout_select_psikolog);
            btn_info = itemView.findViewById(R.id.btn_info_Psikolog);
            context = itemView.getContext();
        }
    }
}
