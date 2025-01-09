object BuildModules {
    object CORE {
        const val BASE = ":core:base"
        const val DATABASE = ":core:database"
        const val UIKIT = ":core:uikit"
        const val DATA = ":core:data"
        const val SHARED = ":core:shared"
        const val NETWORK = ":core:network"
    }

    object FEATURE {}

    /*
      Oluşturduğuuz feature modülleri burada tanımlayabiliriz.
      Bunları kullanmak için bunları eklemek istediğimiz
      gradle içine
      implementation(project(BuildModules.BASE))
      olarak ekleyebiliriz
     */
}