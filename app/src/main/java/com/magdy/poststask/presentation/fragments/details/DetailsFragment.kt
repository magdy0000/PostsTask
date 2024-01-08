package com.magdy.poststask.presentation.fragments.details

import com.magdy.poststask.base.BaseFragment
import com.magdy.poststask.databinding.FragmentDetailsBinding
import com.magdy.poststask.presentation.models.ModelPostsPresentation

class DetailsFragment :
    BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private var modelPost  : ModelPostsPresentation ?= null

    override fun getFragmentArguments() {
        modelPost = DetailsFragmentArgs.fromBundle(requireArguments()).postsModel
    }
    override fun FragmentDetailsBinding.initializeUI() {
        modelPost?.apply {
            textBody.text = body
            textTitle.text = title
        }
    }


}