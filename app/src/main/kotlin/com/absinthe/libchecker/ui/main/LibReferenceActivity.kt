package com.absinthe.libchecker.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.absinthe.libchecker.BaseActivity
import com.absinthe.libchecker.R
import com.absinthe.libchecker.constant.GlobalValues
import com.absinthe.libchecker.constant.NATIVE
import com.absinthe.libchecker.databinding.ActivityLibReferenceBinding
import com.absinthe.libchecker.recyclerview.adapter.AppAdapter
import com.absinthe.libchecker.ui.detail.AppDetailActivity
import com.absinthe.libchecker.ui.detail.EXTRA_PACKAGE_NAME
import com.absinthe.libchecker.utils.AntiShakeUtils
import com.absinthe.libchecker.utils.UiUtils
import com.absinthe.libchecker.viewmodel.LibReferenceViewModel
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import rikka.material.widget.BorderView

const val EXTRA_NAME = "NAME"
const val EXTRA_TYPE = "TYPE"

class LibReferenceActivity : BaseActivity() {

    private lateinit var binding: ActivityLibReferenceBinding
    private val adapter = AppAdapter()
    private val viewModel by viewModels<LibReferenceViewModel>()

    override fun setViewBinding(): View {
        binding = ActivityLibReferenceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.apply {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementsUseOverlay = false
        }
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        super.onCreate(savedInstanceState)

        val name = intent.extras?.getString(EXTRA_NAME)
        val type = intent.extras?.getInt(EXTRA_TYPE) ?: NATIVE

        if (name == null) {
            finish()
        } else {
            initView()
            viewModel.setData(name, type)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.root.apply {
            fitsSystemWindows =
                resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            setPadding(paddingStart, 0, paddingEnd, paddingBottom)
        }
    }

    private fun initView() {
        binding.apply {
            root.apply {
                fitsSystemWindows =
                    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                setPadding(paddingStart, 0, paddingEnd, paddingBottom)
            }

            setAppBar(appbar, toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (root as ViewGroup).bringChildToFront(appbar)

            rvList.apply {
                adapter = this@LibReferenceActivity.adapter
                borderVisibilityChangedListener =
                    BorderView.OnBorderVisibilityChangedListener { top: Boolean, _: Boolean, _: Boolean, _: Boolean ->
                        appBar?.setRaised(!top)
                    }
                setPadding(
                    0,
                    paddingTop + BarUtils.getStatusBarHeight(),
                    0,
                    UiUtils.getNavBarHeight()
                )
            }
            vfContainer.apply {
                setInAnimation(
                    this@LibReferenceActivity,
                    R.anim.anim_fade_in
                )
                setOutAnimation(
                    this@LibReferenceActivity,
                    R.anim.anim_fade_out
                )
            }
        }

        viewModel.libRefList.observe(this, Observer {
            adapter.setList(it)
            binding.vfContainer.displayedChild = 1
        })

        adapter.setOnItemClickListener { _, view, position ->
            if (AntiShakeUtils.isInvalidClick(view)) {
                return@setOnItemClickListener
            }

            val intent = Intent(this, AppDetailActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putString(EXTRA_PACKAGE_NAME, adapter.getItem(position).packageName)
                })
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(
                this, view, view.transitionName
            )

            if (GlobalValues.isShowEntryAnimation.value!!) {
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
            }
        }
    }
}
