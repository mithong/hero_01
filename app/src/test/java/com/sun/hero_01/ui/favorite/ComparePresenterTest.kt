package com.sun.hero_01.ui.favorite

import com.sun.hero_01.FakeData
import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener
import com.sun.hero_01.ui.compare.CompareContract
import com.sun.hero_01.ui.compare.ComparePresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.kotlin.any
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class ComparePresenterTest {

    @Mock
    private lateinit var view: CompareContract.View

    @Mock
    private lateinit var repository: HeroRepository

    @Mock
    private lateinit var exception: Exception

    private lateinit var presenter: ComparePresenter
    private val compare = FakeData.HERO_DETAIL

    @Before
    fun setUp(){
        presenter = ComparePresenter(repository).apply { setView(view) }
    }

    @Test
    fun get_compare_hero_success() {
        `when`(
            repository.getHeroDetails(
                ArgumentMatchers.anyString(),
                any()
            )
        ).thenAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<HeroDetail>).onSuccess(
                compare
            )
        }
        presenter.getCompareHeroDetail(FakeData.ID_HERO,FakeData.ID_HERO)
        verify(view).loadFirstHeroDetailOnSuccess(compare)
        verify(view).loadSecondHeroDetailOnSuccess(compare)
    }

    @Test
    fun get_compare_hero_error() {
        `when`(
            repository.getHeroDetails(
                ArgumentMatchers.anyString(),
                any()
            )
        ).thenAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<HeroDetail>).onError(
                exception
            )
        }
        presenter.getCompareHeroDetail(FakeData.ID_HERO,FakeData.ID_HERO)
        verify(view, times(2)).onError(exception)
    }

    @Test
    fun get_compare_secondary_hero_error() {
        `when`(
            repository.getHeroDetails(
                ArgumentMatchers.anyString(),
                any()
            )
        ).thenAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<HeroDetail>).onSuccess(
                compare
            )
        }.thenAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<HeroDetail>).onError(
                exception
            )
        }
        presenter.getCompareHeroDetail(FakeData.ID_HERO,FakeData.ID_HERO)
        verify(view).loadFirstHeroDetailOnSuccess(compare)
        verify(view).onError(exception)
    }
}
