package testServices;

import dao.*;
import models.HumanGene;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.results.DNASeqResult;
import models.results.DNASeqResultSample;
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
 * Created by tonywang on 7/1/14.
 */
public class TCGAStudySamples
{
    private final static StudyCaseDao studyCaseDao = new StudyCaseDao();
    private final static CancerTypeDao cancerTypeDao = new CancerTypeDao();
    private final static LaboratoryDao laboratoryDao = new LaboratoryDao();
    private final static HumanGeneDao humanGeneDao = new HumanGeneDao();
    private final static SampleDao sampleDao = new SampleDao();
    private final static DNASeqResultDao dnaSeqResultDao = new DNASeqResultDao();
    private final static DNASeqResultSampleComboDao comboDao = new DNASeqResultSampleComboDao();

    private final static String HETMUTANT = "Heterozygous_Mutation";
    private final static String HOMOMUTANT = "Homozygous_Mutation";
    private final static String WILDTYPE = "Wildtype";

    private static final int ADFIELD = 2;  // starting from 1



    public static void TCGAAnnovarCSVReader(String fileName, String studyName, String sampleName) throws FileNotFoundException, IOException
    {

        File studyCaseNameFile = new File(fileName);

        if (!studyCaseNameFile.exists()) {
            System.out.println("study name does not exist!");
            System.exit(-1);
        }

        StudyCase studyCase = studyCaseDao.getStudyCaseByName(studyName);
        Sample curSample = sampleDao.getSampleByName(sampleName);

        /* if either studyCase or curSample is null, return */
        if (studyCase == null || curSample == null) return;

        ICsvListReader csvReader = null;
        int successCount = 0;

        try {
            csvReader = new CsvListReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
            csvReader.getHeader(true);

            List<String> contentList;


            while ((contentList = csvReader.read())!= null)
            {
                String chromosome = contentList.get(21) == null ? "NA" : contentList.get(21);
                Long startPosition = contentList.get(22) == null ? 0L : Long.parseLong(contentList.get(22));
                Long endPosition = contentList.get(23) == null ? 0L : Long.parseLong(contentList.get(23));
                String aaChange = contentList.get(3);
                double polyPhenScore = contentList.get(10) == null ? 0 : Double.parseDouble(contentList.get(10));
                String exonicFunction = contentList.get(2);

                if (exonicFunction == null || exonicFunction.equals("unknown")) {
                    exonicFunction = "---";
                    // continue;  // pass unknown
                }

                HumanGene gene = humanGeneDao.getGeneBySymbol(contentList.get(1));

                if (gene == null)   // skip genes that couldn't be found
                    continue;

                String mutations = contentList.get(3);
                if (mutations == null) continue;
                String[] changeContents = mutations.split(":");

                if (changeContents.length == 3)
                {
                        aaChange = changeContents[1].trim() + ": " + changeContents[2].trim();
                //        System.out.println("AA mutation is " + aaChange);
                }
                else
                    continue;

                DNASeqResult result = new DNASeqResult();
                result.setChromosome("chr" + chromosome);
                result.setStartPosition(startPosition);
                result.setEndPosition(endPosition);
                result.setAminoAcidSubstition(aaChange);
                result.setPolyPhenScore(polyPhenScore);
                result.setExonicFunction(exonicFunction);


                DNASeqResultSample comboObject = new DNASeqResultSample();
                comboObject.setHumanGene(gene);
                comboObject.setDnaSeqResult(result);

/*                String[] namesPart = studyName.split("-");
                List<Sample> currentSamples = sampleDao.getSamplesByName(sampleName);
                Sample workingSample = null;
                for (Sample s : currentSamples)
                {
                    if (s.getStudyCase().equals(studyCase))
                    {
                        workingSample = s;
                        break;
                    }
                }



                if (workingSample == null)
                {
               //     System.out.println("warning! workingSample is null!, sampleName: " + sampleName);
              //      return;
                    continue;
                }
*/
                successCount++;

//                System.out.println("currentSample name: " + curSample.getSampleName());

                comboObject.setSample(curSample);
                comboObject.setNumRefReads(-1);
                comboObject.setNumVariantReads(-1);

                comboObject.setGenotype("---");
                result.getDnaSeqResultSamples().add(comboObject);

                dnaSeqResultDao.saveResult(result);
                System.out.println("persisted this result: " + result.getAminoAcidSubstition());
            }


        } finally {
            if (csvReader != null)
            {
                csvReader.close();
                System.out.println("TCGA data import is done! success count: " + successCount + ", sampleName: " + sampleName);
            }
        }
    }
}
