# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Room database classes
-keep class com.publicservices.pakistan.data.local.** { *; }
-keep class com.publicservices.pakistan.data.model.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
