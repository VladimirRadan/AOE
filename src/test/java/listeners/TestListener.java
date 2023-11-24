package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("STARTING TEST METHOD: " + "[ " + result.getMethod().getMethodName() + " ]");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("[ TEST METHOD---------------: " + result.getMethod().getMethodName() + " ---------> PASSED]");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("[ TEST METHOD--------------: "  + result.getMethod().getMethodName() + " ---------> FAILED]" +  "\n" + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("[SKIPPED METHOD---->]: "+ "[ " + result.getMethod().getMethodName() + " ]");
    }


}//end class
