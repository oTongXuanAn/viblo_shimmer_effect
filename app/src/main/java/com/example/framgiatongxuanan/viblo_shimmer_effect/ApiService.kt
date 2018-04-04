package com.example.framgiatongxuanan.viblo_shimmer_effect

import com.example.framgiatongxuanan.viblo_shimmer_effect.data.AndroidVersion
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by FRAMGIA\tong.xuan.an on 08/01/2018.
 */
interface ApiService {
    @GET("android/jsonarray/")
    fun getAndroidVersion(): Observable<List<AndroidVersion>>
}