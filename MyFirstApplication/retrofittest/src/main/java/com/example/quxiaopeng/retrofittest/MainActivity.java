package com.example.quxiaopeng.retrofittest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

    @Bind(R.id.btn_link)
    Button clickBtn;
    @Bind(R.id.tv_result)
    TextView resultTv;
    SmallLoadingDialog loadingDialog;

    public static final String TAG = "quxiaopeng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadingDialog = new SmallLoadingDialog(this);
        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getMovieInCommon();
                getMovieInRxJava();
            }
        });
    }

//    private void getMovieInCommon() {
//        String baseUrl = "https://api.douban.com/v2/movie/";
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
//
//        MovieService movieService = retrofit.create(MovieService.class);
//        Call<MovieModel> call = movieService.getTopMovie(0, 10);
//        call.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                String s= response.body().toString();
//                resultTv.setText(s);
//                Log.i(TAG, "onResponse: " + s);
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//                resultTv.setText(t.getMessage());
//            }
//        });
//    }

    private void getMovieInRxJava() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .map(new Func1<HttpResultModel<List<MovieModel>>, List<MovieModel>>() {
                    @Override
                    public List<MovieModel> call(HttpResultModel<List<MovieModel>> listHttpResultModel) {
                        return listHttpResultModel.subjects;
                    }
                })
                .flatMap(new Func1<List<MovieModel>, Observable<MovieModel>>() {
                    @Override
                    public Observable<MovieModel> call(List<MovieModel> movieModels) {
                        return Observable.from(movieModels);
                    }
                }).doOnSubscribe(new Action0() {
                     @Override
                      public void call() {
                          loadingDialog.show();
                       }
                 })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieModel>() {
                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Get Top Movie Complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultTv.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieModel movieModel) {
                        resultTv.setText(resultTv.getText() + "\n" + movieModel.toString());
                    }
                });
    }
}
