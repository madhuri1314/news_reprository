package com.test.news.viewmodel;

import android.app.Application;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.news.service.repository.Repository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class NewsViewModel extends AndroidViewModel {
    private static final String TAG = NewsViewModel.class.getName();

    public ObservableField<String> newsUrl = new ObservableField<>();
    public ObservableField<Boolean> isloading = new ObservableField<>();

    @Inject
    public NewsViewModel(@NonNull Repository repository, @NonNull Application application) {
        super(application);
        isloading.set(true);
    }

    public void setNewsUrl(String NewsUrl) {
        newsUrl.set(NewsUrl);
    }

    private class Client extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {
            super.onReceivedError(view, request, error);
            isloading.set(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isloading.set(false);
        }
    }

    public WebViewClient getWebViewClient() {
        return new Client();
    }

}
