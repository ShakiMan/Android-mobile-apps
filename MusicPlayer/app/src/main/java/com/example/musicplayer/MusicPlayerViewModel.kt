package com.example.musicplayer

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.song.Song

class MusicPlayerViewModel : ViewModel() {
    private var songIndex: Int = -1
    private var songsList: List<Song> = listOf()
    private val currentSong = MutableLiveData<Song>()
    private val currentPlayer = MutableLiveData<MediaPlayer>()
    var isPlaying: Boolean = false

    fun getSong(): LiveData<Song> {
        return currentSong
    }

    fun getPlayer(): LiveData<MediaPlayer> {
        return currentPlayer
    }

    fun setSongsList(context: Context, songs: List<Song>) {
        this.songsList = songs
        songIndex = if (songs.isNotEmpty()) {
            0
        } else {
            -1
        }
        setSong(context, songIndex)
    }

    fun playerIsReadyToPlay(): Boolean {
        return songIndex != -1
    }

    fun nextSong(context: Context) {
        if (songsList.isNotEmpty()) {
            val nextIndex: Int = (songIndex + 1) % songsList.size
            setSong(context, nextIndex)
        }
    }

    fun previousSong(context: Context) {
        if (songsList.isNotEmpty()) {
            val previousIndex: Int = if (songIndex - 1 >= 0) {
                songIndex - 1
            } else {
                songsList.size - 1
            }
            setSong(context, previousIndex)
        }
    }

    private fun setSong(context: Context, index: Int) {
        if (0 <= index && index <= songsList.size) {
            currentSong.value = songsList[index]
            currentPlayer.value = MediaPlayer.create(context, songsList[index].songRef)
            songIndex = index
        }
    }

}