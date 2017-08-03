package io.basketball.basketball.page

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by CJJ on 2017/8/3.
 *@author CJJ
 */
class FragmentAdapter : FragmentPagerAdapter {

    var fragments: List<Fragment>
    var fm: FragmentManager
    var titles: List<String>

    constructor(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) : super(fm) {
        this.fragments = fragments
        this.fm = fm
        this.titles = titles
    }

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments!!.size
    }
}