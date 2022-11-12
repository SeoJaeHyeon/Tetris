package com.example.tetris

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder { //서비스객체랑 통신
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {  //서비스호출시 한번만 호출
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.tetris_nintendo)
        mediaPlayer.start()
        mediaPlayer.isLooping = true //노래 무한 루프
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //서비스 호출될 때 노래를 재생시킴
        if(mediaPlayer == null) { //노래 중복 재생 방지
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() { //서비스 종료 명령 받으면 음악 종료
        mediaPlayer.stop()  //노래중지
        mediaPlayer.release() //다른객체와 상호작용 안되게해줌 메모리에서 제거
        super.onDestroy()

    }

}