package com.example.onsite_mockups.data.network

import com.example.onsite_mockups.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

/**
 * Singleton object providing the Supabase client for authentication and direct database access via Postgrest.
 */
object SupabaseClient {

    /**
     * The Supabase client, initialized lazily to avoid issues in unit tests.
     */
    val client by lazy {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY
        ) {
            install(Auth) {
                host = "login-callback"
                scheme = "onsite"
            }
            install(Postgrest)

            defaultSerializer = KotlinXSerializer(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                encodeDefaults = true
            })
        }
    }
}
