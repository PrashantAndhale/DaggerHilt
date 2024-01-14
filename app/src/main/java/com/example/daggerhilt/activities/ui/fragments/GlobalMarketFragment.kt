package com.example.daggerhilt.activities.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Utils.ApiState
import com.example.daggerhilt.Utils.Utility
import com.example.daggerhilt.ViewModel.MainViewModel
import com.example.daggerhilt.databinding.FragmentDashboardBinding
import com.example.daggerhilt.databinding.FragmentGlobalMarketBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class GlobalMarketFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var dashboardBinding: FragmentGlobalMarketBinding

    @Inject
    lateinit var utility: Utility
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = FragmentGlobalMarketBinding.inflate(inflater, container, false)
        return dashboardBinding.root
        init()
    }

    private fun init() {
        if (utility.isInternetConnected())
            mainViewModel.getGlobalIndicatesListingData(
                "https://priceapi.moneycontrol.com/technicalCompanyData/globalMarket/getGlobalIndicesListingData",
                "overview", "W"
            )
        else Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
        lifecycleScope.launch {
            mainViewModel._postStateFlow.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as GlobalMarketData

                        dashboardBinding.progressBardemo.visibility = View.GONE
                        Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    }

                    is ApiState.Empty -> {

                    }

                    is ApiState.Loading -> {
                        dashboardBinding.progressBardemo.visibility = View.VISIBLE
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(activity, it.msg.toString(), Toast.LENGTH_LONG)
                            .show()
                        dashboardBinding.progressBardemo.visibility = View.GONE
                    }

                    else -> {

                    }
                }
            }
        }
    }
}
