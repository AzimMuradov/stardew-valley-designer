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

package me.azimmuradov.svc.engine.llsvc

import me.azimmuradov.svc.engine.llsvc.SvcBehaviour.OnBetweenLayersConflict
import me.azimmuradov.svc.engine.llsvc.layer.MutableSvcLayerBehaviour
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayerBehaviour


interface SvcBehaviour : SvcLayerBehaviour {

    val onBetweenLayersConflict: OnBetweenLayersConflict


    enum class OnBetweenLayersConflict {
        SKIP,
        OVERWRITE,
    }
}


interface MutableSvcBehaviour : SvcBehaviour, MutableSvcLayerBehaviour {

    override var onBetweenLayersConflict: OnBetweenLayersConflict
}