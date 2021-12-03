package com.exzell.tictactoe

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.exzell.tictactoe.databinding.ActivityMainBinding
import com.exzell.tictactoe.fragment.HomeFragment
import com.exzell.tictactoe.fragment.SettingFragment
import com.exzell.tictactoe.viewmodel.MainViewModel

class MainActivity : ThemeActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(toolbar)

            supportFragmentManager.beginTransaction()
                    .replace(fragmentContainer.id, HomeFragment.getInstance())
                    .commit()


            val pref = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)

            mViewModel = ViewModelProvider(this@MainActivity, ViewModelProvider.AndroidViewModelFactory(application))
                    .get(MainViewModel::class.java).apply {
                        prepareSound()
                        enableSound(pref.getBoolean(SettingFragment.KEY_SOUND, true))
                    }

            pref.registerOnSharedPreferenceChangeListener(this@MainActivity)
        }
    }

    override fun onBackPressed() {
        if(!supportFragmentManager.popBackStackImmediate()) super.onBackPressed()
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences, key: String?) {

        if(key.equals(SettingFragment.KEY_SOUND)){
            mViewModel.enableSound(pref.getBoolean(SettingFragment.KEY_SOUND, true))
        }
    }
}