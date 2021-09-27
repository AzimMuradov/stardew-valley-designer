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

package me.azimmuradov.svc.cartographer

import me.azimmuradov.svc.cartographer.SvcBehaviour.*


data class SvcBehaviour(
    val onOutOfBounds: OnOutOfBounds,
    val onConflict: OnConflict,
    val onDisallowed: OnDisallowed,
    val onBetweenLayersConflict: OnBetweenLayersConflict,
) {
    enum class OnOutOfBounds {
        SKIP,
    }

    enum class OnConflict {
        SKIP,
        OVERWRITE,
    }

    enum class OnDisallowed {
        SKIP,
    }

    enum class OnBetweenLayersConflict {
        SKIP,
        OVERWRITE,
    }
}


object DefaultSvcBehaviour {

    val skipper: SvcBehaviour = SvcBehaviour(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.SKIP,
        onDisallowed = OnDisallowed.SKIP,
        onBetweenLayersConflict = OnBetweenLayersConflict.SKIP,
    )

    val rewriter: SvcBehaviour = SvcBehaviour(
        onOutOfBounds = OnOutOfBounds.SKIP,
        onConflict = OnConflict.OVERWRITE,
        onDisallowed = OnDisallowed.SKIP,
        onBetweenLayersConflict = OnBetweenLayersConflict.OVERWRITE,
    )
}