package xyz.manolos.quiz.result

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import xyz.manolos.quiz.R

@RunWith(MockitoJUnitRunner::class)
class ResultPresenterTest {

    @Mock
    private lateinit var view: ResultView

    @InjectMocks
    private lateinit var presenter: ResultPresenter

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `should show result text`() {
        presenter.getResultText("text", "user", 5)
        verify(view).showResultText("text")
    }

    @Test
    fun `should show result text zero `() {
        presenter.getResultText("text.", "user", 0)
        verify(view).showResultZeroText("user")
    }

    @Test
    fun `should show result bad image`() {
        presenter.getImageResourceResult( 3)
        verify(view).showResultImage(R.drawable.ic_bad)
    }

    @Test
    fun `should show result good image`() {
        presenter.getImageResourceResult( 7)
        verify(view).showResultImage(R.drawable.ic_good)
    }

    @Test
    fun `should show result excellent image`() {
        presenter.getImageResourceResult( 10)
        verify(view).showResultImage(R.drawable.ic_excellent)
    }

}