package com.watercanedelivery.app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.watercanedelivery.app.BaseActivity
import com.watercanedelivery.app.ExtensionClass
import com.watercanedelivery.app.databinding.ActivitySplashBinding
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
        @Inject set


    private val SPLASH_TIME_OUT = 2000L
    private var delayHandler: Handler? = null
    var activitySplashBinding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        if (delayHandler == null) {
            delayHandler = Handler(Looper.getMainLooper()!!)
        }
        setContentView(activitySplashBinding?.root)
        if (sharedPreferenceUtil.getData(Constant.USER_ID) == null
        ) {
            delayHandler!!.postDelayed({
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()

            }, SPLASH_TIME_OUT)
        } else {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activitySplashBinding = null
    }
}