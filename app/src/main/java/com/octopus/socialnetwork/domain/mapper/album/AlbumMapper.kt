package com.octopus.socialnetwork.domain.mapper.album

import com.octopus.socialnetwork.data.remote.response.dto.album.AlbumDTO
import com.octopus.socialnetwork.data.remote.response.dto.album.AlbumsDto
import com.octopus.socialnetwork.data.remote.response.dto.album.album_photos_list.AlbumPhotosDTO
import com.octopus.socialnetwork.domain.model.album.AlbumDetails
import com.octopus.socialnetwork.domain.model.album.AlbumPhotos
import com.octopus.socialnetwork.domain.model.album.Albums
import com.octopus.socialnetwork.domain.model.album.UserAlbum



fun AlbumDTO.asAlbum(): UserAlbum {
    return UserAlbum(
        albums = emptyList(),
        count = count ?: 0,
        coverPhoto = coverPhoto ?: "",
        offset = offset ?: 0,
        profilePhoto = profilePhoto ?: ""
    )
}

//fun AlbumPhotosDTO.asAlbumPhotos(): AlbumPhotos {
//    return AlbumPhotos(
//        albumDetails = emptyList(),
//        list = emptyList(),
//    )
//}