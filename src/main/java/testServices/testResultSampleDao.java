package testServices;

import dao.DNASeqResultDao;
import dao.DNASeqResultSampleComboDao;
import dao.HumanGeneDao;
import dao.SampleDao;
import models.HumanGene;
import models.cancer.Sample;
import models.results.DNASeqResult;
import models.results.DNASeqResultSample;

import java.util.List;

/**
 * Created by tonywang on 6/26/14.
 */
public class testResultSampleDao
{
    private static DNASeqResultSampleComboDao dnaSeqResultSampleComboDao = new DNASeqResultSampleComboDao();

    private static HumanGeneDao humanGeneDao = new HumanGeneDao();
    private static DNASeqResultDao dnaSeqResultDao = new DNASeqResultDao();

    private static SampleDao sampleDao = new SampleDao();

    public static void main(String[] args)
    {
        HumanGene curGene = humanGeneDao.getGeneBySymbol("TP53");

        List<DNASeqResult> results = dnaSeqResultDao.getDnaSeqResultsByGene(curGene);
        List<DNASeqResultSample> comboResults = null;
        if (results != null)
        {
           // comboResults = dnaSeqResultSampleComboDao.getDnaSeqResultsSampleComboBySample();
        }


        Sample sample = sampleDao.getSampleByName("F1778L");

        if (sample != null)
        {
            List<DNASeqResultSample> combo = dnaSeqResultSampleComboDao.getDnaSeqResultsSampleComboBySample(sample);

            if (combo != null)
            {
                for (DNASeqResultSample ds : combo)
                {
                    System.out.println(ds.getSample().getSampleName());
                }
            }
        }
    }
}
