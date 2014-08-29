package testServices;

import dao.*;
import models.cancer.CancerType;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.institutions.Laboratory;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tonywang on 7/3/14.
 */
public class DanielImportCasesAndSamples
{
    /* DAO objects */
//    private final static CommonDao commonDao = new CommonDao();
    private final static StudyCaseDao studyCaseDao = new StudyCaseDao();
    private final static SampleDao sampleDao = new SampleDao();
    private final static CancerTypeDao cancerTypeDao = new CancerTypeDao();
    private final static LaboratoryDao labDao = new LaboratoryDao();


    /* Directory for storing CSV files.
     * CSV file format: comma-delimited three columns:
     * 1st column contains the cancer type code, 2nd column: Study case, 3rd: sample name */
    private final static String CSVDirectory = "/Users/tonywang/Desktop/DanielD/DiffMAFProcessCSVOutput";


    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Laboratory currentLab = labDao.getLaboratoryById(3L);

        File workingDir = new File(CSVDirectory);

        if (workingDir.isDirectory())
        {
            for (File csvFile : workingDir.listFiles())
            {
                if (csvFile.getName().endsWith(".csv"))
                {
                    ICsvListReader csvListReader = null;

                    try {
                        csvListReader = new CsvListReader(new FileReader(csvFile.toString()), CsvPreference.STANDARD_PREFERENCE);
                        csvListReader.getHeader(false);

                        List<String> contentList = null;

                        String cancerTypeCode = "";
                        CancerType cancerTypeObj = null;

                        while ((contentList = csvListReader.read()) != null)
                        {
                            if (!cancerTypeCode.equalsIgnoreCase(contentList.get(0)))
                            {
                                cancerTypeCode = contentList.get(0);
                                cancerTypeObj = new CancerType();
                                cancerTypeObj.setName(cancerTypeCode);
                                cancerTypeDao.saveCancerType(cancerTypeObj);
                                System.out.println("successfully saved the new cancer type: " + cancerTypeCode);
                            }

                            CancerType currentCancerType = cancerTypeDao.getCancerTypeByName(contentList.get(0));
                            if (currentCancerType != null)
                            {
                                String studyName = contentList.get(1);
                                int numSamples = 0;

                                StudyCase currentStudyCase = studyCaseDao.getStudyCaseByName(studyName);
                                if (currentStudyCase == null)
                                {
                                    currentStudyCase = new StudyCase();
                                    currentStudyCase.setDiseaseName(currentCancerType.getName());
                                    currentStudyCase.setStudyName(contentList.get(1));   // + "-" + contentList.get(2));
                                    currentStudyCase.setPublicStudy(true);

                                    currentStudyCase.setStudyDescription("TCGA data for cancer type: " + currentCancerType.getName() +
                                            ", barcode is: " + contentList.get(1) + "-" + contentList.get(2));

                                    currentStudyCase.setLaboratory(currentLab);  // set the study case to TCGA lab!
                                }

                                currentStudyCase.setNumSamples(currentStudyCase.getNumSamples()+1);

                                Set<Sample> samples = new HashSet<Sample>();
                                Sample currentSample = new Sample();
                                currentSample.setCancerType(currentCancerType);
                                currentSample.setSampleName(contentList.get(1) + "-" + contentList.get(2));
                                currentSample.setSampleSource("Cancer Patient");
                                currentSample.setGenomeMethodTypeId(1);  // exome-seq or genome seq
                                currentSample.setSequencingInstrument("Illumina HiSeq");
                                currentSample.setSequencingCenter("TCGA");
                                currentSample.setSampleDescription("barcode: " + contentList.get(1) + "-" + contentList.get(2));
                                currentSample.setStudyCase(currentStudyCase);
                                currentSample.setSampleType("Tumor Sample");

                                samples.add(currentSample);

                                currentStudyCase.setSamples(samples);
                                System.out.println("sample name is: " + currentSample.getSampleName());
                                studyCaseDao.saveStudyCase(currentStudyCase);
                            }
                            else
                                System.out.println("cannot find this cancer type!");

//                            System.out.println("Study name is: " + contentList.get(1) + ", Sample name is: " + contentList.get(1) + "-" + contentList.get(2));
                        }
                    } finally {
                        if (csvListReader != null) {
                            csvListReader.close();
                            System.out.println("all done, successful!");
                        }
                    }


                }
            }
        }
    }


}
