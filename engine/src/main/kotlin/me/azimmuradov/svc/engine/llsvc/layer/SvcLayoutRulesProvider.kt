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

import me.azimmuradov.svc.engine.llsvc.layer.SvcLayoutRulesProvider.SvcLayoutRulesType.BigShed
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayoutRulesProvider.SvcLayoutRulesType.Shed
import me.azimmuradov.svc.engine.llsvc.layer.layoutrules.BigShedLayoutRules
import me.azimmuradov.svc.engine.llsvc.layer.layoutrules.ShedLayoutRules


object SvcLayoutRulesProvider {

    fun svcLayoutRulesOf(type: SvcLayoutRulesType): SvcLayoutRules = when (type) {
        Shed -> ShedLayoutRules
        BigShed -> BigShedLayoutRules
    }


    enum class SvcLayoutRulesType {
        Shed,
        BigShed,
    }
}