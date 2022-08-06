package com.buildreams.themovies.util

import com.doepiccoding.usecase_di.BuildConfig.BASE_IMAGE_URL

object MovieUtil {
    /**
     * @param imagePath A relative image path.
     * @return A String URL based on the relative image path
     */
    fun imagePathBuilder(imagePath: String) = BASE_IMAGE_URL + imagePath

}