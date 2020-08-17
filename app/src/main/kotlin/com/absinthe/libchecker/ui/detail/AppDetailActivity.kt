package com.absinthe.libchecker.ui.detail

import android.content.ActivityNotFoundException
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.absinthe.libchecker.BaseActivity
import com.absinthe.libchecker.R
import com.absinthe.libchecker.constant.*
import com.absinthe.libchecker.database.LCDatabase
import com.absinthe.libchecker.database.LCRepository
import com.absinthe.libchecker.databinding.ActivityAppDetailBinding
import com.absinthe.libchecker.databinding.LayoutChipGroupBinding
import com.absinthe.libchecker.ktx.setLongClickCopiedToClipboard
import com.absinthe.libchecker.ui.fragment.applist.ComponentsAnalysisFragment
import com.absinthe.libchecker.ui.fragment.applist.NativeAnalysisFragment
import com.absinthe.libchecker.ui.fragment.applist.Sortable
import com.absinthe.libchecker.utils.PackageUtils
import com.absinthe.libchecker.utils.Toasty
import com.absinthe.libchecker.viewmodel.DetailViewModel
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.IntentUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME"

class AppDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityAppDetailBinding
    private val pkgName by lazy { intent.getStringExtra(EXTRA_PACKAGE_NAME) }
    private val viewModel by viewModels<DetailViewModel>()

    override fun setViewBinding(): View {
        isPaddingToolbar = true
        binding = ActivityAppDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initTransition()
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (GlobalValues.isShowEntryAnimation.value!!) {
            supportFinishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setRootPadding()
    }

    private fun initTransition() {
        window.apply {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                addTarget(android.R.id.content)
                duration = 300L
            }
            sharedElementReturnTransition = MaterialContainerTransform().apply {
                addTarget(android.R.id.content)
                duration = 250L
            }
        }
        findViewById<View>(android.R.id.content).transitionName = "app_card_container"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    }

    private fun initView() {
        setRootPadding()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        pkgName?.let { packageName ->
            supportActionBar?.apply {
                title = AppUtils.getAppName(packageName)
            }
            binding.apply {
                try {
                    val packageInfo = PackageUtils.getPackageInfo(packageName)
                    ivAppIcon.apply {
                        setImageDrawable(AppUtils.getAppIcon(packageName))
                        setOnClickListener {
                            try {
                                startActivity(IntentUtils.getLaunchAppIntent(pkgName))
                            } catch (e: ActivityNotFoundException) {
                                Toasty.show(this@AppDetailActivity, R.string.toast_cant_open_app)
                            } catch (e: NullPointerException) {
                                Toasty.show(this@AppDetailActivity, R.string.toast_package_name_null)
                            }
                        }
                    }
                    tvAppName.apply {
                        text = AppUtils.getAppName(packageName)
                        setLongClickCopiedToClipboard(text.toString())
                    }
                    tvPackageName.apply {
                        text = packageName
                        setLongClickCopiedToClipboard(text.toString())
                    }
                    tvVersion.apply {
                        text = PackageUtils.getVersionString(packageInfo)
                        setLongClickCopiedToClipboard(text.toString())
                    }
                    tvTargetApi.text = PackageUtils.getTargetApiString(packageInfo)

                    val abi = PackageUtils.getAbi(
                        packageInfo.applicationInfo.sourceDir,
                        packageInfo.applicationInfo.nativeLibraryDir,
                        isApk = false
                    )

                    layoutAbi.tvAbi.text = PackageUtils.getAbiString(abi)
                    layoutAbi.ivAbiType.setImageResource(PackageUtils.getAbiBadgeResource(abi))

                    lifecycleScope.launch(Dispatchers.IO) {
                        val lcDao = LCDatabase.getDatabase(application).lcDao()
                        val repository = LCRepository(lcDao)
                        val lcItem = repository.getItem(packageName)
                        val chipGroupBinding =
                            LayoutChipGroupBinding.inflate(layoutInflater).apply {
                                chipSplitApk.isVisible = lcItem?.isSplitApk ?: false
                                chipKotlinUsed.isVisible = lcItem?.isKotlinUsed ?: false
                            }
                        chipGroupBinding.root.id = View.generateViewId()

                        withContext(Dispatchers.Main) {
                            binding.headerContentLayout.addView(chipGroupBinding.root)
                            ConstraintSet().apply {
                                clone(binding.headerContentLayout)
                                connect(
                                    chipGroupBinding.root.id,
                                    ConstraintSet.TOP,
                                    binding.tvVersion.id,
                                    ConstraintSet.BOTTOM,
                                    resources.getDimension(R.dimen.normal_padding).toInt()
                                )
                                applyTo(binding.headerContentLayout)
                            }
                        }
                    }
                } catch (e: Exception) {
                    supportFinishAfterTransition()
                }

                ibSort.setOnClickListener {
                    Sortable.currentReference?.get()?.sort()
                }
            }
            viewModel.initComponentsData(packageName)
        } ?: supportFinishAfterTransition()

        val types = listOf(
            NATIVE, SERVICE, ACTIVITY, RECEIVER, PROVIDER/*, DEX*/
        )
        val tabTitles = listOf(
            getText(R.string.ref_category_native),
            getText(R.string.ref_category_service),
            getText(R.string.ref_category_activity),
            getText(R.string.ref_category_br),
            getText(R.string.ref_category_cp),
            getText(R.string.ref_category_dex)
        )

        binding.viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return types.size
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    types.indexOf(NATIVE) -> NativeAnalysisFragment.newInstance(pkgName!!, NATIVE)
                    types.indexOf(DEX) -> NativeAnalysisFragment.newInstance(pkgName!!, DEX)
                    else -> ComponentsAnalysisFragment.newInstance(types[position])
                }
            }
        }

        val mediator = TabLayoutMediator(binding.tabLayout, binding.viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabTitles[position]
            })
        mediator.attach()
    }

    private fun setRootPadding() {
        val isLandScape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        binding.root.apply {
            fitsSystemWindows = isLandScape
            setPadding(0, if (isLandScape) 0 else BarUtils.getStatusBarHeight(), 0, 0)
        }
    }
}
