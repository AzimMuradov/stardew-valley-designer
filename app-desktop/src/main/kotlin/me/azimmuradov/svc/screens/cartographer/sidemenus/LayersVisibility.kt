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

package me.azimmuradov.svc.screens.cartographer.sidemenus


// TODO

// @Composable
// fun LayersVisibility(
//     // layersVisibility: MutableLayersVisibility,
//     lang: Lang,
//     modifier: Modifier = Modifier,
// ) {
//     val visible = layersVisibility.visible
//
//     Column(modifier = modifier.padding(vertical = 8.dp)) {
//         for (lType in LayerType.all) {
//             LayerVisibility(
//                 layerType = lType,
//                 visible = lType in visible,
//                 onVisibleChange = { vis -> layersVisibility.changeVisibility(lType, vis) },
//                 lang = lang,
//             )
//         }
//     }
// }


// @Composable
// private fun LayerVisibility(
//     layerType: LayerType<*>,
//     visible: Boolean,
//     onVisibleChange: (Boolean) -> Unit,
//     lang: Lang,
// ) {
//     val wordList = Settings.wordList(lang)
//
//     Row(
//         modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 24.dp),
//         verticalAlignment = Alignment.CenterVertically,
//     ) {
//         Checkbox(
//             checked = visible,
//             onCheckedChange = onVisibleChange,
//             modifier = Modifier.size(24.dp),
//         )
//         Spacer(modifier = Modifier.width(20.dp))
//         Text(text = wordList.layer(layerType), fontSize = 14.sp)
//     }
// }