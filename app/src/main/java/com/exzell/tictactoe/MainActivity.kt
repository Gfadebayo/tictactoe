package com.exzell.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.exzell.tictactoe.databinding.ActivityMainBinding
import com.exzell.tictactoe.fragment.GameFragment
import com.exzell.tictactoe.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(toolbar)

            supportFragmentManager.beginTransaction()
                    .add(fragmentContainer.id, HomeFragment.getInstance())
                    .commit()

            ViewModelProvider(this@MainActivity, ViewModelProvider.NewInstanceFactory())
                    .get(MainViewModel::class.java).apply {
                        prepareSound(R.raw.sound_x, R.raw.sound_o, this@MainActivity)
                    }
        }
    }

    override fun onBackPressed() {
        if(!supportFragmentManager.popBackStackImmediate()) super.onBackPressed()
    }
}