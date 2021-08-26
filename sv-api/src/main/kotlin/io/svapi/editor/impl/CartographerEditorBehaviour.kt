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

package io.svapi.editor.impl

import io.svapi.editor.impl.CartographerEditorBehaviour.OnConflict
import io.svapi.editor.impl.layer.CartographerLayerBehaviour.OnInternalConflict


data class CartographerEditorBehaviour(
    val onConflict: OnConflict,
) {

    enum class OnConflict {
        SKIP,
        OVERWRITE,
    }


    companion object {

        val skipper: CartographerEditorBehaviour = CartographerEditorBehaviour(
            onConflict = OnConflict.SKIP,
        )

        val rewriter: CartographerEditorBehaviour = CartographerEditorBehaviour(
            onConflict = OnConflict.OVERWRITE,
        )
    }
}


fun OnConflict.toOnInternalConflict(): OnInternalConflict = when (this) {
    OnConflict.SKIP -> OnInternalConflict.SKIP
    OnConflict.OVERWRITE -> OnInternalConflict.OVERWRITE
}