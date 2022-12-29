package be.mathias.thebible.ui.searchVerse

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.databinding.FragmentSearchVerseBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SearchVerseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchVerseFragment : Fragment() {
    private lateinit var binding: FragmentSearchVerseBinding
    private lateinit var viewModel: SearchVerseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchVerseBinding.inflate(inflater, container, false)


        val appContext = requireNotNull(this.activity).application
        val dataSource =
            BibleDatabase.getInstance(requireNotNull(this.activity).application).databaseVerseDao

        val viewModelFactory = SearchVerseFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchVerseViewModel::class.java)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookName.setOnItemClickListener { adapterView, _, i, l ->
            binding.searchVerse.isEnabled = true
        }

        binding.searchVerse.setOnClickListener {
            Toast.makeText(context, binding.bookName.text, Toast.LENGTH_SHORT).show()
            //do request!
            viewModel.getVerse(
                binding.bookName.text.toString(),
                binding.chapter.text.toString().toInt(),
                binding.verse.text.toString().toInt()
            ).observe(viewLifecycleOwner) {

            }


        }
    }
}