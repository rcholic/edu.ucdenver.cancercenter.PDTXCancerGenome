package testServices;

import dao.SampleDao;
import dao.StudyCaseDao;
import models.cancer.Sample;
import models.cancer.StudyCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by guoliangwang on 7/1/14.
 */
public class ImportTCGAData
{
    private final static SampleDao sampleDao = new SampleDao();
    private final static StudyCaseDao studyCaseDao = new StudyCaseDao();

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

                    //    System.out.println("studyName=" + studyName + ", sampleName: " + sampleName);

                        StudyCase studyCase = studyCaseDao.getStudyCaseByName(studyName);
                        if (studyCase == null) {
                            continue;
                        }

                        Sample curSample = sampleDao.getSampleByName(sampleName);
                        if (curSample == null) {
                            continue;
                        }

                        TCGAStudySamples.TCGAAnnovarCSVReader(file.toString(), studyCase.getStudyName(), curSample.getSampleName());

                    }
                }
            }
        }

    }
}
