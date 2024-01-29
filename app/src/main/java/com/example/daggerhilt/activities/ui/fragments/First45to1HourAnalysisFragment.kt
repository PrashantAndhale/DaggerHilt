package com.example.daggerhilt.activities.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daggerhilt.databinding.FragmentFirst45to1HourAnalysisBinding

class First45to1HourAnalysisFragment : Fragment() {
    var prevoiusDayClose = 0
    var openMarketPrice = 0
    var difference = 0
    var calculatedPercentageValue = 0.0
    private lateinit var binding: FragmentFirst45to1HourAnalysisBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirst45to1HourAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        init();
    }

    private fun init() {
        binding.ButtonCalculateNiftyMarket.setOnClickListener {
            if (!TextUtils.isEmpty(binding.EditTextPrevioidDayCloseNiftyValue.text) && !TextUtils.isEmpty(
                    binding.EditTextOpeningNiftyValue.text
                )
            ) {
                prevoiusDayClose =
                    binding.EditTextPrevioidDayCloseNiftyValue.text.toString().toInt()
                openMarketPrice = binding.EditTextOpeningNiftyValue.text.toString().toInt()

                difference = prevoiusDayClose - openMarketPrice

                if (difference > 0) {
                    calculateBullishMarket();
                } else {
                    calculateBearishMarket()
                }
            }
        }

    }

    private fun calculateBearishMarket() {
        calculatedPercentageValue = prevoiusDayClose * (0.25 / 100)
        if (calculatedPercentageValue <= difference) {
            binding.tvResult.text = "Normal Bull Market-->$difference"
            return
        }

        calculatedPercentageValue = prevoiusDayClose * (0.5 / 100)
        if (calculatedPercentageValue <= difference) {
            binding.tvResult.text = "Bull Market-->$difference"
            return
        }
        calculatedPercentageValue = prevoiusDayClose * (0.75 / 100)
        if (calculatedPercentageValue <= difference) {
            binding.tvResult.text = "Very Bull Market-->$difference"
            return
        }
        calculatedPercentageValue = prevoiusDayClose * (1.0 / 100)
        if (calculatedPercentageValue <= difference) {
            binding.tvResult.text = "Fake Bull Market-->$difference"
            return
        }
    }

    private fun calculateBullishMarket() {


    }


}