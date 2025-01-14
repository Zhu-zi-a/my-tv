package com.lizongying.mytv

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lizongying.mytv.databinding.InfoBinding
import com.lizongying.mytv.models.TVViewModel

class InfoFragment : Fragment() {
    private var _binding: InfoBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler()
    private val delay: Long = 3000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Use a Handler to delay the fragment transaction
        handler.postDelayed(removeRunnable, delay)
    }

    fun show(tvViewModel: TVViewModel) {
        Log.i("", "show")
        binding.textView.text = tvViewModel.title.value
        Glide.with(this)
            .load(tvViewModel.logo.value)
            .into(binding.infoLogo)
        val program = tvViewModel.getProgramOne()
        if (program != null) {
            binding.infoDesc.text = program.name
        }

        handler.removeCallbacks(removeRunnable)
        view?.visibility = View.VISIBLE
        handler.postDelayed(removeRunnable, delay)
    }

    override fun onPause() {
        super.onPause()
        Log.i("", "onPause")
        // Cancel the delayed task when the fragment is paused
        handler.removeCallbacks(removeRunnable)
    }

    private val removeRunnable = Runnable {
        Log.i("", "hide")
        view?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "InfoFragment"
    }
}