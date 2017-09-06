/*
 *    Copyright 2017 Surasek Nusati <surasek@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.skyousuke.ytdlgui.listener;

import com.esotericsoftware.minlog.Log;
import io.github.skyousuke.ytdlgui.controller.MainPageController;
import io.github.skyousuke.ytdlgui.utils.VideoFormatUtils;
import io.github.skyousuke.ytdlgui.video.VideoFormat;

import java.util.ArrayList;
import java.util.List;

public class VideoFormatFinderListener extends DottedProcessListener {

    private List<VideoFormat> videoFormats = new ArrayList<>();

    public VideoFormatFinderListener(MainPageController pageController) {
        super(pageController.getStatusLabel());
    }

    @Override
    public void onStart() {
        startDot();
    }

    @Override
    public void onOutput(String message) {
        if (VideoFormatUtils.isFormatInfo(message)) {
            VideoFormat videoFormat = VideoFormatUtils.getVideoFormat(message);
            if (videoFormat != null)
                videoFormats.add(videoFormat);
        }
        Log.debug(message);
    }

    @Override
    public void onExit(int returnValue) {
        super.onExit(returnValue);
        for (VideoFormat videoFormat : videoFormats) {
            Log.debug(videoFormat.toString());
        }
        cancelDot();
    }

    public List<VideoFormat> getVideoFormats() {
        return videoFormats;
    }
}
