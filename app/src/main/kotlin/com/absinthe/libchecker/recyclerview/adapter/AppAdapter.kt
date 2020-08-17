package com.absinthe.libchecker.recyclerview.adapter

import android.view.ViewGroup
import com.absinthe.libchecker.R
import com.absinthe.libchecker.bean.*
import com.absinthe.libchecker.utils.PackageUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhangyue.we.x2c.X2C
import com.zhangyue.we.x2c.ano.Xml

@Xml(layouts = ["item_app"])
class AppAdapter : BaseQuickAdapter<AppItem, BaseViewHolder>(0) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return createBaseViewHolder(X2C.inflate(context, R.layout.item_app, parent, false))
    }

    override fun convert(holder: BaseViewHolder, item: AppItem) {
        holder.apply {
            setImageDrawable(R.id.iv_icon, item.icon)
            setText(R.id.tv_app_name, item.appName)
            setText(R.id.tv_package_name, item.packageName)
            setText(R.id.tv_version, item.versionName)
            setText(R.id.tv_abi, PackageUtils.getAbiString(item.abi))
            setImageResource(R.id.iv_abi_type, PackageUtils.getAbiBadgeResource(item.abi))
        }
    }
}