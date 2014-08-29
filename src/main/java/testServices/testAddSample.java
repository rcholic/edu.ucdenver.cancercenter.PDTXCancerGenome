package testServices;

import dao.CancerTypeDao;
import dao.SampleDao;
import dao.StudyCaseDao;
import models.cancer.CancerType;
import models.cancer.Sample;
import models.cancer.StudyCase;

/**
 * Created by tonywang on 6/25/14.
 */
public class testAddSample
{
    private final static StudyCaseDao studyCaseDao = new StudyCaseDao();
    private final static SampleDao sampleDao = new SampleDao();
    private final static CancerTypeDao cancerTypeDao = new CancerTypeDao();

    private final static String SEQUENCINGCENTER = "UCDenver Sequencing Core";
    private final static String SEQUENCINGINSTRUMENT = "Illumina HiSeq2000";
    private final static CancerType BATCHCANCERTYPE = cancerTypeDao.getCancerTypeById(1);

    public static void main(String[] args)
    {
        StudyCase studyCase = studyCaseDao.getStudyCaseById(1L);

        Sample sample = new Sample();
        String sampleName = "F2489R";
        sample.setCancerType(BATCHCANCERTYPE);
        sample.setSampleName(sampleName);
        sample.setSampleType("Cancer Sample");

        if (sampleName.contains("FO"))
            sample.setSampleSource("Patient");
        else
            sample.setSampleSource("Mouse");

        sample.setSampleDescription(sampleName + " slid tumor sample");
        sample.setGenomeMethodTypeId(1);    // exome-sequencing
        sample.setSequencingCenter(SEQUENCINGCENTER);
        sample.setSequencingInstrument(SEQUENCINGINSTRUMENT);
        sample.setStudyCase(studyCase);

        sampleDao.saveSample(sample);


    }
}
