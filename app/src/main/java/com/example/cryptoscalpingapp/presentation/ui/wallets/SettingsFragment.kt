package com.example.cryptoscalpingapp.presentation.ui.wallets


import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.example.cryptoscalpingapp.domain.model.TransactionItem
import kotlin.reflect.full.declaredMemberProperties


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceScreen = createPreferenceScreen()
    }

    private fun createPreferenceScreen(): PreferenceScreen {
        val screen = context?.let { preferenceManager.createPreferenceScreen(it) }

        // Получить поля класса Transaction
        val fields = TransactionItem::class.declaredMemberProperties

        // Для каждого поля добавить CheckBoxPreference
        fields.forEach { field ->
            val checkBoxPreference = context?.let {
                CheckBoxPreference(it).apply {
                    key = "show_${field.name}"
                    title = "show ${field.name}"
                    setDefaultValue(true)
                }
            }
            checkBoxPreference?.let { screen?.addPreference(it) }
        }

        return screen!!
    }
}