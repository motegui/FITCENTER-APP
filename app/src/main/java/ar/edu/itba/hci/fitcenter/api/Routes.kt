package ar.edu.itba.hci.fitcenter.api

import ar.edu.itba.hci.fitcenter.api.Client.client
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

const val BASE_URL = "http://localhost:8080"

object Routes {
//    suspend fun getAllPosts(): List<Post> = client.get("$BASE_URL/posts").body()
//
//    suspend fun createNewPost(newPost: Post): Post = client.post("$BASE_URL/posts"){
//        setBody(newPost)
//    }.body()
//
//    suspend fun updatePost(id: Int, post: Post): Post = client.put("$BASE_URL/posts/$id"){
//        setBody(post)
//    }.body()
//
//    suspend fun deletePost(id: Int) = client.delete("$BASE_URL/posts/$id")

    suspend fun login(credentials: Models.Credentials): Models.AuthenticationToken {
        return client.post("$BASE_URL/users/login") {
            setBody(credentials)
        }.body()
    }
}
