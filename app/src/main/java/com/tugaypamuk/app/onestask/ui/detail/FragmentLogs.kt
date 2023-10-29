package com.tugaypamuk.app.onestask.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import com.tugaypamuk.app.onestask.databinding.FragmentLogsBinding
import com.tugaypamuk.app.onestask.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogs : BaseFragment<FragmentLogsBinding>() {
    private lateinit var viewModel : LogsViewModel
    override fun getFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLogsBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[LogsViewModel::class.java]
        setupRecyclerview()

    }

    private fun setupRecyclerview(){
        viewModel.getLogsFromDatabase()
        viewModel.logs.observe(viewLifecycleOwner){ logs ->
            val adapter = LogsAdapter()
            binding.fragmentLogsRecyclerview.adapter = adapter
            binding.fragmentLogsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            adapter.list = logs as MutableList<OnesLogEntitiy>
        }

    }

}