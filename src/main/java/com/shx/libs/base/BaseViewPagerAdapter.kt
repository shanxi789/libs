package com.shx.libs.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Description: BaseViewPagerAdapter
 * @Author: sihaoxuan
 * @Date: 2026/1/29 下午5:56
 */
class BaseViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private var mFragments: MutableList<Fragment> = mutableListOf()

    fun setFragments(fragments: MutableList<Fragment>) {
        this.mFragments = fragments
        notifyDataSetChanged()
    }

    override fun getItemCount() = mFragments.size

    override fun createFragment(position: Int) = mFragments[position]

}