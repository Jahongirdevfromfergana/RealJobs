package uz.fergana.developer

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.model.WorkerModel
import uz.fergana.developer.utils.Prefs


class MyApp : MultiDexApplication() {
    companion object {
        lateinit var app: MyApp
        var categories = listOf(
            CategoryModel(0, "Hammasi", true),
            CategoryModel(1, "Haydovchi", false),
            CategoryModel(2, "Operator", false),
            CategoryModel(3, "Menedjer", false),
            CategoryModel(4, "Kassir", false),
            CategoryModel(5, "Dasturchi", false),
            CategoryModel(6, "Adminstrator", false)
        )

        var employees = listOf(
            WorkerModel(
                1,
                "worker",
                "Soliyev Shoxrux",
                "998",
                40.3714,
                71.787028,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=56",
                "Haydovchi",

                1,
                "B va C toifa haydovchi"
            ),
            WorkerModel(
                2,
                "worker",
                "Nazirqulov Jamoliddin",
                "998",
                40.39117,
                71.766085,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=68",
                "Operator",
                2,
                "Ingliz rus tillarini biluvchi Operator"
            ),
            WorkerModel(
                3,
                "worker",
                "Husanov Jasur",
                "998",
                40.392139,
                71.799129,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=67",
                "Menedjer",
                3,
                "Boshqaruvning haqiqiy ustasi"
            ),
            WorkerModel(
                4,
                "worker",
                "Ergashev Ravshanbek",
                "998",
                40.376675,
                71.780833,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=65",
                "Kassir",
                4,
                "Omonatdor pulga ishonchli"
            ),
            WorkerModel(
                5,
                "worker",
                "Mirzarahoimov Mirzarahim",
                "998",
                40.382877,
                71.753733,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=64",
                "Dasturchi",
                5,
                "Fullstack PHP, JavaScript"
            ),
            WorkerModel(
                6,
                "worker",
                "Abdurahmonov Muhammadjon",
                "998",
                40.376106,
                71.794731,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=60",
                "Haydovchi",
                1,
                ""
            ),
            WorkerModel(
                7,
                "worker",
                "Mamataliyev Jumanazarhoji",
                "998",
                40.371406,
                71.78702891,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=61",
                "Haydovchi",
                1,
                ""
            ),
            WorkerModel(
                8,
                "worker",
                "Sirojiddinov Azamatjon",
                "998",
                40.371407,
                71.787028331,
                1,
                "Farg'ona",
                3,
                "https://i.pravatar.cc/300?img=59",
                "Haydovchi",
                1,
                ""
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        MultiDex.install(this)
        Prefs.init(this)
    }
}