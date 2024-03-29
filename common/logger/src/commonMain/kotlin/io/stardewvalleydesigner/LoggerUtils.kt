/*
 * Copyright 2021-2024 Azim Muradov
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

// TODO : Move to `io.stardewvalleydesigner.logger` (?)
package io.stardewvalleydesigner

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.logger.DefaultLogFormatter
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.stardewvalleydesigner.kmplib.env.Environment
import com.arkivanov.mvikotlin.logging.logger.Logger as MviLogger


object LoggerUtils {

    private val isDebug: Boolean by lazy {
        Environment.getVar(name = "debug")?.toBooleanStrictOrNull() ?: true
    }

    val logger: KLogger = KotlinLogging.logger {}.apply {
        configureLoggerLevel(isDebug)
    }

    fun createLoggerAwareStoreFactory(): StoreFactory = if (isDebug) {
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
