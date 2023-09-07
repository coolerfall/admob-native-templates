package com.coolerfall.admob.templates.sample

import android.app.Application
import com.google.android.gms.ads.MobileAds

/**
 * @author Vincent Cheung (coolingfall@gmail.com)
 */
class SampleApplication : Application() {

	override fun onCreate() {
		super.onCreate()

		MobileAds.initialize(this)
	}
}