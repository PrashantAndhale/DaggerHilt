package com.example.daggerhilt.activities.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Model.MarketStatus
import com.example.daggerhilt.R
import com.example.daggerhilt.Utils.ApiState
import com.example.daggerhilt.Utils.Utility
import com.example.daggerhilt.ViewModel.MainViewModel
import com.example.daggerhilt.databinding.FragmentGlobalMarketBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class GlobalMarketFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var dashboardBinding: FragmentGlobalMarketBinding
    private var mRequestQueue: RequestQueue? = null
    private var mStringRequest: StringRequest? = null

    @Inject
    lateinit var utility: Utility

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardBinding = FragmentGlobalMarketBinding.inflate(inflater, container, false)
        return dashboardBinding.root

    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun getData() {
        mRequestQueue = Volley.newRequestQueue(activity)
        mStringRequest = StringRequest(
            Request.Method.GET, "https://www.nseindia.com/api/marketStatus",
            { response ->
                val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                dashboardBinding.EditTextNiftyValue.setText(
                    jsonObject.getAsJsonObject("indicativenifty50").get("closingValue").toString()
                )
            },
            { error ->
                Log.i("TAG", "Error :$error")
            })

        mRequestQueue!!.add(mStringRequest)
    }

    private fun init() {
        if (utility.isInternetConnected()) {
            mainViewModel.getGlobalIndicatesListingData(
                "https://priceapi.moneycontrol.com/technicalCompanyData/globalMarket/getGlobalIndicesListingData",
                "overview", "W"
            )
        } else Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()

        lifecycleScope.launch {
            mainViewModel._postStateFlow.collect {
                loadData(it)
            }
            mainViewModel._MarketStatusStateFlow.collect {
                loadData(it)
            }
        }
    }

    fun preprocessJson(jsonString: String): String {
        val jsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
        return jsonObject.toString()
    }

    private fun <T> loadData(it: ApiState<T>) {
        when (it) {
            is ApiState.Success<*> -> {
                if (it.data is GlobalMarketData) {
                    val data = it.data as GlobalMarketData
                    bindValues(data)
                }
                if (it.data is MarketStatus) {
                    val data = it.data as MarketStatus
                    dashboardBinding.EditTextNiftyValue.setText(data.indicativenifty.closingValue.toString())
                }
                dashboardBinding.progressBardemo.visibility = View.GONE
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

        }
    }

    private fun bindValues(data: GlobalMarketData) {
        dashboardBinding.EditTextDJ.setText(data.dataList[0].data[0][4])
        dashboardBinding.EditTextSP.setText(data.dataList[0].data[1][4])
        dashboardBinding.EditTextNA.setText(data.dataList[0].data[2][4])

        dashboardBinding.EditTextFT.setText(data.dataList[1].data[0][4])
        dashboardBinding.EditTextCA.setText(data.dataList[1].data[1][4])
        dashboardBinding.EditTextDA.setText(data.dataList[1].data[2][4])

        dashboardBinding.EditTextGN.setText(data.dataList[2].data[0][4])
        dashboardBinding.EditTextSC.setText(data.dataList[2].data[8][4])

        val total: Float = data.dataList[0].data[0][4].toFloat() +
                data.dataList[0].data[1][4].toFloat() +
                data.dataList[0].data[2][4].toFloat() +
                data.dataList[1].data[0][4].toFloat() +
                data.dataList[1].data[1][4].toFloat() +
                data.dataList[1].data[2][4].toFloat() +
                data.dataList[2].data[0][4].toFloat() +
                data.dataList[2].data[8][4].toFloat()

        val formattedValue = (total / 8)
        val mTextView = dashboardBinding.textViewAvarage;
        mTextView.text = "$formattedValue %"
        if (total > 0) {
            mTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorGreen))
        } else {
            dashboardBinding.textViewAvarage.setTextColor(Color.RED)
        }

        dashboardBinding.ButtonCalculateNifty.setOnClickListener {
            var nifity: Float = 0.0F
            val nValue = dashboardBinding.EditTextNiftyValue.text.toString().toFloat()
            val Localnifity = nValue * (formattedValue / 100).toFloat()
            nifity = if (total > 0) {
                nValue + Localnifity
            } else {
                nValue + Localnifity
            }
            dashboardBinding.textViewNiftyAvarage.text = nifity.toString()
        }

        lifecycleScope.launch {
            getData()
           // mainViewModel.getMarketStatus("https://www.nseindia.com/api/marketStatus")
        }
    }
}
