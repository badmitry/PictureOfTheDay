package com.badmitry.pictureoftheday.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.api.load
import com.badmitry.pictureoftheday.BuildConfig
import com.badmitry.pictureoftheday.R
import com.badmitry.pictureoftheday.databinding.FragmentLayoutBinding
import com.badmitry.pictureoftheday.mvvm.vm.FragmentViewModel
import com.badmitry.pictureoftheday.ui.App
import kotlinx.android.synthetic.main.fragment_layout.*
import javax.inject.Inject

class BaseFragment : Fragment() {

    companion object {
        private const val DATE = "date"
        fun newInstance(fragmentNumber: Int) = BaseFragment().apply {
            arguments = Bundle().apply {
                putInt(DATE, fragmentNumber)
            }
        }
    }

    @Inject
    lateinit var viewModel: FragmentViewModel

    private var binding: FragmentLayoutBinding? = null
    private var fragmentNumber = 0
    private val IMAGE_KEY = "image"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        arguments?.let {
            fragmentNumber = it.getInt(DATE)
        }
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_layout, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.numberFragment = fragmentNumber
        super.onViewCreated(view, savedInstanceState)
        val spannable = SpannableStringBuilder(getString(R.string.picture))
        val firstLetter = spannable.length
        binding?.let { bind ->
            bind.inputLayout.setEndIconOnClickListener {
                viewModel.startWiki()
            }
            bind.imageView.setOnClickListener { view ->
                viewModel.animate()
            }
        }
        activity?.let {
            viewModel.getNasaRequestLiveData().observe(it, { value ->
                binding?.let { bind ->
                    if (value.mediaType == IMAGE_KEY) {
                        bind.imageView.load(value.hdurl)
                    } else {
                        bind.imageView.load(value.thumbnail_url)
                    }
                    spannable.insert(spannable.length, value.title)
                    spannable.setSpan(
                        ForegroundColorSpan(Color.RED),
                        0, firstLetter,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    spannable.setSpan(
                        android.graphics.Typeface.BOLD,
                        0, firstLetter,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    spannable.setSpan(
                        android.graphics.Typeface.ITALIC,
                        0, firstLetter,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    spannable.setSpan(
                        RelativeSizeSpan(3f),
                        0, spannable.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    bind.urlTv.text = spannable
                    bind.urlTv.visibility = View.VISIBLE
                    bind.mLayout.transitionToEnd()
                }
            })
            viewModel.getStartWikiLiveData().observe(it, {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${binding?.inputEditText?.text.toString()}")
                })
            })
            viewModel.getStartAnimation().observe(it, { isExpanded ->
                binding?.let { bind ->
                    TransitionManager.beginDelayedTransition(
                        m_layout, ChangeImageTransform().setDuration(1000)
                    )
                    bind.imageView.scaleType =
                        if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
                    bind.imageView.animate()
                        .rotation(if (isExpanded) 90F else 0F)
                        .setDuration(1000)
                        .start()
                }
            })
        }
        viewModel.getNasaRequest(BuildConfig.NASA_API_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onStop() {
        viewModel.onCleared()
        super.onStop()
    }
}