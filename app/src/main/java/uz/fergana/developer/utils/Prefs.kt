package uz.fergana.developer.utils

import com.orhanobut.hawk.Hawk
import uz.fergana.developer.MyApp
import uz.fergana.developer.model.UserModel
import uz.fergana.developer.model.WorkerModel

const val PREF_TOKEN = "pref_token"
const val PREF_LANG = "pref_lang"
const val PREF_FCM = "pref_fcm"
const val PREF_HOST = "pref_host"
const val PREF_USER = "pref_user"
const val PREF_FAVORITES = "pref_favorites"

class Prefs {

    companion object {
        fun init(app: MyApp) {
            Hawk.init(app).build()
        }

        //d1cb7fe42c63d70cd6ad17475cbc78deab72f066
        fun getToken(): String {
            return Hawk.get(PREF_TOKEN, "")
        }

        fun setToken(token: String) {
            Hawk.put(PREF_TOKEN, token)
        }

        fun getLang(): String? {
            return Hawk.get(PREF_LANG)
        }

        fun setLang(lang: String) {
            Hawk.put(PREF_LANG, lang)
        }

        fun setUser(value: UserModel) {
            Hawk.put(PREF_USER, value)
        }

        fun getUser(): UserModel? {
            return Hawk.get(PREF_USER)
        }

        fun setHost(value: String) {
            Hawk.put(PREF_HOST, value)
        }

        fun getHost(): String? {
            return Hawk.get(PREF_HOST)
        }

        fun setFCM(value: String) {
            Hawk.put(PREF_FCM, value)
        }

        fun getFCM(): String? {
            return Hawk.get(PREF_FCM)
        }

        fun setFavorites(value: List<Int>) {
            Hawk.put(PREF_FAVORITES, value)
        }

        fun getFavorites(): List<Int> {
            return Hawk.get(PREF_FAVORITES, emptyList())
        }

        fun setFavorite(worker: WorkerModel) {
            val favorites = getFavorites().toMutableList()
            if (favorites.any { it == worker.id }) {
                favorites.removeAt(favorites.indexOfFirst { it == worker.id })
            } else {
                favorites.add(0, worker.id)
            }
            setFavorites(favorites)
        }

        fun checkFavorite(worker: WorkerModel): Boolean{
            return getFavorites().any { it == worker.id }
        }


        fun clearAll() {
            Hawk.deleteAll()
        }

    }
}