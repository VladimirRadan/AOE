package tests;

import core.Constants;
import org.testng.ITestListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class TestNGRunner {
    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        ITestListener iTestListener = new TestListenerAdapter();
        //List<String> xmlFiles= new ArrayList<>();
        //xmlFiles.add("user.dir\\test.xml");
//        String fileName = "test.xml";
//        xmlFiles.add(Paths.get(fileName).toAbsolutePath().toString());
        //testNG.setTestSuites(xmlFiles);
        testNG.addListener(iTestListener);
        testNG.setTestClasses(new Class[]{VacanciesTest.class});
        testNG.run();
    }


}
