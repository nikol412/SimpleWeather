package ru.nikol.simplyweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.nikol.simplyweather.databinding.OverviewFragmentBinding
import ru.nikol.simplyweather.db.WeatherDB


class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(OverviewViewModel::class.java)
        GlobalScope.launch {
            viewModel.db = WeatherDB(context!!)
        }
        val binding = OverviewFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.overviewVM = this.viewModel
        viewModel.image.value = R.drawable.sun

        viewModel.getCashedWeather()
        viewModel.getWeather()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OverviewViewModel::class.java)

    }


}
