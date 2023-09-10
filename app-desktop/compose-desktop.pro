 -optimizationpasses 5
# -verbose
-ignorewarnings

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable,Signature

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


# Java & LWJGL & Native

-dontwarn java.awt.**
-dontwarn org.lwjgl.**

-dontnote java.awt.**
-dontnote org.lwjgl.**

-keep class org.lwjgl.** { *; }

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclasseswithmembernames class * {
    native <methods>;
}
