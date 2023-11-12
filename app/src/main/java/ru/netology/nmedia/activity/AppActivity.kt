package ru.netology.nmedia.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    R.string.error_empty_context,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
                return@let
            }
            it.removeExtra(Intent.EXTRA_TEXT)

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

            val navController = navHostFragment.navController
            navController.navigate(R.id.action_feedFragment_to_newPostFragment, Bundle().apply {
                textArg = text
            })

        }
        requestNotificationsPermission()
    }

    private fun requestNotificationsPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) { // текущая версия андроида
            return
        }

        val permission = Manifest.permission.POST_NOTIFICATIONS // разреш на уведомл

        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) { // провверка есть ли такое разр
            return
        }

        requestPermissions(arrayOf(permission), 1) // запрос на уведомл : диалог
    }
}