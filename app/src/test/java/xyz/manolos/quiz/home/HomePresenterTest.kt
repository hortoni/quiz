package xyz.manolos.quiz.home

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    @Mock
    private lateinit var view: HomeView

    @InjectMocks
    private lateinit var presenter: HomePresenter

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `should show error when textview is blank`() {
        presenter.goToQuiz(true, "error")
        verify(view).showError("error")
    }

    @Test
    fun `should go to question activity when textview is not blank`() {
        presenter.goToQuiz(false, "error")
        verify(view).goQuestionActivity()
    }
}