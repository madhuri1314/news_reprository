package com.test.news.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.news.R;
import com.test.news.databinding.NewsListItemBinding;
import com.test.news.service.model.Article;
import com.test.news.view.callback.NewsClickCallback;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ProjectViewHolder> {

    List<? extends Article> newsList;

    @Nullable
    private final NewsClickCallback newsClickCallback;

    public NewsAdapter(@Nullable NewsClickCallback newsClickCallback) {
        this.newsClickCallback = newsClickCallback;
    }

    public void setNewsList(final List<? extends Article> newsList) {
        if (this.newsList == null) {
            this.newsList = newsList;
            notifyItemRangeInserted(0, newsList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return NewsAdapter.this.newsList.size();
                }

                @Override
                public int getNewListSize() {
                    return newsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return NewsAdapter.this.newsList.get(oldItemPosition).getId() == newsList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    try {
                        Article project = newsList.get(newItemPosition);
                        Article old = newsList.get(oldItemPosition);
                        return project.getId() == old.getId();
                    }catch (Exception e){
                        e.printStackTrace();
                        return true;
                    }
                }
            });
            this.newsList = newsList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.news_list_item,
                        parent, false);

        binding.setCallback(newsClickCallback);

        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.binding.setNews(newsList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {

        final NewsListItemBinding binding;

        public ProjectViewHolder(NewsListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
