package ru.netology.nmedia.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val channelId = "server"

    override fun onCreate() { //канал где будет отобр уведомл
        /**
        зарегистрировать канал в системе
         **/
        super.onCreate()
        val name = getString(R.string.channel_remote_name) // название канала
        val descriptionText = getString(R.string.channel_remote_description) // описание канала
        val importance = NotificationManager.IMPORTANCE_DEFAULT // значение важности
        val channel =
            NotificationChannel(channelId, name, importance).apply { // регистрируем канал
                description = descriptionText
            }
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // отправляем  на регистрацию в систему
        manager.createNotificationChannel(channel)
        // после этого появляется свой канал для отправки уведомления
    }

    override fun onMessageReceived(message: RemoteMessage) { // обрабатывает получение сообщения в сервисе Firebase Cloud Messaging (FCM)
        /**
        сервис получает сообщение с действием "LIKE", он обрабатывает это событие с помощью функции handleLike ->
        создаёт и отображает уведомление пользователю.
         **/
        message.data["action"]?.let { actionVal ->
            val listAction = Actions.values().map { it.name }
            if (!listAction.contains(actionVal)) {
                println("такого значения нет")
                return
            }

            when (Actions.valueOf(actionVal)) {
                Actions.LIKE -> handLike(Gson().fromJson(message.data["content"], Like::class.java))
                Actions.POST -> handPost(Gson().fromJson(message.data["content"], PostNotification::class.java))
            }

        }
        println(Gson().toJson(message))
    }

    private fun handLike(like: Like) { // показать  уведомл
        /**
        Устанавливает маленький значок уведомления через setSmallIcon.
        Устанавливает заголовок содержимого через setContentTitle, который использует строку notification_user_liked из ресурсов и включает userName и postAuthor из содержимого события "лайка".
        Устанавливает приоритет уведомления через setPriority с использованием стандартного приоритета PRIORITY_DEFAULT.
        Строит уведомление через метод build().
        Затем она отправляет уведомление с помощью NotificationManagerCompat, вызывая метод notify с двумя аргументами:

        Первый аргумент — это случайное число, сгенерированное функцией Random.nextInt, которое используется как ID уведомления и гарантирует, что каждое уведомление имеет уникальный ID (в данном случае от 0 до 100,000).
        Второй аргумент — это объект уведомления, который был создан ранее.
         **/
        val intent = Intent(this, AppActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_add_24)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .setContentText(getString(R.string.notification_user_liked, like.userName, like.postAuthor))
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this)
                .notify(Random.nextInt(100_000), notification) //а есть ли разрешение на уведомл ?
        }
    }
    private fun handPost(post: PostNotification) { // показать  уведомл

        val intent = Intent(this, AppActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_boy)
            .setContentIntent(pi)
            .setAutoCancel(true)
            .setContentText(getString(R.string.notification_user_published, post.userName, post.postText))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(post.postText))
//            .setStyle(
//                NotificationCompat.InboxStyle()
//                    .addLine("Re: Planning")
//                    .addLine("Delivery on its way")
//                    .addLine("Follow-up")
//            )
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this)
                .notify(Random.nextInt(100_000), notification) //а есть ли разрешение на уведомл ?
        }
    }


    override fun onNewToken(token: String) {
        println(token)
    }

    enum class Actions {
        LIKE, POST
    }

    data class Like(
        val userId: Int,
        val userName: String,
        val postId: Int,
        val postAuthor: String,
        val postText : String

    )
    data class PostNotification(

        val userId: Int,
        val userName: String,
        val postId: Int,
        val postAuthor: String,
        val postText : String
    )

}