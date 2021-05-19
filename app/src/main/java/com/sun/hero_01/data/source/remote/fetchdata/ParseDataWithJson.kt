package com.sun.hero_01.data.source.remote.fetchdata

import com.sun.hero_01.data.model.HeroEntity
import com.sun.hero_01.utils.KeyEntityType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ParseDataWithJson {

    fun getJsonFromUrl(urlString: String?): String? {
        try {
            val url = URL(urlString)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.apply {
                connectTimeout = TIME_OUT
                readTimeout = TIME_OUT
                requestMethod = METHOD_GET
                doOutput = true
                connect()
            }
            val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            httpURLConnection.disconnect()
            return stringBuilder.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    fun parseJsonToData(jsonObject: JSONObject?, keyEntityType: KeyEntityType): Any? {
        return try {
            when (keyEntityType) {
                KeyEntityType.HERO_DETAIL -> {
                    parseJsonObject(jsonObject, keyEntityType)
                }
                KeyEntityType.LIST_HERO -> {
                    parseJsonListObject(jsonObject?.getJSONObject(HeroEntity.DATA), keyEntityType)
                }
                else -> {
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun parseJsonArray(jsonArray: JSONArray?, typeModel: KeyEntityType): Any? {
        return try {
            val data = mutableListOf<Any?>()
            for (i in 0 until (jsonArray?.length() ?: 0)) {
                val jsonObject = jsonArray?.getJSONObject(i)
                data.add(parseJsonObject(jsonObject, typeModel))
            }
            data
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    private fun parseJsonListObject(jsonObject: JSONObject?, keyEntityType: KeyEntityType): Any {
        val data = mutableListOf<Any?>()
        try {
            jsonObject?.keys()?.forEach {
                val jsonObjects = jsonObject.getJSONObject(it)
                val item = ParseDataWithJson().parseJsonObject(jsonObjects, keyEntityType)
                data.add(item)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return data
    }

    fun parseJsonObject(jsonObject: JSONObject?, keyEntityType: KeyEntityType): Any? {
        try {
            jsonObject?.let {
                return when (keyEntityType) {
                    KeyEntityType.HERO_DETAIL -> {
                        ParseJson().onParseJsonToHeroDetail(it)
                    }
                    KeyEntityType.LIST_SKIN -> {
                        ParseJson().onParseJsonToHeroSkin(it)
                    }
                    KeyEntityType.LIST_INFO -> {
                        ParseJson().onParseJsonToHeroInfo(it)
                    }
                    KeyEntityType.LIST_STAT -> {
                        ParseJson().onParseJsonToHeroStat(it)
                    }
                    KeyEntityType.LIST_SPELL -> {
                        ParseJson().onParseJsonToHeroSpell(it)
                    }
                    KeyEntityType.LIST_PASSIVE -> {
                        ParseJson().onParseJsonToHeroPassive(it)
                    }
                    KeyEntityType.LIST_HERO -> {
                        ParseJson().onParseJsonToListHero(it)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
    }
}
