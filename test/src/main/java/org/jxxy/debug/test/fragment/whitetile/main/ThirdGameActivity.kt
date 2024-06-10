package cn.lentme.projects.whitetile.main


import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.lentme.projects.whitetile.core.GameSystem
import cn.lentme.projects.whitetile.game.view.TileScreenView
import org.jxxy.debug.test.databinding.ActivityGameThirdBinding

class ThirdGameActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var _binding: ActivityGameThirdBinding
    private lateinit var _gameManager: GameSystem
    var media: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameThirdBinding.inflate(layoutInflater)
        setContentView(TileScreenView(this))
        init()
        val url = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/07/0ce7519d-fcbc-4199-af90-071d1e9921fc.mp3"
        media = MediaPlayer.create(
            this,
            Uri.parse(url)
        )
        media?.isLooping = true
        media?.start()
    }

    private fun init() {

    }

    override fun onDestroy() {
        super.onDestroy()
        media?.release()
    }
}