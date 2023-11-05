package com.example.birthdayobligatorisk

import android.content.res.Configuration
import android.os.Bundle
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

class SecondFragment : Fragment() {

    private lateinit var viewModel: PersonsViewModel
    private lateinit var adapter: MyAdapter<Person>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Opret adapteren med en tom liste i starten
        adapter = MyAdapter(emptyList()) { position ->
            // Implementer handling, når et element i RecyclerView klikkes
            // Du kan få adgang til det klikkede element ved at bruge adapter.getItem(position)
        }

        recyclerView.adapter = adapter

        // Initialiser PersonsViewModel
        viewModel = ViewModelProvider(this).get(PersonsViewModel::class.java)

        // Observer for at hente data fra ViewModel og opdatere RecyclerView
        viewModel.personsLiveData.observe(viewLifecycleOwner, { persons ->
            adapter.updateData(persons)
        })

        // Hent data fra REST API og opdater ViewModel ved behov
        viewModel.reload()

        return view
    }
}