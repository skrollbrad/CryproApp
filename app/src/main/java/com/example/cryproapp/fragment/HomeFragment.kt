package com.example.cryproapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import com.example.cryproapp.R
import com.example.cryproapp.adapter.TopLossGainPagerAdapter
import com.example.cryproapp.adapter.TopMarketAdapter
import com.example.cryproapp.apis.ApiInterface
import com.example.cryproapp.apis.ApiUtilites
import com.example.cryproapp.databinding.ActivityMainBinding
import com.example.cryproapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencyList()

        setTablayout()



        return binding.root
    }

    private fun setTablayout() {


        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE

                }
                else {
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })
    TabLayoutMediator(binding.tabLayout, binding.contentViewPager) {
        tab,position ->
        var title = if (position == 0) {
            "Top Gainers"
        }
        else {
            "Top Losers"
        }
        tab.text = title
    }.attach()


    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilites.getInstance().create(ApiInterface::class.java).getMarketData()

            withContext(Dispatchers.Main) {
                binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(requireContext(),res.body()!!.data.cryptoCurrencyList)



            }

            Log.d("Shubi","getTopcurrencyList:  ${res.body()!!.data.cryptoCurrencyList}")
        }
    }

}