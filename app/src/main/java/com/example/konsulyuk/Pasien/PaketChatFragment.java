package com.example.konsulyuk.Pasien;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.konsulyuk.R;

public class PaketChatFragment extends Fragment {

    Button btn_kenalan, btn_lebihDekat, btn_mantul;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paket_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_kenalan = getView().findViewById(R.id.btn_paket_kenalan_chat);
        btn_lebihDekat = getView().findViewById(R.id.btn_paket_lebih_dekat_chat);
        btn_mantul = getView().findViewById(R.id.btn_paket_mantul_chat);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_kenalan = getView().findViewById(R.id.btn_paket_kenalan_chat);


        btn_kenalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SelectPsikologActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        });
    }
}
