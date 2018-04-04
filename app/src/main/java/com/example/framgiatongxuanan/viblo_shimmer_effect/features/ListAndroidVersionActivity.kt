package com.example.framgiatongxuanan.viblo_shimmer_effect.features

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.framgiatongxuanan.viblo_shimmer_effect.ApiService
import com.example.framgiatongxuanan.viblo_shimmer_effect.R
import com.example.framgiatongxuanan.viblo_shimmer_effect.adapter.AndroidVersionAdapter
import com.example.framgiatongxuanan.viblo_shimmer_effect.data.AndroidVersion
import com.example.framgiatongxuanan.viblokolin.features.androidversion.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import com.facebook.shimmer.ShimmerFrameLayout



/**
 * Created by FRAMGIA\tong.xuan.an on 08/01/2018.
 */
class ListAndroidVersionActivity : AppCompatActivity() {
    private val TAG = ListAndroidVersionActivity::class.java.simpleName
    private var mAdapter: AndroidVersionAdapter? = null
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        requestAndroidVersion()

    }

    override fun onResume() {
        super.onResume()
        shimmer_view_container.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        shimmer_view_container.stopShimmerAnimation()
    }

    // khởi tao recyclerview
    private fun initRecyclerView() {
        rv_android_list.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_android_list.layoutManager = layoutManager
    }

    // request data tu server
    private fun requestAndroidVersion() {
        Repository.createService(ApiService::class.java).getAndroidVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        //cú pháp của rxjava trong kotlin
                        { result ->
                            //request thành công
                            handleSuccessAndroidVersion(result)
                        },
                        { error ->
                            //request thất bai
                            handlerErrorAndroidVersion(error)
                        }
                )
    }

    //Xử lí dữ liệu khi request thành công
    private fun handleSuccessAndroidVersion(result: List<AndroidVersion>) {
        SystemClock.sleep(4000);
        mAdapter = AndroidVersionAdapter(result)
        rv_android_list.adapter = mAdapter
        shimmer_view_container.stopShimmerAnimation()
        shimmer_view_container.visibility=View.GONE

    }

    //Xử lí dữ lieu khi request thất bại
    private fun handlerErrorAndroidVersion(error: Throwable) {
        Log.e(TAG, "handlerErrorAndroidVersion: ${error.localizedMessage}")
        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}