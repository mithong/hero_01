package com.sun.hero_01.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sun.hero_01.R
import com.sun.hero_01.ui.MainActivity
import com.sun.hero_01.utils.ToolbarIcon
import com.sun.hero_01.utils.extensions.showIcon

abstract class BaseFragment : Fragment() {

    protected open var bottomNavigationViewVisibility = View.VISIBLE
    protected val toolbar by lazy { (activity as AppCompatActivity?)?.supportActionBar }

    abstract val layoutResourceId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.let {
            it.isClickable = true
        }
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }

    protected fun showAlertDialog(context: Context, msg: String) {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    protected fun showAlertDialog(context: Context, @StringRes resId: Int) {
        showAlertDialog(context, context.resources.getString(resId))
    }

    protected open fun initToolbar() {
        toolbar?.apply {
            title = getString(R.string.app_name_toolbar)
            showIcon(ToolbarIcon.APP)
        }
    }
}
