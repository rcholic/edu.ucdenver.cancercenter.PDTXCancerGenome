package testServices;

import dao.SampleDao;
import models.cancer.Sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by guoliangwang on 7/1/14.
 */
public class ImportTCGAData
{
    private final static SampleDao sampleDao = new SampleDao();

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        String TGCADirectory = "/Users/tonywang/Desktop/DanielD/annotatedVCFsFromMAFs/annovar";
        File workingDir = new File(TGCADirectory);
        for (File f : workingDir.listFiles())
        {
          //  System.out.println(f.toPath());
            if (f.isDirectory())
            {
                for (File file : f.listFiles())
                {
                    if (file.getName().contains("exome_summary.csv"))
                    {
                        System.out.println(file.toPath());
                        String[] fileNames = f.getName().split("-");
                        //System.out.println("fileName is: " + file.getName());

                        String studyName = fileNames[1] + "-" + fileNames[2];
                        String sampleName = studyName + "-" + fileNames[3];

                        System.out.println("studyName=" + studyName + ", sampleName: " + sampleName);
//                        System.out.println("file path: " + file.toString());

 //                       Sample curSample = sampleDao.getSampleByName(sampleName);
 //                       System.out.println("curSample is: " + curSample.getSampleId());

                //        TCGAStudySamples.TCGAAnnovarCSVReader(file.toString(), studyName, sampleName);

                    }
                }
            }
        }

    }
}
