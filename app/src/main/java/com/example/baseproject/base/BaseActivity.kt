package com.example.baseproject.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.example.baseproject.utils.PrefUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var mPrefUtils: PrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(data: Any?) {
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
    }

    fun printData(str: Any?) {
        Log.e("The_Wolf", "printData: $str")
    }

    fun printData(key: String, str: Any?) {
        Log.e("The_Wolf", "$key---------->: $str")
    }

    fun AppCompatActivity.setWindowInsets(
        view: View? = null,
        isLeftMargin: Boolean = true,
        isRightMargin: Boolean = true,
        isBottomMargin: Boolean = false,
        isTopMargin: Boolean = true,
    ) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (view == null) return
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                if (isLeftMargin) leftMargin = inset.left
                if (isBottomMargin) bottomMargin = inset.bottom
                if (isRightMargin) rightMargin = inset.right
                if (isTopMargin) topMargin = inset.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}
