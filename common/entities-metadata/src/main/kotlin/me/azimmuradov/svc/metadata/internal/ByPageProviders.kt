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

package me.azimmuradov.svc.metadata.internal

import me.azimmuradov.svc.metadata.*
import me.azimmuradov.svc.metadata.EntityPage.*
import me.azimmuradov.svc.metadata.EntityPage.Companion.UNIT


internal fun common(index: Int) = metadata(
    page = CommonObjects,
    index,
    w = 1, h = 1
)

internal fun craftable(index: Int) = metadata(
    page = Craftables,
    index,
    w = 1, h = 2
)

internal fun furniture(x: Int, y: Int, w: Int, h: Int) = metadata(
    page = Furniture,
    x, y,
    w, h
)


@Suppress("SameParameterValue")
private fun metadata(page: EntityPage, index: Int, w: Int, h: Int) = metadata(
    page,
    x = index % (page.width / UNIT) * page.grain.w,
    y = index / (page.width / UNIT) * page.grain.h,
    w, h
)

private fun metadata(page: EntityPage, x: Int, y: Int, w: Int, h: Int) = EntityMetadata(
    page,
    sourceOffset = EntityOffset(x, y) * UNIT,
    sourceSize = EntitySize(w, h) * UNIT
)
