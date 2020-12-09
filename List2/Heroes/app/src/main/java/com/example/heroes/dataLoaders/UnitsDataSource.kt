package com.example.heroes.dataLoaders

import com.example.heroes.dataModels.UnitData
import java.util.*

class UnitsDataSource {

    private var list = mutableListOf<UnitData>()

    fun getUnitByName(unitName: String?): UnitData {
        val unitsList = createDataSet()
        unitsList.forEach {
            if (it.name == unitName)
                return it
        }
        return list[0]
    }

    fun getImagesUrlByName(unitName: String?): ArrayList<String> {
        val imagesList = arrayListOf<String>()
        when (unitName?.toLowerCase(Locale.ROOT)) {
            "angel" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396368-fc13d680-3044-11eb-8144-f57352375d90.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396369-fcac6d00-3044-11eb-90be-2395590f00ed.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396370-fcac6d00-3044-11eb-9e76-95ee581da3aa.jpg")
                return imagesList
            }
            "archer" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396288-edc5ba80-3044-11eb-8852-a2db8898392d.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396289-edc5ba80-3044-11eb-909b-dc66d01f2a91.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396291-ee5e5100-3044-11eb-8677-0e0fff3399af.jpg")
                return imagesList
            }
            "centaur" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396298-ef8f7e00-3044-11eb-9ec5-e5be8946e962.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396299-ef8f7e00-3044-11eb-8248-c8c4a88ee851.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396301-f0281480-3044-11eb-8475-356ab6fc0f85.jpg")
                return imagesList
            }
            "green dragon" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396324-f4543200-3044-11eb-84a5-1e54700dff2e.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396326-f4543200-3044-11eb-8879-e8029cd5ec5c.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396327-f4543200-3044-11eb-89c7-b5038e143fb5.jpg")
                return imagesList
            }
            "griffin" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396330-f4ecc880-3044-11eb-89ae-8152665af34b.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396331-f5855f00-3044-11eb-9bad-e441802151af.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396332-f5855f00-3044-11eb-85f8-559cea05fbb4.jpg")
                return imagesList
            }
            "manticore" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396334-f61df580-3044-11eb-8c8b-d8a2bb0cb28d.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396335-f61df580-3044-11eb-931d-c364a0fcc2f6.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396336-f61df580-3044-11eb-93f3-cff156d67314.jpg")
                return imagesList
            }
            "minotaur" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396338-f6b68c00-3044-11eb-96fc-cb85ca1431f3.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396339-f74f2280-3044-11eb-8fb8-a37e12a32586.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396340-f74f2280-3044-11eb-9e8c-eef246a2d12c.jpg")
                return imagesList
            }
            "red dragon" -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396350-f8804f80-3044-11eb-81bf-5f209a88c700.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396353-f918e600-3044-11eb-8e31-4400c6df5a64.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396354-f918e600-3044-11eb-8493-9f4ff71daf33.jpg")
                return imagesList
            }
            else -> {
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396364-fb7b4000-3044-11eb-949b-4f6cd36a462b.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396365-fb7b4000-3044-11eb-9190-e63144c43207.jpg")
                imagesList.add("https://user-images.githubusercontent.com/49079073/100396366-fc13d680-3044-11eb-86ba-89360cd9424b.jpg")
                return imagesList
            }
        }
    }

    fun getStatsImageByName(unitName: String?): String {
        return when (unitName?.toLowerCase(Locale.ROOT)) {
            "angel" -> "https://user-images.githubusercontent.com/49079073/100396371-fcac6d00-3044-11eb-9286-716342f11686.jpg"
            "archer" -> "https://user-images.githubusercontent.com/49079073/100396292-ee5e5100-3044-11eb-9cd9-90a0801d8a60.jpg"
            "centaur" -> "https://user-images.githubusercontent.com/49079073/100396302-f0281480-3044-11eb-98cd-6a98f42a0900.jpg"
            "green dragon" -> "https://user-images.githubusercontent.com/49079073/100396328-f4ecc880-3044-11eb-8cad-00b8405ea104.jpg"
            "griffin" -> "https://user-images.githubusercontent.com/49079073/100396333-f5855f00-3044-11eb-8a18-4e1e54ec9ff0.jpg"
            "manticore" -> "https://user-images.githubusercontent.com/49079073/100396337-f6b68c00-3044-11eb-9fdc-b4580c4fc2c3.jpg"
            "minotaur" -> "https://user-images.githubusercontent.com/49079073/100396341-f74f2280-3044-11eb-81e3-aa7c45c0447f.jpg"
            "red_dragon" -> "https://user-images.githubusercontent.com/49079073/100396355-f9b17c80-3044-11eb-861e-721e7e255144.jpg"
            else -> "https://user-images.githubusercontent.com/49079073/100396367-fc13d680-3044-11eb-87af-a6790321709f.jpg"
        }
    }

    fun createDataSet(): MutableList<UnitData> {
        list.add(
            UnitData(
                "Angel",
                "Castle",
                "https://user-images.githubusercontent.com/49079073/100395483-662a7c80-3041-11eb-917b-ffa7d95cf402.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Archer",
                "Castle",
                "https://user-images.githubusercontent.com/49079073/100395313-b81ed280-3040-11eb-921b-90d89b5f7b2f.png",
                false
            )
        )
        list.add(
            UnitData(
                "Centaur",
                "Rampart",
                "https://user-images.githubusercontent.com/49079073/100396298-ef8f7e00-3044-11eb-9ec5-e5be8946e962.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Green Dragon",
                "Rampart",
                "https://user-images.githubusercontent.com/49079073/100396324-f4543200-3044-11eb-84a5-1e54700dff2e.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Griffin",
                "Castle",
                "https://user-images.githubusercontent.com/49079073/100396330-f4ecc880-3044-11eb-89ae-8152665af34b.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Manticore",
                "Dungeon",
                "https://user-images.githubusercontent.com/49079073/100396334-f61df580-3044-11eb-8c8b-d8a2bb0cb28d.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Minotaur",
                "Dungeon",
                "https://user-images.githubusercontent.com/49079073/100396338-f6b68c00-3044-11eb-96fc-cb85ca1431f3.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Red Dragon",
                "Dungeon",
                "https://user-images.githubusercontent.com/49079073/100396350-f8804f80-3044-11eb-81bf-5f209a88c700.jpg",
                false
            )
        )
        list.add(
            UnitData(
                "Wood Elf",
                "Rampart",
                "https://user-images.githubusercontent.com/49079073/100396364-fb7b4000-3044-11eb-949b-4f6cd36a462b.jpg",
                false
            )
        )

        return list
    }

}