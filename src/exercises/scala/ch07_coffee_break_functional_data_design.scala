object model {
    case class User(name: String)

    case class Artist(name: String)

    enum MusicGenre {
        case HeavyMetal
        case Rock
        case Salsa
        case Funk
        case House
    }

    case class Song(name: String, artist: Artist)

    enum PlaylistKind {
        case Curated(curator: User)
        case ArtistBased(artist: Artist)
        case GenreBased(genres: List[MusicGenre])
    }

    case class Playlist(name: String, kind: PlaylistKind, songs: List[Song])
}

import model._
import MusicGenre._
import PlaylistKind._


val ff = Artist("Foo Fighters")
val thisIsFF = Playlist(
    name="This is foo fighters", 
    kind=ArtistBased(artist=ff), 
    songs=List(
        Song("Breakout", artist=ff),
        Song("Learn to fly", artist=ff)
    )
)

val deepFocus = Playlist(
    name="Deep Focus",
    kind=GenreBased(genres=List(
        Funk, 
        House
    )),
    songs=List(
        Song("One More Time", artist=Artist("Daft Punk")),
        Song("Hey Boye Hey Girl", artist=Artist("The Chemical Brothers"))
    )
)

val myPlaylist = Playlist(
    name="Luis Playlist",
    kind=Curated(curator=User("Luis")),
    songs=List.empty
)

def gatherSongs(playlists: List[Playlist], artist: Artist, genre: MusicGenre): List[Song] = {
    playlists.foldLeft(List.empty[Song])((songs, playlist) =>
        val matchingSongs = playlist.kind match {
            case Curated(curator) =>
                playlist.songs.filter(_.artist == artist)
            case ArtistBased(playlistArtist) =>
                if (playlistArtist == artist) playlist.songs else List.empty
            case GenreBased(genres) =>
                if (genres.contains(genre)) playlist.songs else List.empty
        }
        songs.appendedAll(matchingSongs)
    )
}

gatherSongs(List(thisIsFF, deepFocus, myPlaylist), ff, Salsa)
