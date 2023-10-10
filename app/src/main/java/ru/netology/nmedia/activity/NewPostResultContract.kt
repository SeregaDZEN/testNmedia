package ru.netology.nmedia.activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

object NewPostResultContract : ActivityResultContract<String?, String?>() { // тут поменяли на "Стринг?" вместо Юнит

    override fun createIntent(context: Context, input: String?): Intent =
        Intent(context, NewPostActivity::class.java).putExtra(Intent.EXTRA_TEXT,input)


    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        intent?.getStringExtra(Intent.EXTRA_TEXT)
}