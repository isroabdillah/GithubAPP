package com.isrodicoding.githubapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.isrodicoding.githubapp.R
import com.isrodicoding.githubapp.SettingPreferences
import com.isrodicoding.githubapp.databinding.ActivitySettingBinding
import com.isrodicoding.githubapp.viewModels.SettingThemeViewModel
import com.isrodicoding.githubapp.viewModels.SettingThemeViewModelFactory

//=--



//--

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)

        val temaViewModel = ViewModelProvider(this, SettingThemeViewModelFactory(pref)).get(
            SettingThemeViewModel::class.java
        )



        temaViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->

            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchNightmode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchNightmode.isChecked = false
            }
        }

        binding.switchNightmode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            temaViewModel.saveThemeSetting(isChecked)
            setNightModeSum(isChecked)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    private fun setNightModeSum(isNightMode: Boolean) {
        binding.tvNightMode.text =
            if (isNightMode) resources.getString(R.string.descriptionOffNightMode) else resources.getString(
                R.string.descriptionNightMode
            )
    }

}