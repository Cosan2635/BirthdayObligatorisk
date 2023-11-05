package com.example.birthdayobligatorisk

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdayobligatorisk.databinding.FragmentSecondBinding
import com.example.birthdayobligatorisk.models.MyAdapter
import com.example.birthdayobligatorisk.models.Person
import com.example.birthdayobligatorisk.models.PersonsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val personsViewModel: PersonsViewModel by activityViewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personsViewModel.personsLiveData.observe(viewLifecycleOwner) { persons ->
            Log.d("APPLE", persons.toString())

            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons == null) View.GONE else View.VISIBLE

            if (persons != null) {
                val adapter = MyAdapter(persons) { position ->
                    val action =
                        SecondFragmentDirections.actionSecondFragmentTo.... (position)
                    findNavController().navigate(action)
                }
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)
                binding.recyclerView.adapter = adapter
            }
        }
        personsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }
        personsViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            personsViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }
        binding.buttonSort.setOnClickListener {
            when (binding.spinnerPersons.selectedItemPosition) {
                0 -> personsViewModel.sortByName()
                1 -> personsViewModel.sortByAge()
            }
        }
        binding.buttonSort.setOnClickListener {
            val filter = binding.edittextFilterName.text.toString().trim()
            if (filter.isBlank()) {
                binding.edittextFilterName.error = "No title"
                return@setOnClickListener
            }
            personsViewModel.sortByName()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}