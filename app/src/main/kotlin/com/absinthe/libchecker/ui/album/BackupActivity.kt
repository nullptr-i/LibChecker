package com.absinthe.libchecker.ui.album

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.absinthe.libchecker.BaseActivity
import com.absinthe.libchecker.R
import com.absinthe.libchecker.constant.Constants
import com.absinthe.libchecker.databinding.ActivityBackupBinding
import com.absinthe.libchecker.ui.main.MainActivity
import com.absinthe.libchecker.utils.StorageUtils
import com.absinthe.libchecker.viewmodel.SnapshotViewModel
import moe.shizuku.preference.PreferenceFragment
import rikka.material.widget.BorderRecyclerView
import rikka.material.widget.BorderView
import rikka.recyclerview.fixEdgeEffect
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BackupActivity : BaseActivity() {

    private lateinit var binding: ActivityBackupBinding
    private val viewModel by viewModels<SnapshotViewModel>()

    override fun setViewBinding(): ViewGroup {
        binding = ActivityBackupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAppBar(binding.appbar, binding.toolbar)
        (binding.root as ViewGroup).bringChildToFront(binding.appbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BackupFragment())
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE_BACKUP && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                try {
                    contentResolver.openOutputStream(it)?.let { os ->
                        viewModel.backup(os)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == Constants.REQUEST_CODE_RESTORE_BACKUP && resultCode == Activity.RESULT_OK) {
            data?.data?.let { intentData ->
                try {
                    contentResolver.openInputStream(intentData)?.let { inputStream ->
                        viewModel.restore(inputStream)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    class BackupFragment : PreferenceFragment() {
        private val viewModel by activityViewModels<SnapshotViewModel>()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.album_backup, rootKey)

            findPreference(Constants.PREF_LOCAL_BACKUP)?.apply {
                setOnPreferenceClickListener {
                    viewModel.snapshotItems.observe(viewLifecycleOwner, {
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
                        val date = Date()
                        val formatted = simpleDateFormat.format(date)

                        if (StorageUtils.isExternalStorageWritable) {
                            StorageUtils.createFile(requireActivity() as BaseActivity, "*/*",
                                "LibChecker-Snapshot-Backups-$formatted.lcss"
                            )
                        }
                    })
                    true
                }
            }
            findPreference(Constants.PREF_LOCAL_RESTORE)?.apply {
                setOnPreferenceClickListener {
                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"
                    }
                    requireActivity().startActivityForResult(intent, Constants.REQUEST_CODE_RESTORE_BACKUP)
                    true
                }
            }
        }

        override fun onCreateItemDecoration(): DividerDecoration? {
            return CategoryDivideDividerDecoration()
        }

        override fun onCreateRecyclerView(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): RecyclerView {
            val recyclerView = super.onCreateRecyclerView(inflater, parent, savedInstanceState) as BorderRecyclerView
            recyclerView.fixEdgeEffect()
            recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val lp = recyclerView.layoutParams
            if (lp is FrameLayout.LayoutParams) {
                lp.rightMargin = recyclerView.context.resources.getDimension(R.dimen.rd_activity_horizontal_margin).toInt()
                lp.leftMargin = lp.rightMargin
            }

            recyclerView.borderViewDelegate.borderVisibilityChangedListener = BorderView.OnBorderVisibilityChangedListener { top: Boolean, _: Boolean, _: Boolean, _: Boolean -> (activity as MainActivity?)?.appBar?.setRaised(!top) }
            return recyclerView
        }
    }
}