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

package io.stardewvalleydesigner

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.logger.DefaultLogFormatter
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import com.arkivanov.mvikotlin.logging.logger.Logger as MviLogger


object LoggerUtils {

    private val _isDebug: Boolean by lazy(::isDebug)

    val logger: KLogger = KotlinLogging.logger {}.apply {
        configureLoggerLevel(_isDebug)
    }

    fun createLoggerAwareStoreFactory(): StoreFactory = if (_isDebug) {
        LoggingStoreFactory(
            delegate = DefaultStoreFactory(),
            logger = object : MviLogger {
                override fun log(text: String) {
                    logger.debug { "[MVI] - $text" }
                }
            },
            logFormatter = DefaultLogFormatter(valueLengthLimit = Int.MAX_VALUE)
        )
    } else {
        DefaultStoreFactory()
    }
}

internal expect fun configureLoggerLevel(isDebug: Boolean)

internal expect fun isDebug(): Boolean
