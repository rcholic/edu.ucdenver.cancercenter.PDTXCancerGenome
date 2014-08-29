package testServices;

import dao.CancerTypeDao;
import dao.CommonDao;
import models.cancer.CancerType;

/**
 * Created by tonywang on 6/24/14.
 */
public class testCancerType
{
//    final static CancerTypeDao cancerTypeDao = new CancerTypeDao();

    final static CommonDao commonDao = new CommonDao();

    public static void main(String[] args)
    {

        CancerType cancerType = new CancerType();
        cancerType.setName("HNSC");

        commonDao.saveObject(cancerType);
        System.out.println("saved successfully!");


        // testCancerTypeDao

        CancerTypeDao cancerTypeDao = new CancerTypeDao();

        // CancerType cancerType = null;
        cancerType = cancerTypeDao.getCancerTypeById(1);

        if (cancerType != null)
        {
            System.out.println("retrieved cancer type name: " + cancerType.getName());
        }
    }
}
