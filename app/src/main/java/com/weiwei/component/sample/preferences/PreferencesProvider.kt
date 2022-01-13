package com.weiwei.component.sample.preferences

import android.net.Uri
import com.weiwei.core.content.SharedProvider

/**
 * @author Weicools
 *
 * @date 2021.08.20
 */
class PreferencesProvider : SharedProvider() {
    companion object {
        const val authorities = "com.weiwei.component.preferences"

        fun getPreferenceUri(): Uri {
            return Uri.parse("content://$authorities")
        }
    }

    override fun onCreate(): Boolean {
        return true
    }
}