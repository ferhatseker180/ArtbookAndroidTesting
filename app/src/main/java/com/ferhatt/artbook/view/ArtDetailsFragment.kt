package com.ferhatt.artbook.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ferhatt.artbook.R
import com.ferhatt.artbook.databinding.FragmentArtsDetailsBinding

class ArtDetailsFragment : Fragment(R.layout.fragment_arts_details) {
    private var detailBinding : FragmentArtsDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsDetailsBinding.bind(view)
        detailBinding = binding

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

}