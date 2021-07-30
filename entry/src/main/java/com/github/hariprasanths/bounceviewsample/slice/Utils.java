/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hariprasanths.bounceviewsample.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Size;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils Class.
 */
public class Utils {
    private static Map<Integer, PixelMap> imageCache = new HashMap<>();

    private Utils() {
        // private constructor added to hide implicit public ones
    }

    /**
     * Used to add image icons inside tab.
     *
     * @param abilitySlice AbilitySlice
     * @param resId integer
     * @param str String
     * @return pixelMap which can be used inside tab to set image icon
     * @throws IOException if there is a input/output error
     * @throws NotExistException if it does not exist
     */
    public static PixelMap createByResourceId(AbilitySlice abilitySlice, int resId, String str)
            throws IOException, NotExistException {
        if (abilitySlice == null) {
            throw new IOException();
        } else if (imageCache.containsKey(resId)) {
            return imageCache.get(resId);
        } else {
            ResourceManager resourceManager = abilitySlice.getResourceManager();
            if (resourceManager != null) {
                Resource resource = resourceManager.getResource(resId);
                if (resource != null) {
                    ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
                    sourceOptions.formatHint = str;
                    ImageSource create = ImageSource.create(readResource(resource), sourceOptions);
                    resource.close();
                    if (create != null) {
                        ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
                        decodingOptions.desiredSize = new Size(0, 0);
                        decodingOptions.desiredRegion = new ohos.media.image.common.Rect(0, 0, 0, 0);
                        decodingOptions.desiredPixelFormat = PixelFormat.ARGB_8888;
                        PixelMap pixelMap = create.createPixelmap(decodingOptions);
                        imageCache.put(resId, pixelMap);
                        return pixelMap;
                    }
                    throw new FileNotFoundException();
                }
                throw new IOException();
            }
            throw new IOException();
        }
    }

    private static byte[] readResource(Resource resource) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byteArr = new byte[1024];
        while (true) {
            try {
                int read = resource.read(byteArr, 0, 1024);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(byteArr, 0, read);
            } catch (IOException e) {
                //
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Creates Element with given color.
     *
     * @param color used to create Rectangle Element with this color
     * @return this created element
     */
    public static Element buildDrawableByColor(int color) {
        ShapeElement drawable = new ShapeElement();
        drawable.setShape(ShapeElement.RECTANGLE);
        drawable.setRgbColor(RgbColor.fromArgbInt(color));
        return drawable;
    }
}
