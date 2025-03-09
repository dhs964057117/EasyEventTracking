package com.dhs.tools.easyeventtracking

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dhs.tools.easyeventtracking.databinding.ActivityMainBinding
import com.haosen.tool.exposure.detect.GlobalViewEvent.setDesc
import com.haosen.tool.exposure.detect.core.ViewExposureListener.Companion.addViewExposureDetectThresholdListener

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private val willInvisibleColor = Color.parseColor("#F8F6E3")
        private val visibleColor = Color.parseColor("#97E7E1")
    }

    override fun viewbinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.top) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView(){
        binding.verticalContainer.let { container ->
            container.post {
                val itemHeight = ((container.parent as? View)?.height ?: 0) / 5
                for (i in 0 until 20) {
                    container.addView(
                        createTextView(i),
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            itemHeight
                        )
                    )
                }
            }
        }

        binding.top.setOnClickListener {
            Toast.makeText(this@MainActivity, "点击top", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createTextView(index: Int):TextView{
        val textView = TextView(this)
        textView.setOnClickListener {
            Toast.makeText(this@MainActivity, "点击第{$index}个", Toast.LENGTH_SHORT).show()

        }
        textView.text = "TextView $index"
        textView.addViewExposureDetectThresholdListener(0.6f) { visible ->
            textView.setBackgroundColor(
                if (visible) visibleColor else willInvisibleColor
            )
        }
        textView.setDesc("textView:$index")
        return textView
    }
}