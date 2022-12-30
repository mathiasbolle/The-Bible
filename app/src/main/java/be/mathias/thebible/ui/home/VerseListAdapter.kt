package be.mathias.thebible.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.mathias.thebible.databinding.VerseItemBinding
import be.mathias.thebible.domain.Verse

class VerseListAdapter() : ListAdapter<Verse, VerseListAdapter.ViewHolder>(SessionDiffCallback()) {
    lateinit var binding: VerseItemBinding

    inner class ViewHolder(val binding: VerseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        binding = VerseItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val verse = getItem(position)

        holder.binding.authorFirstLetter.text = verse.bookName[0].toString()
        holder.binding.verseContent.text = verse.text
        holder.binding.chapterVerse.text = String.format("%d;%d", verse.chapter, verse.verseNumber)
    }
}

class SessionDiffCallback : DiffUtil.ItemCallback<Verse>() {
    override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem.verseNumber == newItem.verseNumber && oldItem.chapter == newItem.chapter && oldItem.bookName.equals(
            newItem.bookName
        )
    }

    override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem == newItem
    }
}