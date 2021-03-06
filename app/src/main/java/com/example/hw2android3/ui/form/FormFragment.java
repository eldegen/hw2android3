package com.example.hw2android3.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw2android3.App;
import com.example.hw2android3.R;
import com.example.hw2android3.data.models.Post;
import com.example.hw2android3.databinding.FragmentFormBinding;
import com.example.hw2android3.databinding.FragmentPostBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private static final int groupId = 38;
    private static final int userId = 1;
    private FragmentFormBinding binding;

    public FormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() == null) {
            binding.btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = binding.etTitle.getText().toString();
                    String content = binding.etContent.getText().toString();
                    Post post = new Post(title, content, userId, groupId);
                    App.api.createPost(post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                requireActivity().onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }
            });
        } else {
            try {
                binding.etTitle.setText(getArguments().getString("getTitle"));
                binding.etContent.setText(getArguments().getString("getContent"));
            } catch (NullPointerException e) {

            }

            binding.btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = binding.etTitle.getText().toString();
                    String content = binding.etContent.getText().toString();
                    int id = getArguments().getInt("getId");
                    int groupId = getArguments().getInt("getGroup");
                    int userId = getArguments().getInt("getUser") ;

                    Post post = new Post(title, content, groupId, userId);
                    App.api.putPost(id, title, content, groupId, userId).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            Toast.makeText(getContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Toast.makeText(getContext(), "Failed to save changes! (" + t + ")", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}