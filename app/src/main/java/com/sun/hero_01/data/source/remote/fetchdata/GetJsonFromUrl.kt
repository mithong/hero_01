package com.sun.hero_01.data.source.remote.fetchdata

import android.os.AsyncTask
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener
import com.sun.hero_01.utils.KeyEntityType
import org.json.JSONObject

class GetJsonFromUrl<T> constructor(
    private val listener: OnFetchDataJsonListener<T>,
    private val keyEntityType: KeyEntityType
) : AsyncTask<String?, Void?, String?>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg strings: String?): String? {
        var data = ""
        try {
            val parseDataWithJson = ParseDataWithJson()
            data = parseDataWithJson.getJsonFromUrl(strings[0]).toString()
        } catch (e: Exception) {
            exception = e
        }
        return data
    }

    override fun onPostExecute(data: String?) {
        super.onPostExecute(data)
        if (data != null && !data.isBlank()) {
            val jsonObject = JSONObject(data)
            @Suppress("UNCHECKED_CAST")
            listener.onSuccess(ParseDataWithJson().parseJsonToData(jsonObject, keyEntityType) as T)
        } else {
            listener.onError(exception)
        }
    }
}
