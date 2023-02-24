/*
 * Copyright 2021-2023 Azim Muradov
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

package me.azimmuradov.svc.engine.layer

import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.EntityType
import me.azimmuradov.svc.engine.layout.LayoutRules
import me.azimmuradov.svc.engine.rectmap.MutableRectMap
import me.azimmuradov.svc.engine.rectmap.RectMap


interface Layer<out EType : EntityType> : RectMap<Entity<EType>> {

    val layoutRules: LayoutRules
}


interface MutableLayer<EType : EntityType> : Layer<EType>, MutableRectMap<Entity<EType>>
