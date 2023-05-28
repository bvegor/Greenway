package fr.Egor.greenwaywork

//contenu dynamique (images)
class VeloModel(
    val id: String = "velo0",
    val name: String = "Vélo 000",
    val latitude: String = "latitude",
    val longitude: String ="longitude",
    val niveauBat: String = "niveauBatterie",
    val idBat: String = "idBatterie",
    val imageUrl: String = "https://www.linkpicture.com/q/001_3.png",
    var liked: Boolean = true
)