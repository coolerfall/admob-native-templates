package com.coolerfall.admob.templates.sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolerfall.admob.templates.databinding.FragmentAdmobBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest.Builder
import com.google.android.gms.ads.LoadAdError

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AdmobFragment : Fragment() {

	private var _binding: FragmentAdmobBinding? = null

	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAdmobBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
			.forNativeAd {
				with(binding) {
					admobSamllTplView.setNativeAd(it)
					admobMediumTplView.setNativeAd(it)
					admobLargeTplView.setNativeAd(it)
				}
			}
			.withAdListener(object : AdListener() {
				override fun onAdFailedToLoad(error: LoadAdError) {
					Log.e("AD", "load error: $error")
				}
			})
			.build()
		adLoader.loadAds(Builder().build(), 3)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding.admobLargeTplView.destroyNativeAd()
		binding.admobMediumTplView.destroyNativeAd()
		binding.admobSamllTplView.destroyNativeAd()
		_binding = null
	}
}