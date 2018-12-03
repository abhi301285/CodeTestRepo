package com.test.code.codetestapp.network;

import android.util.Log;

import com.test.code.codetestapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OKHttpHelper {

    private static Retrofit retrofit = null;
    private static String mBaseUrl = "";


    public static APIService getAPIService(String baseUrl) {
        return getClient(baseUrl).create(APIService.class);
    }

    public static Retrofit getClient(String baseUrl) {

        if (retrofit == null || !baseUrl.equalsIgnoreCase(mBaseUrl)) {
            Log.e("RetrofitClient", "baseUrl  === " + baseUrl);
            mBaseUrl = baseUrl;

            //TODO for logging purpose
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            //TODO: need to check once VPN is connected with this app
            //OkHttpClient httpClient = createOkHttpClient();

            //BYPASSING HTTPS
            // OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            // OkHttpClient httpClient = configureToIgnoreCertificate(httpClientBuilder).build();

            // add logging as last interceptor
            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(logging);  // <-- this is the important line!
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())  //to add logging code
                    .build();

        }
        return retrofit;
    }

}
