package com.skyvo.mobile.core.network.cokkie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.Collections

class CookieManager : CookieJar {

    private val sessionCookies = Collections.synchronizedList(ArrayList<Cookie>())

    @Suppress("NestedBlockDepth")
    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val cookiesToRemove = ArrayList<Cookie>()
        val oldSessionCookies = ArrayList(sessionCookies)

        oldSessionCookies.addAll(this.sessionCookies)

        if (this.sessionCookies.size > 0) {
            for (newCookie in cookies) {
                for (oldCookie in oldSessionCookies) {
                    if (checkNameAndDomain(newCookie, oldCookie) && checkPathAndHost(
                            newCookie,
                            oldCookie
                        )
                    ) {
                        cookiesToRemove.add(oldCookie)
                    }
                }
            }
        }

        this.sessionCookies.removeAll(cookiesToRemove)
        this.sessionCookies.addAll(cookies)

        val currentCookies = ArrayList<Cookie>()
        currentCookies.addAll(sessionCookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val currentCookies = ArrayList<Cookie>()
        currentCookies.addAll(sessionCookies)
        return currentCookies
    }

    fun clearCookies() {
        this.sessionCookies.clear()
    }

    private fun checkNameAndDomain(newCookie: Cookie, oldCookie: Cookie): Boolean {
        return newCookie.name == oldCookie.name && newCookie.domain == oldCookie.domain
    }

    private fun checkPathAndHost(newCookie: Cookie, oldCookie: Cookie): Boolean {
        return newCookie.path == oldCookie.path && newCookie.hostOnly == oldCookie.hostOnly
    }
}