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
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.Configuration
import org.apache.logging.log4j.core.config.LoggerConfig
import com.arkivanov.mvikotlin.logging.logger.Logger as MviLogger


object LoggerUtils {

    private val isDebug get() = System.getProperty("debug")?.toBooleanStrictOrNull() ?: true

    val logger = KotlinLogging.logger {}.apply {
        if (!isDebug) {
            val ctx = LogManager.getContext(false) as LoggerContext
            val config: Configuration = ctx.configuration
            val loggerConfig: LoggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)
            loggerConfig.level = Level.WARN
            ctx.updateLoggers()
        }
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
