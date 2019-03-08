package com.test.news.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.news.R;
import com.test.news.databinding.FragmentNewsDetailsBinding;
import com.test.news.di.Injectable;
import com.test.news.viewmodel.NewsViewModel;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


public class NewsFragment extends Fragment implements Injectable {

    private static final String NEWS_URL = "news_url";

    private FragmentNewsDetailsBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false);
        return (View) binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NewsViewModel viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsViewModel.class);

        viewModel.setNewsUrl(getArguments().getString(NEWS_URL));

        binding.setNewsViewModel(viewModel);

    }

    public static NewsFragment forProject(String newsUrl) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_URL, newsUrl);
        fragment.setArguments(args);

        return fragment;
    }
}
