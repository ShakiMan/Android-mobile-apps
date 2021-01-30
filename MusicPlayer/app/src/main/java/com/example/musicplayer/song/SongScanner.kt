package com.example.musicplayer.song

import com.example.musicplayer.R

class SongScanner : Scanner {
    override fun getSongReferences(): List<Song> {
        val songs = mutableListOf<Song>()
        songs.add(Song("Hollywood", "LA Vision & Gigi D'Agostino",R.raw.hollywood, R.drawable.hollywood))
        songs.add(Song("Brother Louie", "VIZE, Imanbek & Dieter Bohlen", R.raw.brother_louie, R.drawable.brother_louie))
        songs.add(Song("Fight Back", "Ummet Ozcan x Harris & Ford", R.raw.fight_back, R.drawable.fight_back))
        songs.add(Song("On N On", "VINAI", R.raw.on_n_on, R.drawable.on_n_on))
        songs.add(Song("Dolly Song", "VIZE x LEONY", R.raw.dolly_song, R.drawable.dolly_song))
        return songs
    }
}