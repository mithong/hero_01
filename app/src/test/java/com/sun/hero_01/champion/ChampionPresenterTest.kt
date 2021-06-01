package com.sun.hero_01.champion

import com.sun.hero_01.FakeData
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener
import com.sun.hero_01.ui.champion.ChampionContract
import com.sun.hero_01.ui.champion.ChampionPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@Suppress("UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class ChampionPresenterTest {

    @Mock
    private lateinit var view: ChampionContract.View

    @Mock
    private lateinit var repository: HeroRepository

    @Mock
    lateinit var exception: Exception

    private lateinit var presenter: ChampionPresenter
    private lateinit var listHero: MutableList<Hero>

    @Before
    fun setUP() {
        presenter = ChampionPresenter(repository).apply { setView(view) }
        listHero = FakeData.HEROES
    }

    @Test
    fun get_heroes_success() {
        `when`(
            repository.getListHero(any())
        ).thenAnswer {
            (it.arguments[0] as OnFetchDataJsonListener<MutableList<Hero>>).onSuccess(
                listHero
            )
        }

        presenter.getListHero()
        verify(view).loadListHeroOnSuccess(listHero)
    }

    @Test
    fun get_heroes_error() {
        `when`(
            repository.getListHero(any())
        ).thenAnswer {
            (it.arguments[0] as OnFetchDataJsonListener<MutableList<Hero>>).onError(exception)
        }

        presenter.getListHero()
        verify(view).onError(exception)
    }
}
