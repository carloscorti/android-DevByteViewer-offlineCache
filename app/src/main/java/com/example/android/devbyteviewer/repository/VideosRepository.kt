/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideoDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class VideosRepository(private val database: VideoDatabase) {
    val videos = Transformations.map(database.videoDao.getAllVideos()) {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            try {
                var videoPlayList = Network.devbytes.getPlaylist()
                database.videoDao.insert(*videoPlayList.asDatabaseModel())
            } catch (e: Exception) {
                Timber.i("error: ${e.message}")
            }
        }
    }
}

