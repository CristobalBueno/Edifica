package com.edifica.interfaces

import com.edifica.models.Ads

interface CustomAdsListener {
    fun onItemAdsClick(currentAds: Ads, position: Int)
}