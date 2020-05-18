package com.edifica.models

import android.net.Uri
import java.net.URI

object Dataholder {
    val FILENAME = "auth"

    val spinnerSettlement = arrayOf(
        "Trabajo a realizar",
        "Vivienda",
        "Restaurante",
        "Local",
        "Hotel"
    )

    val spinnerProvince = arrayOf(
        "Provincia",
        "Málaga",
        "Sevilla",
        "Cádiz",
        "Huelva",
        "Granada",
        "Córdoba",
        "Almería",
        "Jaén"
    )

    // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
    val business: ArrayList<Business> = arrayListOf(
        (Business(
            "Ferrovial S.L",
            "685485685",
            "ferrovial@gmail.com",
            "https://www.ptcarretera.es/wp-content/uploads/2015/07/Ferrovial.jpg",
            arrayListOf(5.0F, 5.0F, 4.0F, 3.5F, 1F, 2F),
            "https://www.ferrovial.com/es-es/"
        )),
        (Business(
            "Rivervial S.L",
            "75236542",
            "pepe@gmail.com",
            "https://rivervial.es/wp-content/uploads/2016/08/riverlogoligh.jpg",
            arrayListOf(4.0F, 5.0F, 4.0F, 3.5F, 1F, 4F),
            "http://rivervial.es/"
        )),
        (Business(
            "Sandro Rosell S.L",
            "2452132185",
            "rossel@gmail.com",
            "https://www.fcbarcelona.com/fcbarcelona/photo/2018/03/16/3b613a25-37c6-4cda-b331-4c89602b6b4f/2722815.jpg",
            arrayListOf(1.0F, 5.0F, 4.0F, 3.5F, 1F, 5F),
            "https://es.wikipedia.org/wiki/Sandro_Rosell"
        )),
        (Business(
            "Ferrovial S.L",
            "685485685",
            "ferrovial@gmail.com",
            "https://www.ptcarretera.es/wp-content/uploads/2015/07/Ferrovial.jpg",
            arrayListOf(5.0F, 5.0F, 4.0F, 3.5F, 1F, 2F),
            "https://www.ferrovial.com/es-es/"
        ))
    )

    // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
    lateinit var currentBusinessProfile: Business
    lateinit var inputRatingBusiness: ArrayList<Float> // [general, communication, cleaning, deadlines, quality, professionalism]

    // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
    var name = ""
    var phone = ""
    var email = ""
    var photo: Uri? = null

    /* // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
     val ads: ArrayList<Ads> = arrayListOf(
         Ads(User(),"Hotel","Málaga",arrayListOf("https://www.roca.es/rocalife/wp-content/uploads/2016/12/bano-pequeno_936x462_acf_cropped@2x.jpg","https://www.hola.com/imagenes/decoracion/20180411122699/decorar-banos-mini/0-557-216/banos-mini-1a-a.jpg"),"También es una composición de caracteres imprimibles (con grafema) generados por un algoritmo de cifrado que, aunque no tienen sentido para cualquier persona, sí puede ser descifrado por su destinatario original. En otras palabras, un texto es un entramado de signos con una intención comunicativa que adquiere sentido en determinado contexto.\n" +
                 "\n" +
                 "Las ideas que comunica un texto están contenidas en lo que se suele denominar «macroproposiciones», unidades estructurales de nivel superior o global, que otorgan coherencia al texto constituyendo su hilo central, el esqueleto estructural que cohesiona elementos lingüísticos formales de alto nivel, como los títulos y subtítulos, la secuencia de párrafos, etc. En contraste, las «microproposiciones» son los elementos coadyuvantes de la cohesión de un texto, pero a nivel más particular o local. Esta distinción fue realizada por Teun van Dijk en 1980.1\u200B"),
         Ads(User(),"Reforma","Málaga",arrayListOf("https://www.hola.com/imagenes/decoracion/20180411122699/decorar-banos-mini/0-557-216/banos-mini-1a-a.jpg","https://www.hola.com/imagenes/decoracion/20180411122699/decorar-banos-mini/0-557-216/banos-mini-1a-a.jpg"),"Esta en la cocina y necesita todo el alicatado"),
         Ads(User(),"Casa","Málaga",arrayListOf("https://www.roca.es/rocalife/wp-content/uploads/2016/12/bano-pequeno_936x462_acf_cropped@2x.jpg","https://www.hola.com/imagenes/decoracion/20180411122699/decorar-banos-mini/0-557-216/banos-mini-1a-a.jpg"),"Esta en la cocina y necesita todo el alicatado"),
         Ads(User(),"Baño","Málaga",arrayListOf("https://www.hola.com/imagenes/decoracion/20180411122699/decorar-banos-mini/0-557-216/banos-mini-1a-a.jpg","https://www.hola.com/imagenes/decoracion/20180411122699/decorar-banos-mini/0-557-216/banos-mini-1a-a.jpg"),"Esta en la cocina y necesita todo el alicatado")
     )*/

    // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
    lateinit var currentAds: Ads
    lateinit var offersentbusiness: String
}