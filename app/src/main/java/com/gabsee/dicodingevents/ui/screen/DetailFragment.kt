package com.gabsee.dicodingevents.ui.screen

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gabsee.dicodingevents.R
import com.gabsee.dicodingevents.data.local.entity.EventEntity
import com.gabsee.dicodingevents.databinding.FragmentDetailBinding
import com.gabsee.dicodingevents.ui.viewmodel.DetailViewModel
import com.gabsee.dicodingevents.ui.viewmodel.ViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val eventId = arguments?.getInt("id") ?: 0

        viewModel.getEventDetail(eventId).observe(viewLifecycleOwner) { event ->
            event?.let { displayEventDetail(it) }
        }
    }

    private fun displayEventDetail(event: EventEntity) {
        with(binding) {
            Glide.with(requireContext())
                .load(event.mediaCover)
                .into(ivEventImage)
            tvDetailTitle.text = event.title
            tvOwnerName.text = getString(R.string.event_owner, event.organizer)

            chipCategory.text = event.category
            tvCityName.text = event.cityName
            tvDetailTime.text = getString(R.string.event_time_range, event.beginTime, event.endTime)
            tvDetailQuota.text = getString(R.string.event_quota, event.quota - event.registrants)

            tvDetailDescription.text = HtmlCompat.fromHtml(
                event.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            tvDetailDescription.movementMethod = LinkMovementMethod.getInstance()

            val icon = if (event.isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            ivBookmarkIcon.setImageResource(icon)

            btnBookmark.setOnClickListener {
                viewModel.toggleBookmark(event)
                val message = if (!event.isBookmarked) {
                    getString(R.string.message_bookmarked)
                } else {
                    getString(R.string.message_unbookmarked)
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

            btnOpenLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, event.link.toUri())
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
