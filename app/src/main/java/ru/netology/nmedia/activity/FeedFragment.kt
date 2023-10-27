package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnClickListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {


    private val viewModel: PostViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(layoutInflater, container, false)


        val adapter = PostsAdapter(onClickListener)
        binding.listRC.adapter = adapter
        viewModel.dataOfClassPost.observe(viewLifecycleOwner) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) binding.listRC.smoothScrollToPosition(0)  // поднять скрол вверх
            }
        }

        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }
        return binding.root
    }


    private val onClickListener = object : OnClickListener {

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArg = post.content
                }
            )

        }
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, post.content)
            }
            val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(chooser)
            viewModel.share(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun playVideo(post: Post) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.uRl))

            val chooser = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(chooser)
        }

        override fun clickRoot(post: Post) {
            findNavController().navigate(
                R.id.action_feedFragment_to_addPostFragment, bundleOf("idArg" to  post.id)
//                Bundle().apply {
//                    idArg = post.id
//                }
            )
        }
    }
}
