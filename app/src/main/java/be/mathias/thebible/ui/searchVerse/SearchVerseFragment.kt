package be.mathias.thebible.ui.searchVerse

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import be.mathias.thebible.database.BibleDatabase
import be.mathias.thebible.databinding.FragmentSearchVerseBinding
import be.mathias.thebible.ui.VerseApiStatus
import com.google.android.material.snackbar.Snackbar

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

        handleClickableViews()

    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun handleClickableViews() {
        binding.bookName.setOnItemClickListener { _, _, _, _ ->
            binding.searchVerse.isEnabled = true
        }

        binding.searchVerse.setOnClickListener {
            //do request
            viewModel.getVerse(
                binding.bookName.text.toString(),
                binding.chapter.text.toString().toInt(),
                binding.verse.text.toString().toInt()
            )

            viewModel.verse.observe(viewLifecycleOwner) {
                binding.textVerse.text = it?.text
            }

            viewModel.status.observe(viewLifecycleOwner) {
                if (it == VerseApiStatus.DONE) {
                    binding.fab.visibility = View.VISIBLE
                    hideKeyboard()
                }
            }

            binding.fab.setOnClickListener {
                val id = viewModel.id(binding.bookName.text.toString(), binding.verse.text.toString().toInt(), binding.chapter.text.toString().toInt())
                id.observe(viewLifecycleOwner) {
                    viewModel.update(it) //update method from room database
                }

                viewModel.status.observe(viewLifecycleOwner) {
                    Snackbar.make(binding.root, "liked verse!", Snackbar.LENGTH_SHORT).show()

                }
            }
        }
    }
}