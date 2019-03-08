package com.test.news.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.news.R;
import com.test.news.databinding.FragmentNewsListBinding;
import com.test.news.di.Injectable;
import com.test.news.view.adapter.NewsAdapter;
import com.test.news.view.callback.NewsClickCallback;
import com.test.news.viewmodel.NewsListViewModel;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class NewsListFragment extends Fragment implements Injectable {

    public static final String TAG = NewsListFragment.class.getName();

    private NewsAdapter newsAdapter;

    private FragmentNewsListBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false);
        newsAdapter = new NewsAdapter(newsClickCallback);
        binding.rvNewsList.setAdapter(newsAdapter);
        binding.setIsLoading(true);
        return (View) binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NewsListViewModel viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(NewsListViewModel.class);
        binding.setViewModel(viewModel);
        observeViewModel(viewModel);
    }
    private final NewsClickCallback newsClickCallback = article -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((NewsActivity) getActivity()).show(article.getUrl());
        }
    };

    private void observeViewModel(NewsListViewModel viewModel) {
        viewModel.getNewsListObservable().observe(this, list -> {
            if (list != null) {
                binding.setIsLoading(false);
                newsAdapter.setNewsList(list);
            }
        });
    }
}
