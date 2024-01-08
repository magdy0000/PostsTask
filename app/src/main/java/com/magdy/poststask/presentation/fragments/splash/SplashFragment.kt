package com.magdy.poststask.presentation.fragments.splash

import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.magdy.poststask.base.BaseFragment
import com.magdy.poststask.databinding.FragmentSplashBinding


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun FragmentSplashBinding.initializeUI() {
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(requireContext()
            , android.R.anim.fade_in
        )
        fadeInAnimation.duration=  3500
        fadeInAnimation.setAnimationListener(object : AnimationListener{
           override fun onAnimationStart(p0: Animation?) {

           }

           override fun onAnimationEnd(p0: Animation?) {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
           }

           override fun onAnimationRepeat(p0: Animation?) {

           }
       })
        image.startAnimation(fadeInAnimation)
    }
}