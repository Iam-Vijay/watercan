package com.watercanedelivery.utils
import javax.inject.Inject

/**
 * Created by Karthikeyan on 27-01-2021.
 */
class SharedPreferenceUtil @Inject constructor(val sharedPreferences: android.content.SharedPreferences) {

    fun saveData(key: String, value: String) {
        try {
            val map: HashMap<String, String> = HashMap()
            map.put(key, value)
            setDataSet(map)
        } catch (e: Exception) {
        }
    }

    private fun setDataSet(map: HashMap<String, String>) {
        try {
            val editor = sharedPreferences.edit()
            for (key in map.keys) {
                editor.putString(key, map[key])
            }
            editor.apply()
        } catch (e: Exception) {
        }
    }

    fun getData(key: String?): String? {
        return sharedPreferences.getString(key, null)
    }

    fun removeData(key: String) {
        try {
            removeDataSet(arrayOf(key))
        } catch (e: Exception) {
        }
    }

    private fun removeDataSet(keys: Array<String>) {
        try {
            val editor = sharedPreferences.edit()
            for (key in keys) {
                editor.remove(key)
            }
            editor.apply()
        } catch (e: Exception) {
        }
    }

    fun setIntegerData(key: String?, value: Int) {
        try {
            val editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        } catch (e: Exception) {
        }
    }

    fun getIntegerData(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun setBooleanData(key: String?, value: Boolean) {
        try {
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        } catch (e: Exception) {
        }
    }

    fun getBooleanData(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearAllData() {
        try {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        } catch (e: Exception) {
        }
    }
}
