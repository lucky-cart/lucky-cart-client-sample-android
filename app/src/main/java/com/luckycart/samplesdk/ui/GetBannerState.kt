package com.luckycart.samplesdk.ui

import com.luckycart.model.Banner

sealed class GetBannerState {
    class OnSuccess(val banner: Banner) : GetBannerState()
    class OnError(val error: String) : GetBannerState()
}