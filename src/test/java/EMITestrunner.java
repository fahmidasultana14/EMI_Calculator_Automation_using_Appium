import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EMITestrunner extends Setup{

    @BeforeTest
    public void startEMICalc(){
        EMIScreen emiScreen=new EMIScreen(driver);
        emiScreen.btnStart.click();
    }

    @Test(priority = 1,dataProviderClass = Dataset.class,dataProvider = "data-provider")
    public void calculateEMI(int amount,double interest,int year,double processingFee,double mEMI,double tInterest,double pFee,double tPayment)
    {
        EMIScreen emiScreen=new EMIScreen(driver);
        emiScreen.calculateEMI(amount,interest,year,processingFee);

        double actualmEMI= Math.round(Double.parseDouble(emiScreen.lblMonthlyEMI.getText().replace(",","")));
        double actualtInterest= Math.round(Double.parseDouble(emiScreen.lblTotalInterest.getText().replace(",","")));
        double actualpFee= Math.round(Double.parseDouble(emiScreen.lblProcessingFee.getText().replace(",","")));
        double actualtPayment= Math.round(Double.parseDouble(emiScreen.lblTotalPayment.getText().replace(",","")));

        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(actualmEMI,mEMI);
        softAssert.assertEquals(actualtInterest,tInterest);
        softAssert.assertEquals(actualpFee,pFee);
        softAssert.assertEquals(actualtPayment,tPayment);
        softAssert.assertAll();



        emiScreen.btnReset.click();
    }
}
