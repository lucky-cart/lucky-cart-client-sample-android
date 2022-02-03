package com.luckycart.samplesdk.ui

import com.luckycart.model.BannerDetails

sealed class GetBannerState {
    class OnSuccess(val response: BannerDetails) : GetBannerState()
    class OnError(val error: String) : GetBannerState()
}