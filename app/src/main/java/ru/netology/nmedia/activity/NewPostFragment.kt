package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.functions.LongArg
import ru.netology.nmedia.functions.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {



    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: PostViewModel by activityViewModels()
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
        binding.contentNewPost.requestFocus()

       arguments?.textArg?.let {
           binding.contentNewPost.setText(it)
       }

        binding.add.setOnClickListener {

            if (!binding.contentNewPost.text.isNullOrBlank()) {
                val text = binding.contentNewPost.text.toString()
                viewModel.changeContent(text)
                viewModel.save()
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
    companion object {
        var Bundle.textArg: String? by StringArg
    }
}
//забыл уде, что делали чтобы объеденить затем просто переименовал +
//        обработчик нажатия кнопки назад в main2


