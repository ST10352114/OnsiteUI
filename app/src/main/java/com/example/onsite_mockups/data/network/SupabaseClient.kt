package com.example.onsite_mockups.data.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

object SupabaseClient {
    private const val SUPABASE_URL = "https://eagxobztulgodbsimfcx.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVhZ3hvYnp0dWxnb2Ric2ltZmN4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODc4MTYzNzUsImV4cCI6MjEwMzM5MjM3NX0.cOqcFO6sQMkzDsX6yBiO6E87oKM-dIi0cjYrZXfiUpo"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
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
