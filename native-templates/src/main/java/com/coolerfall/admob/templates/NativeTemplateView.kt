package com.coolerfall.admob.templates

import android.content.Context
import android.os.Build.VERSION_CODES
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * @author Vincent Cheung (coolingfall@gmail.com)
 */
class NativeTemplateView : FrameLayout {

	private var nativeAd: NativeAd? = null
	private lateinit var nativeAdView: NativeAdView
	private var primaryView: TextView? = null
	private var secondaryView: TextView? = null
	private var ratingBar: RatingBar? = null
	private var tertiaryView: TextView? = null
	private var iconView: ImageView? = null
	private var mediaView: MediaView? = null
	private var callToActionView: Button? = null

	constructor(context: Context) : this(context, null)
	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		initView(context, attrs)
	}

	@RequiresApi(VERSION_CODES.LOLLIPOP)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
		context,
		attrs,
		defStyleAttr,
		defStyleRes
	) {
		initView(context, attrs)
	}

	public override fun onFinishInflate() {
		super.onFinishInflate()
		nativeAdView = findViewById(R.id.native_ad_view)
		primaryView = findViewById(R.id.gnt_tv_headline)
		secondaryView = findViewById(R.id.gnt_tv_secondary)
		tertiaryView = findViewById(R.id.gnt_tv_body)
		ratingBar = findViewById(R.id.gnt_rating_bar)
		ratingBar?.isEnabled = false
		callToActionView = findViewById(R.id.gnt_btn_call_to_action)
		iconView = findViewById(R.id.gnt_iv_icon)
		mediaView = findViewById(R.id.gnt_media_view)
	}

	private fun initView(context: Context, attributeSet: AttributeSet?) {
		val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.GntTemplateView, 0, 0)
		val templateType: Int = try {
			attributes.getResourceId(
				R.styleable.GntTemplateView_gnt_template_layout, R.layout.gnt_large_template_view
			)
		} finally {
			attributes.recycle()
		}
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		inflater.inflate(templateType, this)
	}

	fun setNativeAd(nativeAd: NativeAd) {
		this.nativeAd = nativeAd
		val store = nativeAd.store
		val advertiser = nativeAd.advertiser
		val headline = nativeAd.headline
		val body = nativeAd.body
		val callToAction = nativeAd.callToAction
		val starRating = nativeAd.starRating
		val icon = nativeAd.icon
		val secondaryText: String?
		nativeAdView.callToActionView = callToActionView
		nativeAdView.headlineView = primaryView
		nativeAdView.mediaView = mediaView
		if (adHasOnlyStore(nativeAd)) {
			nativeAdView.storeView = secondaryView
			secondaryText = store
		} else if (!TextUtils.isEmpty(advertiser)) {
			nativeAdView.advertiserView = secondaryView
			secondaryText = advertiser
		} else {
			secondaryText = ""
		}
		primaryView?.text = headline
		callToActionView?.text = callToAction

		/* Set the secondary view to be the star rating if available. */
		if (starRating != null && starRating > 0) {
			secondaryView?.visibility = GONE
			ratingBar?.visibility = VISIBLE
			ratingBar?.rating = starRating.toFloat()
			nativeAdView.starRatingView = ratingBar
		} else {
			secondaryView?.text = secondaryText
			secondaryView?.visibility = VISIBLE
			ratingBar?.visibility = GONE
		}
		if (icon != null) {
			iconView?.visibility = VISIBLE
			iconView?.setImageDrawable(icon.drawable)
		} else {
			iconView?.visibility = GONE
		}
		if (tertiaryView != null) {
			nativeAdView.bodyView = tertiaryView
			tertiaryView?.text = body
		}
		nativeAdView.setNativeAd(nativeAd)
	}

	/**
	 * To prevent memory leaks, make sure to destroy your ad when you don't need it anymore. This
	 * method does not destroy the template view.
	 * https://developers.google.com/admob/android/native-unified#destroy_ad
	 */
	fun destroyNativeAd() {
		nativeAd?.destroy()
	}

	private fun adHasOnlyStore(nativeAd: NativeAd): Boolean {
		val store = nativeAd.store
		val advertiser = nativeAd.advertiser
		return !TextUtils.isEmpty(store) && TextUtils.isEmpty(advertiser)
	}
}