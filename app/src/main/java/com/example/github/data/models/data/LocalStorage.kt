package com.example.github.data.models.data

import android.content.Context
import android.content.SharedPreferences
import com.example.github.app.App
import com.example.github.data.models.GetUserProfileInfoResponse
import packagename.data.utils.IntPreference
import packagename.data.utils.StringPreference

class LocalStorage() {
    companion object {
        val prefs: SharedPreferences =
            App.instance.getSharedPreferences("TaskAppSharePrefs", Context.MODE_PRIVATE)
    }

    var code by StringPreference(prefs)
    var token by StringPreference(prefs)
    var login by IntPreference(prefs)
}