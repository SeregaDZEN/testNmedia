package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnClickListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.AddPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.functions.LongArg
import ru.netology.nmedia.functions.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class AddPostFragment : Fragment() {


    private val viewModel: PostViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddPostBinding.inflate(layoutInflater, container, false)

        arguments?.idArg?.let {
            val id = requireArguments().idArg
            viewModel.dataOfClassPost.observe(viewLifecycleOwner) { posts ->
                val post: Post? = posts.find { it.id == id }
                if (post == null) {
                    findNavController().navigateUp()
                    return@observe
                }
                PostViewHolder(binding.singlePost, onClickListener).bind(post)

            }
        }

        return binding.root
    }

    private val onClickListener = object : OnClickListener {
        override fun onEdit(post: Post) {
            viewModel.edit(post)
            findNavController().navigate(R.id.action_addPostFragment_to_newPostFragment,
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
            val chooser =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(chooser)
            viewModel.share(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            findNavController().navigateUp()
        }

        override fun playVideo(post: Post) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.uRl))

            val chooser =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(chooser)
        }
    }

    companion object {
        var Bundle.idArg: Long? by LongArg
    }

}







