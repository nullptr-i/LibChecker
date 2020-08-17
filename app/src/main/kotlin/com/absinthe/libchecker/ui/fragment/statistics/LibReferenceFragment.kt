package com.absinthe.libchecker.ui.fragment.statistics

import android.content.Intent
import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.absinthe.libchecker.R
import com.absinthe.libchecker.constant.*
import com.absinthe.libchecker.constant.librarymap.NativeLibMap
import com.absinthe.libchecker.databinding.FragmentLibReferenceBinding
import com.absinthe.libchecker.recyclerview.adapter.LibReferenceAdapter
import com.absinthe.libchecker.recyclerview.diff.RefListDiffUtil
import com.absinthe.libchecker.ui.fragment.BaseFragment
import com.absinthe.libchecker.ui.main.EXTRA_NAME
import com.absinthe.libchecker.ui.main.EXTRA_TYPE
import com.absinthe.libchecker.ui.main.LibReferenceActivity
import com.absinthe.libchecker.utils.ActivityStackManager
import com.absinthe.libchecker.utils.AntiShakeUtils
import com.absinthe.libchecker.view.dialogfragment.LibDetailDialogFragment
import com.absinthe.libchecker.viewmodel.AppViewModel
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.analytics.EventProperties

class LibReferenceFragment : BaseFragment<FragmentLibReferenceBinding>(R.layout.fragment_lib_reference), SearchView.OnQueryTextListener {

    private val viewModel by activityViewModels<AppViewModel>()
    private val adapter = LibReferenceAdapter()

    private var isInit = false
    private var isListInit = false
    private var menu: Menu? = null
    private var category = NATIVE

    override fun initBinding(view: View): FragmentLibReferenceBinding = FragmentLibReferenceBinding.bind(view)

    override fun init() {
        binding.apply {
            rvList.adapter = adapter
            vfContainer.apply {
                setInAnimation(activity, R.anim.anim_fade_in)
                setOutAnimation(activity, R.anim.anim_fade_out)
            }
        }

        adapter.apply {
            setDiffCallback(RefListDiffUtil())
            setOnItemClickListener { _, view, position ->
                if (AntiShakeUtils.isInvalidClick(view)) {
                    return@setOnItemClickListener
                }

                val intent = Intent(requireContext(), LibReferenceActivity::class.java).apply {
                    val item = this@LibReferenceFragment.adapter.data[position]
                    putExtra(EXTRA_NAME, item.libName)
                    putExtra(EXTRA_TYPE, item.type)
                }
                startActivity(intent)
            }
            setOnItemChildClickListener { _, view, position ->
                if (view.id == R.id.iv_icon) {
                    if (GlobalValues.config.enableLibDetail) {
                        val ref = this@LibReferenceFragment.adapter.getItem(position)
                        val name = ref.libName
                        val regexName = NativeLibMap.findRegex(name)?.regexName
                        LibDetailDialogFragment.newInstance(name, ref.type, regexName)
                                .apply {
                                ActivityStackManager.topActivity?.apply {
                                    show(supportFragmentManager, tag)
                                }
                            }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (!isInit) {
            viewModel.apply {
                libReference.observe(viewLifecycleOwner, Observer {
                    adapter.setList(it)

                    if (binding.vfContainer.displayedChild == 0) {
                        binding.vfContainer.displayedChild = 1
                    }
                    isListInit = true
                    menu?.findItem(R.id.search)?.isVisible = true
                })
                clickBottomItemFlag.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        binding.rvList.apply {
                            if (canScrollVertically(-1)) {
                                smoothScrollToPosition(0)
                            }
                        }
                    }
                })
            }
            GlobalValues.isShowSystemApps.observe(viewLifecycleOwner, Observer {
                computeRef()
            })
            GlobalValues.libReferenceThreshold.observe(viewLifecycleOwner, Observer {
                viewModel.refreshRef()
            })

            computeRef()
            isInit = true
        }
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onPause() {
        super.onPause()
        setHasOptionsMenu(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lib_ref_menu, menu)
        this.menu = menu

        val searchView = SearchView(requireContext()).apply {
            setIconifiedByDefault(false)
            setOnQueryTextListener(this@LibReferenceFragment)
            queryHint = getText(R.string.search_hint)
            isQueryRefinementEnabled = true

            findViewById<View>(androidx.appcompat.R.id.search_plate).apply {
                setBackgroundColor(Color.TRANSPARENT)
            }
        }

        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView

            if (!isListInit) {
                isVisible = false
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ref_category_all -> category = ALL
            R.id.ref_category_native -> category = NATIVE
            R.id.ref_category_service -> category = SERVICE
            R.id.ref_category_activity -> category = ACTIVITY
            R.id.ref_category_br -> category = RECEIVER
            R.id.ref_category_cp -> category = PROVIDER
        }

        if (item.itemId != R.id.search) {
            computeRef()
        }
        Analytics.trackEvent(Constants.Event.LIB_REFERENCE_FILTER_TYPE, EventProperties().set("Type", category.toLong()))
        return super.onOptionsItemSelected(item)
    }

    private fun computeRef() {
        binding.vfContainer.displayedChild = 0
        viewModel.computeLibReference(category)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.libReference.value?.let { list ->
            val filter = list.filter {
                it.libName.contains(newText, ignoreCase = true) || it.chip?.name?.contains(newText, ignoreCase = true) ?: false
            }
            adapter.setDiffNewData(filter.toMutableList())
        }
        return false
    }
}