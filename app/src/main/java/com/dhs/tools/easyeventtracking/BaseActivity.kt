package com.dhs.tools.easyeventtracking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dhs.tools.easyeventtracking.databinding.ActivityMainBinding


/**
 * FileName: BaseActivity
 * Author: haosen
 * Date: 2025/3/8 17:29
 * Description:
 **/
abstract class BaseActivity<VB : ViewBinding>() : AppCompatActivity() {

    protected val binding by lazy { viewbinding() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    abstract fun viewbinding(): VB
}