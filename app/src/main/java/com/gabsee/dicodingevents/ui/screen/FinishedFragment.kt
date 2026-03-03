package com.gabsee.dicodingevents.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabsee.dicodingevents.data.Result
import com.gabsee.dicodingevents.databinding.FragmentFinishedBinding
import com.gabsee.dicodingevents.ui.adapter.EventAdapter
import com.gabsee.dicodingevents.ui.viewmodel.FinishedViewModel
import com.gabsee.dicodingevents.ui.viewmodel.ViewModelFactory


class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventAdapter
    private lateinit var viewModel: FinishedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupObservers()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun setupObservers() {
        viewModel.finishedEvents.observe(viewLifecycleOwner) {event ->
            adapter.submitList(event)
        }

        viewModel.syncStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                    if (adapter.itemCount == 0) binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Gagal sinkron: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun setupRecyclerView() {
        adapter = EventAdapter (
            onItemClick = {
                val action = FinishedFragmentDirections.actionNavigationFinishedToDetailFragment(it.id)
                findNavController().navigate(action)
            },
            onBookmarkClick = {
                viewModel.toggleBookmark(it)
            }
        )
        binding.rvFinishedEvent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = this@FinishedFragment.adapter
        }
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FinishedViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}