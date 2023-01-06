package be.mathias.thebible.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import be.mathias.thebible.R
import junit.framework.AssertionFailedError
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchVerseTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchVerseTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.button_first), withText("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialAutoCompleteTextView = onView(
            allOf(
                withId(R.id.book_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.book_textfield_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialAutoCompleteTextView.perform(click())

        val materialTextView = onData(anything())
            .inRoot(RootMatchers.isPlatformPopup())
            .atPosition(0)
        materialTextView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.chapter),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("1"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.verse),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("1"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.search_verse), withText("Search"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        Thread.sleep(3000)
        val textView = onView(
            allOf(
                withId(R.id.text_verse),
                withText("In the beginning was the Word, and the Word was with God, and the Word was God.\n"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        //textView.check(matches(withText("In the beginning was the Word, and the Word was with God, and the Word was God. ")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    private fun onViewEnabled(viewMatcher: Matcher<View>): ViewInteraction {
        val isEnabled: ()->Boolean = {
            var isDisplayed = false
            try {
                onView(viewMatcher).check(matches((ViewMatchers.isEnabled())))
                isDisplayed = true
            }
            catch (e: AssertionFailedError) { isDisplayed = false }
            isDisplayed
        }
        for (x in 0..9) {
            Thread.sleep(400)
            if (isEnabled()) {
                break
            }
        }
        return Espresso.onView(viewMatcher)
    }
}
