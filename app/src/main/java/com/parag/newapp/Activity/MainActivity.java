package com.parag.newapp.Activity;

import static com.parag.newapp.Network.APIS.BASE_URL;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parag.newapp.Network.APIS;
import com.parag.newapp.R;
import com.parag.newapp.Model.NewListModel;
import com.parag.newapp.Adapter.NewsListAdapter;
import com.parag.newapp.Utils.ProgressUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Retrofit retrofit;
    NewsListAdapter adapter;
    ProgressUtil loader;
    static ConnectivityManager cm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.noti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        recyclerView = findViewById(R.id.rvNewsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsListAdapter(this);
        recyclerView.setAdapter(adapter);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        loader = new ProgressUtil(this);
        loader.show();


        /*Caching*/
        File httpCacheDirectory = new File(getCacheDir(),  "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        /*Caching*/

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(OFFLINE_INTERCEPTOR)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        callEndpoints();
    }

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");

        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 10)
                    .build();
            } else {
                return originalResponse;
            }
    };

    private static final Interceptor OFFLINE_INTERCEPTOR = chain -> {
        Request request = chain.request();

        if (!isOnline()) {

            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return chain.proceed(request);
    };

    public static boolean isOnline() {
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

        private void callEndpoints() {

        APIS apis = retrofit.create(APIS.class);

        //Single call
        /*Observable<Crypto> cryptoObservable = cryptocurrencyService.getCoinData("btc");
        cryptoObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).map(result -> result.ticker).subscribe(this::handleResults, this::handleError);*/


        Observable<NewListModel> newsObservable = apis.getNewsListData("us", "59978576976e4780bbf2952c697e1178");
        newsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(this::handleResults, this::handleError);


//        Observable<List<Crypto.Market>> btcObservable = apis.getNewsListData("us", "59978576976e4780bbf2952c697e1178");
//                .map(result -> Observable.fromIterable(result.ticker.markets))
//                .flatMap(x -> x).filter(y -> {
//                    y.coinName = "btc";
//                    return true;
//                }).toList().toObservable();
//
//        Observable<List<Crypto.Market>> ethObservable = apis.getNewsListData("us", "59978576976e4780bbf2952c697e1178");
//                .map(result -> Observable.fromIterable(result.ticker.markets))
//                .flatMap(x -> x).filter(y -> {
//                    y.coinName = "eth";
//                    return true;
//                }).toList().toObservable();
//
//        Observable.merge(btcObservable, ethObservable)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::handleResults, this::handleError);



    }


    private void handleResults(NewListModel articles) {
        loader.dismiss();
        if (articles.getArticles() != null && articles.getArticles().size() != 0) {
            adapter.setData(articles.getArticles());
        } else {
            Toast.makeText(this, "Opps - No New Found",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        System.out.println("t Error "+t);
        Toast.makeText(this, "Error --- "+t, Toast.LENGTH_LONG).show();
    }


}
