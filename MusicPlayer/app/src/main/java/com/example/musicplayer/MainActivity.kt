package com.example.musicplayer

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.song.SongScanner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var musicPlayerViewModel: MusicPlayerViewModel
    private val songScanner: SongScanner = SongScanner()
    private lateinit var musicPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private val seekBarRefreshPeriod = 350L
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        musicPlayerViewModel = ViewModelProvider(this).get(MusicPlayerViewModel::class.java)

        if (!musicPlayerViewModel.playerIsReadyToPlay()) {
            musicPlayerViewModel.setSongsList(this, songScanner.getSongReferences())
        }

        musicPlayer = musicPlayerViewModel.getPlayer().value!!
        observeViewModel()
        prepareNewSong()
        setSeekBarFunctionality()
        setPlayButtonListener()
        setNextButtonListener()
        setPreviousButtonListener()
        setRewindButtonListener()
        setForwardButtonListener()
        setPlayButton()
    }

    private fun observeViewModel() {
        musicPlayerViewModel.getSong().observe(this, {
            coverIV.setImageResource(it.coverRef)
            titleTV.text = it.title
            authorTV.text = it.author
        })

        musicPlayerViewModel.getPlayer().observe(this, {
            musicPlayer = it
        })
    }

    private fun setSeekBarFunctionality() {
        songSeekBar.progressDrawable.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
        songSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        runnable = Runnable {
            songSeekBar.progress = musicPlayer.currentPosition
            lefttime_TV.text = createTimeLabel(musicPlayer.currentPosition)
            righttime_TV.text = createTimeLabel(musicPlayer.duration)
            handler.postDelayed(runnable, seekBarRefreshPeriod)
        }
        handler.postDelayed(runnable, seekBarRefreshPeriod)
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    private fun setNextButtonListener() {
        next_btn.setOnClickListener {
            musicPlayer.stop()
            musicPlayerViewModel.nextSong(this)
            prepareNewSong()
        }
    }

    private fun setPreviousButtonListener() {
        previous_btn.setOnClickListener {
            musicPlayer.stop()
            musicPlayerViewModel.previousSong(this)
            prepareNewSong()
        }
    }

    private fun prepareNewSong() {
        if (musicPlayerViewModel.isPlaying) {
            musicPlayer.start()
        }
        songSeekBar.max = musicPlayer.duration

        musicPlayer.setOnCompletionListener {
            musicPlayerViewModel.nextSong(this)
            if (musicPlayerViewModel.isPlaying) {
                musicPlayer.start()
            }

            songSeekBar.max = musicPlayer.duration
            songSeekBar.progress = 0
        }
    }

    private fun setForwardButtonListener() {
        forward_btn.setOnClickListener {
            rewindSong(10)
        }
    }

    private fun setRewindButtonListener() {
        rewind_btn.setOnClickListener {
            rewindSong(-10)
        }
    }

    private fun rewindSong(seconds: Int) {
        val milliseconds = seconds * 1000
        val newPosition = musicPlayer.currentPosition + milliseconds

        if (newPosition > 0 && newPosition < musicPlayer.duration) {
            musicPlayer.seekTo(newPosition)
        } else if (newPosition <= 0) {
            musicPlayer.seekTo(0)
        } else {
            musicPlayer.seekTo(musicPlayer.duration - 1)
        }
    }

    private fun setPlayButtonListener() {
        play_btn.setOnClickListener {
            if (!musicPlayer.isPlaying) {
                musicPlayer.start()
                musicPlayerViewModel.isPlaying = true
                setPlayButton()
            } else {
                musicPlayer.pause()
                musicPlayerViewModel.isPlaying = false
                setPlayButton()
            }
        }
    }

    private fun setPlayButton() {
        if (musicPlayerViewModel.isPlaying) {
            play_btn.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
        } else {
            play_btn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
        }
    }

}