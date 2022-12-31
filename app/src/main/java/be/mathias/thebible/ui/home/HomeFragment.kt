package be.mathias.thebible.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.mathias.thebible.R
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.databinding.FragmentHomeBinding
import be.mathias.thebible.ui.searchVerse.SearchVerseFactory
import be.mathias.thebible.ui.searchVerse.SearchVerseViewModel

/**
 * A simple [Fragment] subclass that defines the overview of the functionalities of the app.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: VerseListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val appContext = requireNotNull(this.activity).application
        val dataSource =
            BibleDatabase.getInstance(requireNotNull(this.activity).application).databaseVerseDao

        val viewModelFactory = HomeFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_searchVerseFragment)
        }

        binding.buttonFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_favoritesFragment)
        }
        setupListview()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListview() {
        binding.versesRecycleview.layoutManager = LinearLayoutManager(activity)

        adapter = VerseListAdapter()
        //viewmodel!!
        binding.versesRecycleview.adapter = adapter

        viewModel.verses.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}