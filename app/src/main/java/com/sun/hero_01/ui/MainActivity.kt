package com.sun.hero_01.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sun.hero_01.R
import com.sun.hero_01.ui.`class`.ClassFragment
import com.sun.hero_01.ui.champion.ChampionFragment
import com.sun.hero_01.ui.favorite.FavoriteFragment
import com.sun.hero_01.utils.extensions.showIcon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initEvent()
    }

    private fun initView() {
        setSupportActionBar(toolbarTop)
        supportActionBar.apply {
            title = getString(R.string.app_name_toolbar)
            showIcon()
        }
        loadFragment(ChampionFragment.newInstance())
    }

    private fun initEvent() {
        navigationBottom.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.menuChampion -> {
                    loadFragment(ChampionFragment.newInstance())
                    true
                }
                R.id.menuClass -> {
                    loadFragment(ClassFragment.newInstance())
                    true
                }
                R.id.menuFavorite -> {
                    loadFragment(FavoriteFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.frameContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        navigationBottom.visibility = visibility
    }

    companion object {
        fun getMainIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
