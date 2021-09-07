/*
 * Copyright 2021-2021 Azim Muradov
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
 */

package me.azimmuradov.svc.engine.rectmap

import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnConflict
import me.azimmuradov.svc.engine.rectmap.RectMapBehaviour.OnOutOfBounds


/**
 * Behaviour of a rectangular map.
 */
interface RectMapBehaviour {

    val onOutOfBounds: OnOutOfBounds

    val onConflict: OnConflict


    enum class OnOutOfBounds {
        SKIP,
    }

    enum class OnConflict {
        SKIP,
        OVERWRITE,
    }
}


/**
 * Mutable behaviour of a rectangular map.
 */
interface MutableRectMapBehaviour : RectMapBehaviour {

    override var onOutOfBounds: OnOutOfBounds

    override var onConflict: OnConflict
}