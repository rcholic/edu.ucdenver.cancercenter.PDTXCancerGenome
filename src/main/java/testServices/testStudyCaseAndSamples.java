package testServices;

import dao.CancerTypeDao;
import dao.CommonDao;
import dao.LaboratoryDao;
import dao.StudyCaseDao;
import models.cancer.CancerType;
import models.cancer.Sample;
import models.cancer.StudyCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tonywang on 6/25/14.
 */
public class testStudyCaseAndSamples
{
    private final static StudyCaseDao studyCaseDao = new StudyCaseDao();
    private final static CancerTypeDao cancerTypeDao = new CancerTypeDao();
    private final static LaboratoryDao laboratoryDao = new LaboratoryDao();

    private final static String SEQUENCINGCENTER = "UCDenver Sequencing Core";
    private final static String SEQUENCINGINSTRUMENT = "Illumina HiSeq2000";
    private final static CancerType BATCHCANCERTYPE = cancerTypeDao.getCancerTypeById(1);


    public static void main(String[] args) throws ParseException
    {
        StudyCase studyCase = new StudyCase();
        studyCase.setDiseaseName(cancerTypeDao.getCancerTypeById(6).getName());
        studyCase.setNumSamples(10);
        studyCase.setPublicStudy(false);
        studyCase.setStudyName("Heterogeneity Analysis of HNSCC (Patient 070)");

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = "20130101";

        Date projectStartDate = dateFormat.parse(dateString);
        studyCase.setStudyStartDate(projectStartDate);
        studyCase.setStudyDescription("This is the study description section: 070 - Heterogeneity Analysis of HNSCC (derived from Patient 070) in PDTX Mouse Models ");

        studyCase.setLaboratory(laboratoryDao.getLaboratoryById(1L));  // set the study case to Jimeno's lab

        System.out.println("Project start date is: " + projectStartDate);

        /* create samples for this study case by parsing the Annovar output */
        Set<Sample> samples = new HashSet<Sample>();

        /*

        Sample sample = new Sample();
        sample.setCancerType(cancerTypeDao.getCancerTypeById(1));
        sample.setSampleName("sample 1");
        sample.setSampleSource("Patient");
        sample.setGenomeMethodTypeId(1);   // exome-sequencing
        sample.setSequencingInstrument("Illumina HiSeq2000");
        sample.setSequencingCenter("UCDenver");
        sample.setSampleDescription("Solid Tumor from patient");
        sample.setStudyCase(studyCase);

        samples.add(sample);


        Sample sample2 = new Sample();
        sample2.setCancerType(cancerTypeDao.getCancerTypeById(1));
        sample2.setSampleName("sample 2");
        sample2.setSampleSource("Mouse");
        sample2.setGenomeMethodTypeId(1);   // exome-sequencing
        sample2.setSequencingInstrument(SEQUENCINGINSTRUMENT);
        sample2.setSequencingCenter(SEQUENCINGCENTER);
        sample2.setSampleDescription("Solid Tumor from patient");
        sample2.setStudyCase(studyCase);  // IS THIS REQUIRED????

        samples.add(sample2);

        studyCase.setSamples(samples);
        */


        /************** Parse the CSV file output from Annovar to save the results into database *****************/
    //    String[] sampleNames = {"F2116R", "F2119R",	"F1616L", "F1777L",	"F1778L", "F1778R",	"F2116L", "F2118L",	"F2120L", "FO1", "FO2", "F1616R"};
        String[] sampleNames = {"F1551L", "F1551R",	"F1553L", "F1553R",	"F2487R", "F2488L",	"F2488R", "F2489L",	"F2489R", "FO"};


        for (String sampleName : sampleNames)
        {
            Sample sample = new Sample();
            sample.setCancerType(BATCHCANCERTYPE);
            sample.setSampleName(sampleName);
            sample.setSampleType("Cancer Sample");

            if (sampleName.contains("FO"))
                sample.setSampleSource("Patient");
            else
                sample.setSampleSource("Mouse");

            sample.setSampleDescription(sampleName + " solid tumor sample");
            sample.setGenomeMethodTypeId(1);    // exome-sequencing
            sample.setSequencingCenter(SEQUENCINGCENTER);
            sample.setSequencingInstrument(SEQUENCINGINSTRUMENT);
            sample.setStudyCase(studyCase);

            samples.add(sample);
        }
        studyCase.setSamples(samples);

        studyCaseDao.saveStudyCase(studyCase);





    }
}
