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

package me.azimmuradov.svc.engine.llsvc.layer

import me.azimmuradov.svc.engine.llsvc.layer.SvcLayoutsProvider.SvcLayoutType.BigShed
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayoutsProvider.SvcLayoutType.Shed
import me.azimmuradov.svc.engine.llsvc.layer.layouts.BigShedLayout
import me.azimmuradov.svc.engine.llsvc.layer.layouts.ShedLayout


object SvcLayoutsProvider {

    fun svcLayoutOf(type: SvcLayoutType): SvcLayout = when (type) {
        Shed -> ShedLayout
        BigShed -> BigShedLayout
    }


    enum class SvcLayoutType {
        Shed,
        BigShed,
    }
}