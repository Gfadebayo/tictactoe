package com.exzell.tictactoe.fragment

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import com.exzell.tictactoe.R
import com.exzell.tictactoe.utils.saveInputStream
import java.io.File

class SettingFragment: PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    companion object{
        fun getInstance(): SettingFragment{
            return SettingFragment()
        }

        const val KEY_SOUND = "KEY_SOUND"
        const val KEY_THEME = "KEY_THEME"
        const val KEY_SOUND_X = "KEY_SOUND_X"
        const val KEY_SOUND_Y = "KEY_SOUND_Y"
    }

    //since after the sound is returned, knowing the preference
    // it belongs to will no longer be possible, so we set this beforehand
    private var saveXSound = true

    private lateinit var mActivityResultContract: ActivityResultLauncher<Array<String>>

    private fun useResult(uri: Uri){
        val stream = requireContext().contentResolver.openInputStream(uri)
        val file = File(requireContext().getExternalFilesDir("sounds"), if(saveXSound) "x.mp3" else "y.mp3")
        saveInputStream(stream!!, file.path)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityResultContract = registerForActivityResult(ActivityResultContracts.OpenDocument()){
            useResult(it)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.setPreferencesFromResource(R.xml.pref_setting, rootKey)

        findPreference<Preference>(KEY_SOUND_X)?.setOnPreferenceClickListener {
            saveXSound = it.key.equals(KEY_SOUND_X)
            mActivityResultContract.launch(arrayOf("audio/*"))
            true
        }

        findPreference<Preference>(KEY_SOUND_Y)?.setOnPreferenceClickListener {
            saveXSound = it.key.equals(KEY_SOUND_Y)
            mActivityResultContract.launch(arrayOf("audio/*"))
            true
        }

        findPreference<ListPreference>(KEY_THEME)?.apply {

            summary = entry
            onPreferenceChangeListener = this@SettingFragment
        }
    }

    override fun onPreferenceChange(pref: Preference, newValue: Any?): Boolean {
        return if(pref.key.equals(KEY_THEME)){
            requireActivity().recreate()

            true

        }
        else false
    }
}