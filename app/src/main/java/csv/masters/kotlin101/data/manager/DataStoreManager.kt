package csv.masters.kotlin101.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class DataStoreManager constructor(
    private val context: Context
) {

    suspend fun putString(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String? {
        return context.dataStore.data.first()[stringPreferencesKey(key)]
    }

    suspend fun <T> putObject(key: String, obj: Any, classOfT: Class<T>) {
        if (key.isEmpty()) throw NullPointerException()
        putString(key, Gson().toJson(obj, classOfT))
    }

    suspend fun <T> getObject(key: String, classOfT: Class<T>): T? {
        val json = getString(key)
        return Gson().fromJson(json, classOfT) ?: null
    }

    suspend fun <T> getObjectList(key: String, classOfT: Class<T>): ArrayList<T>? {
        val json = getString(key)
        return Gson().fromJson(json, TypeToken.getParameterized(ArrayList::class.java, classOfT).type)
    }

    companion object {
        private const val DATA_STORE_NAME = "kotlin_101_data_store"
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    }
}