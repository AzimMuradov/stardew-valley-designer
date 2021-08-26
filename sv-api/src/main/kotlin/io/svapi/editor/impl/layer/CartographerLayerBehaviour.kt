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

package io.svapi.editor.impl.layer


data class CartographerLayerBehaviour(
    val onOutOfBounds: OnOutOfBounds,
    val onDisallowed: OnDisallowed,
    val onInternalConflict: OnInternalConflict,
) {

    enum class OnOutOfBounds {
        // CANCEL,
        SKIP,
    }

    enum class OnDisallowed {
        // CANCEL,
        SKIP,
    }

    enum class OnInternalConflict {
        // CANCEL,
        SKIP,
        OVERWRITE,
    }


    companion object {

        // val canceler: CartographerLayerBehaviour = CartographerLayerBehaviour(
        //     onOutOfBounds = OnOutOfBounds.CANCEL,
        //     onDisallowed = OnDisallowed.CANCEL,
        //     onInternalConflict = OnInternalConflict.CANCEL,
        // )

        val skipper: CartographerLayerBehaviour = CartographerLayerBehaviour(
            onOutOfBounds = OnOutOfBounds.SKIP,
            onDisallowed = OnDisallowed.SKIP,
            onInternalConflict = OnInternalConflict.SKIP,
        )

        val rewriter: CartographerLayerBehaviour = CartographerLayerBehaviour(
            onOutOfBounds = OnOutOfBounds.SKIP,
            onDisallowed = OnDisallowed.SKIP,
            onInternalConflict = OnInternalConflict.OVERWRITE,
        )
    }
}