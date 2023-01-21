package com.example.loginthird.helpers

import android.content.Context

class Config(context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }

//    var directorySorting: Int
//        get(): Int = prefs.getInt(DIRECTORY_SORT_ORDER, SORT_BY_DATE_MODIFIED or SORT_DESCENDING)
//        set(order) = prefs.edit().putInt(DIRECTORY_SORT_ORDER, order).apply()
//
//    fun saveFolderGrouping(path: String, value: Int) {
//        if (path.isEmpty()) {
//            groupBy = value
//        } else {
//            prefs.edit().putInt(GROUP_FOLDER_PREFIX + path.toLowerCase(), value).apply()
//        }
//    }
//
//    fun getFolderGrouping(path: String): Int {
//        var groupBy = prefs.getInt(GROUP_FOLDER_PREFIX + path.toLowerCase(), groupBy)
//        if (path != SHOW_ALL && groupBy and GROUP_BY_FOLDER != 0) {
//            groupBy -= GROUP_BY_FOLDER + 1
//        }
//        return groupBy
//    }
//
//    fun removeFolderGrouping(path: String) {
//        prefs.edit().remove(GROUP_FOLDER_PREFIX + path.toLowerCase()).apply()
//    }
//
//    fun hasCustomGrouping(path: String) = prefs.contains(GROUP_FOLDER_PREFIX + path.toLowerCase())
//
//    fun saveFolderViewType(path: String, value: Int) {
//        if (path.isEmpty()) {
//            viewTypeFiles = value
//        } else {
//            prefs.edit().putInt(VIEW_TYPE_PREFIX + path.toLowerCase(), value).apply()
//        }
//    }

}
