-ignorewarnings

-keepattributes Signature

-keep class kotlin.Metadata { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


# Logger

-dontwarn org.apache.logging.log4j.**
-dontwarn org.apache.logging.slf4j.**
-keep class org.apache.logging.log4j.** { *; }
-keep class org.apache.logging.slf4j.** { *; }

# -keepnames class org.slf4j.** { *; }

-assumenosideeffects class * implements org.slf4j.Logger {
    public *** trace(...);
    public *** debug(...);
    # public *** info(...);
    # public *** warn(...);
    # public *** error(...);
}
