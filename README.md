# try-paging-3
Membahas tentang penggunaan Paging 3 untuk dapat menampilkan data secara berkala dari local database atau dari Restful API

# Paging 3

Membantu anda menampilkan data secara berkala dari local database atau Restful API.

### Setup

```sh
dependencies {
    def paging_version = "3.0.0-alpha09"
    implementation "androidx.paging:paging-runtime:$paging_version"
}
```

Pada project ini menggunakan Restful API dari The Movie DB yang dapat anda akses di laman berikut:

https://developers.themoviedb.org/3/getting-started/introduction

untuk dapat menggunakan API dari The Movie DB yang perlu anda lakukan adalah 
Pada /gradle.properties tambahkan API Key 
```sh
APIkey = "API key kalian"
```
Pada app:build.gradle tambahkan buildConfigField pada buildTypes
```sh
buildTypes {
    debug {
        buildConfigField "String", "APIkey", APIkey
        // etc.
    }
    release {
        buildConfigField "String", "APIkey", APIkey
        // etc.
    }
}
```

Pada codelab terdapat beberapa branch
  - Master (intial projek)
  - source_data (Cara menampilkan load more data secara manual)
  - loading_state (Cara menampilkan loading saat load more data)
  - database_network (Cara menampilkan data dari local database dan load more data dari Restful API)

Referensi projek adalah documentasi dari developer android :
https://developer.android.com/topic/libraries/architecture/paging/v3-overview

### Social Media
| [LinkedIn](https://www.linkedin.com/in/renalsa18/) | [Youtube](https://www.youtube.com/channel/UCU9kq_235U9rEYT6v6DE_tQ?view_as=subscriber) | [Medium](https://renaldysabdo.medium.com)
