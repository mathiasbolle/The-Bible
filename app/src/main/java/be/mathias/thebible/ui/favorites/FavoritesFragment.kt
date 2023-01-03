package be.mathias.thebible.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.databinding.FragmentFavoritesBinding
import be.mathias.thebible.ui.home.HomeFactory
import be.mathias.thebible.ui.home.HomeViewModel

/**
 * A simple [Fragment] subclass that list all the favorite verses from the user.
 */
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: FavoriteVerseListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val appContext = requireNotNull(this.activity).application
        val dataSource =
            BibleDatabase.getInstance(requireNotNull(this.activity).application).databaseVerseDao

        val viewModelFactory = FavoriteFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoritesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListview()
    }

    private fun setupListview() {
        binding.versesRecycleview.layoutManager = LinearLayoutManager(activity)

        adapter = FavoriteVerseListAdapter()
        binding.versesRecycleview.adapter = adapter


        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}