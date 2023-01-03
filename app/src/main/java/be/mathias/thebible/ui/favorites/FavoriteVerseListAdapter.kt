package be.mathias.thebible.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.mathias.thebible.databinding.VerseFavoriteItemBinding
import be.mathias.thebible.databinding.VerseItemBinding
import be.mathias.thebible.domain.Verse
import be.mathias.thebible.ui.home.VerseListAdapter

class FavoriteVerseListAdapter() : ListAdapter<Verse, FavoriteVerseListAdapter.ViewHolder>(VerseDiffCallback()) {
    lateinit var binding: VerseFavoriteItemBinding

    inner class ViewHolder(val binding: VerseFavoriteItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        binding = VerseFavoriteItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val verse = getItem(position)

        holder.binding.authorFirstLetter.text = verse.bookName[0].toString()
        holder.binding.verseContent.text = verse.text
        holder.binding.chapterVerse.text = String.format("%d;%d", verse.chapter, verse.verseNumber)
    }
}

class VerseDiffCallback : DiffUtil.ItemCallback<Verse>() {
    override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem.verseNumber == newItem.verseNumber && oldItem.chapter == newItem.chapter && oldItem.bookName.equals(
            newItem.bookName
        )
    }

    override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
        return oldItem == newItem
    }
}
