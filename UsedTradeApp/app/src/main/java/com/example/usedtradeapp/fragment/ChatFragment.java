package com.example.usedtradeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.usedtradeapp.R;
import com.example.usedtradeapp.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        ListView listView = rootView.findViewById(R.id.listview_chat);

        listView.setOnItemClickListener((parent, view, position, id) -> {

        });
        return rootView;
    }
}