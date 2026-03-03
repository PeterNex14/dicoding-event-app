package com.gabsee.dicodingevents.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.gabsee.dicodingevents.databinding.FragmentHomeBinding
import com.gabsee.dicodingevents.ui.adapter.EventAdapter
import com.gabsee.dicodingevents.ui.viewmodel.HomeViewModel
import com.gabsee.dicodingevents.ui.viewmodel.ViewModelFactory
import com.gabsee.dicodingevents.data.Result
import com.gabsee.dicodingevents.ui.adapter.HorizontalEventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventAdapter
    private lateinit var horizontalAdapter: HorizontalEventAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupHorizontalRecyclerView()
        setupSearch()
        setupObservers()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun setupHorizontalRecyclerView() {
        horizontalAdapter = HorizontalEventAdapter {
            val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(it.id)
            findNavController().navigate(action)
        }

        binding.rvHorizontalUpcoming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = horizontalAdapter

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }

        viewModel.limitedUpcomingEvent.observe(viewLifecycleOwner) { event ->
            horizontalAdapter.submitList(event)
        }
    }

    private fun setupObservers() {
        viewModel.allEvents.observe(viewLifecycleOwner) { event ->
            adapter.submitList(event)
        }

        viewModel.upcomingCount.observe(viewLifecycleOwner) { count ->
            binding.tvUpcomingCount.text = count.toString()
        }

        viewModel.finishedCount.observe(viewLifecycleOwner) {count ->
            binding.tvFinishedCount.text = count.toString()
        }

        viewModel.syncStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Hanya tampilkan bar tengah jika layar masih kosong total
                    if (adapter.itemCount == 0) binding.progressBar.visibility = View.VISIBLE
                    binding.swipeRefresh.isRefreshing = true
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(context, "Gagal sinkron: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText ?: "")
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = EventAdapter (
            onItemClick = {
                val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(it.id)
                findNavController().navigate(action)
            },
            onBookmarkClick = {
                viewModel.toggleBookmark(it)
            }
        )

        binding.rvEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = this@HomeFragment.adapter
        }
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}