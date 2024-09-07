package com.example.cryptoscalpingapp.presentation.ui.settings


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.presentation.viewmodel.APIKeySecureViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: APIKeySecureViewModel by viewModels()
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        preferenceScreen = createPreferenceScreen()

        setPreferencesFromResource(R.xml.preferences, rootKey)
        val apiKeyPreference = findPreference<EditTextPreference>("api_key")

        apiKeyPreference?.text = viewModel.getApiKey()
        apiKeyPreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val newApiKey = newValue as String
                viewModel.saveApiKey(newApiKey)
                true
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView.background = context?.getDrawable(R.drawable.main_background_gradient)

    }

    companion object {
        fun newInstanceEditSettings(): SettingsFragment {
            return SettingsFragment()
        }
    }

//    private fun createPreferenceScreen(): PreferenceScreen {
//        val screen = context?.let { preferenceManager.createPreferenceScreen(it) }
//
//        // Получить поля класса Transaction
//        val fields = TransactionItem::class.declaredMemberProperties
//
//        // Для каждого поля добавить CheckBoxPreference
//        fields.forEach { field ->
//            val checkBoxPreference = context?.let {
//                CheckBoxPreference(it).apply {
//                    key = "show_${field.name}"
//                    title = "show ${field.name}"
//                    setDefaultValue(true)
//                }
//            }
//            checkBoxPreference?.let { screen?.addPreference(it) }
//        }
//
//        return screen!!
//    }
}