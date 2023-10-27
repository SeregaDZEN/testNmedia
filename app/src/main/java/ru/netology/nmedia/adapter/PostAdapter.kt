package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.reduceNumber

interface OnClickListener {
    fun playVideo(post: Post) {}

    fun onLike(post: Post) {}

    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
    fun clickRoot(post: Post) {}
}

class PostViewHolder(
    private val binding: CardPostBinding, private val onClickListener: OnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            cardPostText.text = post.content
            buttonShare.text = reduceNumber(post.share)
            textForSee.text = post.views.toString()
            buttonLike.text = reduceNumber(post.likes)



            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.removeInMenu -> {
                                onClickListener.onRemove(post)
                                true
                            }

                            R.id.editInMenu -> {

                                onClickListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            root.setOnClickListener {
                onClickListener.clickRoot(post)
            }

            planet.setOnClickListener {

                onClickListener.playVideo(post)
            }
            floatingIcPlay.setOnClickListener {
                onClickListener.playVideo(post)
            }

            buttonLike.isChecked = post.likedByMe

            buttonLike.setOnClickListener {
                onClickListener.onLike(post)
            }
            buttonShare.isChecked = post.shareByMe

            binding.buttonShare.setOnClickListener {
                onClickListener.onShare(post)
                buttonShare.isChecked = post.shareByMe

            }
        }
    }

}

class PostsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val post = getItem(position)
        holder.bind(post)
    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}




