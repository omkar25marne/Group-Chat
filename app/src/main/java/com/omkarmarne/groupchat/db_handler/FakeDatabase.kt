package com.omkarmarne.groupchat.db_handler

import com.omkarmarne.groupchat.models.GroupUsers
import com.omkarmarne.groupchat.models.Groups
import com.omkarmarne.groupchat.models.Message
import com.omkarmarne.groupchat.models.User

/**
 * Temporary class to add fake/sample records in the database
 */
class FakeDatabase {

    fun generateUsers(chatDao: ChatDao) {
        val userFury = User(
            "user1000",
            "Nick Fury",
            "9900000000",
            "https://static.wikia.nocookie.net/disney/images/9/9c/Profile_-_Nick_Fury.png"
        )
        val userStark = User(
            "user0001",
            "Tony Stark",
            "9753108642",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/002irm_ons_inl_15.jpg"
        )
        val userCap = User(
            "user0002",
            "Steve Rogers",
            "8976123046",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/003cap_ons_inl_02_0.jpg"
        )
        val userWidow = User(
            "user0003",
            "Natasha Romanoff",
            "9087126354",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/011blw_ons_inl_01.jpg"
        )
        val userThor = User(
            "user0004",
            "Thor Odinson",
            "9753108643",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/004tho_ons_inl_06.jpg"
        )
        val userHawkeye = User(
            "user0005",
            "Clint Barton",
            "8976123045",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/018hcb_ons_inl_03.jpg"
        )
        val userHulk = User(
            "user0006",
            "Bruce Banner",
            "9087126355",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/006hbb_ons_inl_01.jpg"
        )
        // region - GOTG
        val userStarLord = User(
            "user0007",
            "Peter Quill",
            "9876543120",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/021slq_ons_inl_04_0.jpg"
        )
        val userGamora = User(
            "user0008",
            "Gamora",
            "8797897890",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/022gam_ons_inl_03.jpg"
        )
        val userDrax = User(
            "user0009",
            "Drax",
            "86757698870",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/025drx_ons_inl_01_3.jpg"
        )
        val userRocket = User(
            "user0010",
            "Rocket Raccoon",
            "8778879609",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/023rra_ons_inl_03.jpg"
        )
        val userGroot = User(
            "user0011",
            "Groot",
            "9786787876",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/024grt_ons_inl_03.jpg"
        )
        // endregion
        // region - Fantastic Four
        val userReedRichards = User(
            "user0012",
            "Reed Richards",
            "978123123",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/156ffo_com_inl_02.jpg"
        )
        val userSusan = User(
            "user0013",
            "Susan Storm-Richards",
            "9678124242",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/156ffo_com_inl_02.jpg"
        )
        val userJohnnyStorm = User(
            "user0014",
            "Johnny Storm",
            "9124354879",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/156ffo_com_inl_02.jpg"
        )
        val userThing = User(
            "user0015",
            "Benn Grim",
            "9923657283",
            "https://url.should.fail/156ffo_com_inl_02.jpg"
        )
        // endregion

        chatDao.insert(userFury)
        chatDao.insert(userStark)
        chatDao.insert(userCap)
        chatDao.insert(userWidow)
        chatDao.insert(userThor)
        chatDao.insert(userHawkeye)
        chatDao.insert(userHulk)
        chatDao.insert(userStarLord)
        chatDao.insert(userGamora)
        chatDao.insert(userDrax)
        chatDao.insert(userRocket)
        chatDao.insert(userGroot)
        chatDao.insert(userReedRichards)
        chatDao.insert(userSusan)
        chatDao.insert(userJohnnyStorm)
        chatDao.insert(userThing)
    }

    fun generateGroups(chatDao: ChatDao) {
        val groupAvengers = Groups(
            "group001",
            "Avengers",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/001avn_ons_inl_02_0.jpg"
        )
        val groupGOTG = Groups(
            "group002",
            "G.O.T.G",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/020gga_ons_inl_05.jpg"
        )
        val groupF4 = Groups(
            "group003",
            "Fantastic Four",
            "https://terrigen-cdn-dev.marvel.com/content/prod/1x/156ffo_com_inl_01.jpg"
        )
        chatDao.insert(groupAvengers)
        chatDao.insert(groupGOTG)
        chatDao.insert(groupF4)
    }

    fun addGroupMembers(chatDao: ChatDao) {
        val groupMemberFury1 = GroupUsers(
            "user1000",
            "group001",
            0
        )
        val groupMemberFury2 = GroupUsers(
            "user1000",
            "group002",
            0
        )
        val groupMemberFury3 = GroupUsers(
            "user1000",
            "group003",
            0
        )
        val groupMember1 = GroupUsers(
            "user0001",
            "group001",
            1
        )
        val groupMember2 = GroupUsers(
            "user0002",
            "group001",
            0
        )
        val groupMember3 = GroupUsers(
            "user0003",
            "group001",
            0
        )
        val groupMember4 = GroupUsers(
            "user0004",
            "group001",
            0
        )
        val groupMember5 = GroupUsers(
            "user0005",
            "group001",
            0
        )
        val groupMember6 = GroupUsers(
            "user0006",
            "group001",
            0
        )
        val groupMember7 = GroupUsers(
            "user0007",
            "group002",
            1
        )
        val groupMember8 = GroupUsers(
            "user0008",
            "group002",
            0
        )
        val groupMember9 = GroupUsers(
            "user0009",
            "group002",
            0
        )
        val groupMember10 = GroupUsers(
            "user0010",
            "group002",
            0
        )
        val groupMember11 = GroupUsers(
            "user0011",
            "group002",
            0
        )
        val groupMember12 = GroupUsers(
            "user0012",
            "group003",
            1
        )
        val groupMember13 = GroupUsers(
            "user0013",
            "group003",
            0
        )
        val groupMember14 = GroupUsers(
            "user0014",
            "group003",
            0
        )
        val groupMember15 = GroupUsers(
            "user0015",
            "group003",
            0
        )

        chatDao.insert(groupMemberFury1)
        chatDao.insert(groupMemberFury2)
        chatDao.insert(groupMemberFury3)
        chatDao.insert(groupMember1)
        chatDao.insert(groupMember2)
        chatDao.insert(groupMember3)
        chatDao.insert(groupMember4)
        chatDao.insert(groupMember5)
        chatDao.insert(groupMember6)
        chatDao.insert(groupMember7)
        chatDao.insert(groupMember8)
        chatDao.insert(groupMember9)
        chatDao.insert(groupMember10)
        chatDao.insert(groupMember11)
        chatDao.insert(groupMember12)
        chatDao.insert(groupMember13)
        chatDao.insert(groupMember14)
        chatDao.insert(groupMember15)
    }

    fun generateMessages(chatDao: ChatDao) {
        val message1 = Message(
            "group001",
            "user1000",
            "9900000000",
            "There was an idea.\nThe idea was to bring together a group of remarkable people to see if they could become something more...",
            1669362851000,
            6,
            arrayListOf("user0001", "user0002", "user0003", "user0004", "user0005", "user0006")
        )
        val message2 = Message(
            "group001",
            "user0002",
            "8976123046",
            "Before we get started, does anyone want to get out?",
            1669362856000,
            0,
            arrayListOf()
        )
        val message3 = Message(
            "group001",
            "user0004",
            "9753108643",
            "I like it. Another!",
            1669362861000,
            0,
            arrayListOf()
        )
        val message4 = Message(
            "group001",
            "user0005",
            "8976123045",
            "If we’re going to win this fight, some of us might have to lose it.",
            1669362866000,
            0,
            arrayListOf()
        )
        val message5 = Message(
            "group001",
            "user0002",
            "8976123046",
            "You get hurt, hurt ’em back.\nYou get killed… walk it off.",
            1669362872000,
            2,
            arrayListOf("user1000", "user0003")
        )
        val message6 = Message(
            "group001",
            "user0002",
            "8976123046",
            "I can do this all day",
            1669362877000,
            0,
            arrayListOf()
        )
        val message7 = Message(
            "group001",
            "user0001",
            "9753108642",
            "Anybody on our side hiding any shocking and fantastic abilities they'd like to disclose? I'm open to suggestions.",
            1669362883000,
            2,
            arrayListOf("user0004", "user0005")
        )
        val message8 = Message(
            "group001",
            "user0006",
            "9087126355",
            "That’s my secret, Captain. I’m always angry.",
            1669362889000,
            2,
            arrayListOf("user0002")
        )
        val message9 = Message(
            "group001",
            "user0003",
            "9087126354",
            "I get emails from a raccoon, so nothing sounds crazy anymore.",
            1669362897000,
            0,
            arrayListOf()
        )
        val message10 = Message(
            "group001",
            "user0002",
            "8976123046",
            "Avengers, assemble!",
            1669362902000,
            5,
            arrayListOf("user0001", "user0003", "user0004", "user0005", "user006")
        )

        chatDao.insert(message1)
        chatDao.insert(message2)
        chatDao.insert(message3)
        chatDao.insert(message4)
        chatDao.insert(message5)
        chatDao.insert(message6)
        chatDao.insert(message7)
        chatDao.insert(message8)
        chatDao.insert(message9)
        chatDao.insert(message10)
    }
}