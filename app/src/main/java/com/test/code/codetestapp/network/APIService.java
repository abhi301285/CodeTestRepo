package com.test.code.codetestapp.network;

import com.test.code.codetestapp.data.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET(NetworkConstants.WS_GET_ALBUMS)
    Call<List<Album>> getAlbumList();

}
