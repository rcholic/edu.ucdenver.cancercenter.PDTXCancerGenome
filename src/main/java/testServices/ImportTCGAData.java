package testServices;

import dao.SampleDao;
import dao.StudyCaseDao;
import models.cancer.Sample;
import models.cancer.StudyCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoliangwang on 7/1/14.
 */
public class ImportTCGAData
{
    private final static SampleDao sampleDao = new SampleDao();
    private final static StudyCaseDao studyCaseDao = new StudyCaseDao();

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        List<String> caseLists = new ArrayList<String>();
        List<String> sampleLists = new ArrayList<>();

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
                            System.out.println("could not find the study case: " + studyName);
                            caseLists.add(studyName);
                            continue;
                        }

                        Sample curSample = sampleDao.getSampleByName(sampleName);
                        if (curSample == null) {
                            System.out.println("did not find the sample: " + sampleName);
                            sampleLists.add(sampleName);
                            continue;
                        }

                    //    TCGAStudySamples.TCGAAnnovarCSVReader(file.toString(), studyCase.getStudyName(), curSample.getSampleName());

                    }
                }
            }
        }

        if (caseLists.size() != 0) {
            for (String caseName : caseLists) {
                System.out.println("case name is: " + caseName);
            }
        }

        if (sampleLists.size() != 0) {
            for (String sampleName : sampleLists) {
                System.out.println("sample name is: " + sampleName);
            }
        }

    }
}
