
package com.example.annchih.classplanner;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
/* Case 1
 * Given I have selected college, major, and year,
 * When I click OK,
 * Then I can go to the next page to select CSE classes that I have taken.
 * Case 2
 * Given all the CSE classes are shown on the page
 * When I click on the class I have taken,
 * Then I the checkbox is checked.
 * Case 3
 * Given all the CSE classes are shown on the page And I have selected the checkboxes of the classes taken,
 * When I click on the Finish button,
 * Then I can go to the next page to select which view to plan my schedule.
 * Case 4
 * Given the page shows two options “List All Classes” and “Quarter Calendar”,
 * When I click “List All Classes”,
 * Then I can see all the CSE courses with class ID and class title displayed in a list view.
 * Case 5
 * Given I have clicked “List All Classes”,
 * When I scroll down and up the page,
 * Then I can see all the CSE courses displayed in the list view.
 * Case 6
 * Given I have clicked “List All Classes”,
 * When I click on a single view of a course,
 * Then I can see the description of the course.
 * Case 7
 * Given the page shows two options “List All Classes” and “Quarter Calendar”,
 * When I click “Quarter Calendar”,
 * Then I can go to the quarter view with fall, winter, spring, and summer quarters specified in the tab above the view.
 */


public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public ApplicationTest() {
        // super("com.example.annchih.classplanner", MainActivity.class);
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testActivities() throws Exception {
        solo.unlockScreen();
        //Case 1
        solo.clickOnButton("OK");
        solo.assertCurrentActivity("Check on activity", MainActivity.class);
        //Case 2
        solo.clickOnCheckBox(3);
        //Case 3
        solo.clickOnButton("Finish");
        solo.assertCurrentActivity("Check on activity", Select_classesTaken.class);
        //Case 4
        solo.clickOnText("List All Classes");
        solo.assertCurrentActivity("Check on activity", QuarterListView.class);
        //Case 5
        solo.scrollDown();
        //Case 6
        solo.clickInList(3);
        solo.clickOnButton("Add");
        //solo.goBack();
        //solo.goBack();
        //Case 7
        //solo.clickOnText("Quarter Calender");


    }

}