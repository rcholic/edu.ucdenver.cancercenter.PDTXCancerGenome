package clients;

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
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonywang on 10/15/14.
 * This will import the csv file annotated by ANNOVAR
 * The columns starting 36th should include the samples' names
 * The sample names and their associated study case should be first saved prior to running this program
 *
 */
public class ImportSamplesData
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

    /* path to the CSV file output by ANNOVAR (use the file */
    private static final String fileName = "/Users/tonywang/Dropbox/java/Ninja/edu.ucdenver.cancercenter.PDTXCancerGenome/070_SNPs_filtered.csv";
    private static final String caseName = "";

    public static void main(String[] args) throws IOException
    {
        StudyCase studyCase = studyCaseDao.getStudyCaseByName(caseName);
        List<Sample> samplesInThisStudy = new ArrayList<>(); // sampleDao.getSamplesByStudyCase(studyCase);

//              String fileName = "/Users/tonywang/Desktop/Data/Jimeno/Steve/CUHN070_annovar_withAgilent_20131216/exome.indels.filtered.selectedIndels/070_Indels_filtered.csv";
//              String fileName = "/Users/tonywang/Desktop/Data/Jimeno/Steve/CUHN076_reSeq_batch20140505/annovar/20140505_merged_076_repeated_Agilent_bed_covered.snps.filtered.selected/076_snpsJavaFiltered.csv";
//              String fileName = "/Users/tonywang/Desktop/Data/Jimeno/Steve/CUHN076_reSeq_batch20140505/annovar/20140505_merged_076_repeated_Agilent_bed_covered.indels.filtered.selected/076_indelsJavaFiltered.csv";

        /** new raw CSV file **/
        //String fileName = "/Users/tonywang/Desktop/Data/Jimeno/Steve/CUHN070_annovar_withAgilent_20131216/exome.indels.filtered.selectedIndels/exome.indels.filtered.selectedIndels.exome_summary.csv";
        //String fileName = "/Users/tonywang/Desktop/Data/Jimeno/Steve/CUHN070_annovar_withAgilent_20131216/exome.snps.filtered.selectedSNPs/exome.snps.filtered.selectedSNPs.exome_summary.csv";


        File inputFile = new File(fileName);
        String[] headerRow = null;

        if (inputFile.exists())
        {
            FileReader fr = new FileReader(inputFile);
            LineNumberReader lineReader = new LineNumberReader(fr);
            String headerLine = null;

            while (lineReader.getLineNumber() == 0)
            {
                headerLine = lineReader.readLine();
            }
            lineReader.close();

            if (headerLine != null)
                headerRow = headerLine.split(",");

            /* sampleNamesArray contains all the sample name strings */
            String[] sampleNamesArray = new String[headerRow.length-36];  // column #36 is the first sample (counting from 1)

            int startHeaderIndex = 35, newIndex = 0;
            while (startHeaderIndex < headerRow.length-1)
            {
                sampleNamesArray[newIndex++] = headerRow[startHeaderIndex++];
            }

            Sample sample = null;
            for (String sampleName : sampleNamesArray)
            {
                sample = sampleDao.getSampleByName(sampleName);
                if (sample != null)
                    samplesInThisStudy.add(sample);
                else {
                    System.out.println("Cannot retrieve the sample! Sample name: " + sampleName);
                }
            }


            /* parse the result - CSV file filtered by Java on the Annovar output (filtered out synonymous SNPs, polyPhen >= 0.6 */
            ICsvListReader listReader = null;

            try {
                listReader = new CsvListReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
                List<String> contentList;
                listReader.getHeader(true);
//                HumanGene currentGene = null;

//                boolean saveSampleIdHumanGeneId = false;   /* flag used for deciding whether save sampleIDGeneID */

                while ((contentList = listReader.read()) != null)
                {
                    String chromosome = contentList.get(21) == null ? "NA" : contentList.get(21);
                    Long startPosition = contentList.get(22) == null ? 0L : Long.parseLong(contentList.get(22));
                    Long endPosition = contentList.get(23) == null ? 0L : Long.parseLong(contentList.get(23));
                    String aaChange = contentList.get(3);
                    double polyPhenScore = contentList.get(10) == null ? 0 : Double.parseDouble(contentList.get(10));
                    String exonicFunction = contentList.get(2);

                    if (exonicFunction == null || exonicFunction.equals("unknown"))
                        continue;  // pass unknown

                    HumanGene gene = humanGeneDao.getGeneBySymbol(contentList.get(1));

                    if (gene == null)   // skip genes that couldn't be found
                        continue;

                    String mutations = contentList.get(3);
                    if (mutations == null) continue;
                    String[] changeContents = mutations.split(":");
                    /*
                    if (changeContents.length == 3)
                    {
                        aaChange = changeContents[1].trim() + " : " + changeContents[2].trim();
                        System.out.println("AA mutation is " + aaChange);
                    }
                    else
                        continue;
                        */

                    DNASeqResult result = new DNASeqResult();
                    result.setChromosome(chromosome);
                    result.setStartPosition(startPosition);
                    result.setEndPosition(endPosition);
                    result.setAminoAcidSubstition(aaChange);
                    result.setPolyPhenScore(polyPhenScore);
                    result.setExonicFunction(exonicFunction);


                    int startColumNum = 35;
                    int endColumnNum = contentList.size() - 1;   // last column is the additiona column for total num of alt reads

                    for (int i = 0; i < samplesInThisStudy.size(); i++)
                    {
                        DNASeqResultSample comboObject = new DNASeqResultSample();
                        comboObject.setHumanGene(gene);
                        comboObject.setDnaSeqResult(result);

                        Sample currentSample = samplesInThisStudy.get(i);

                        comboObject.setSample(currentSample);                // keep replacing the sample in the same combo object

                        String cellContent = contentList.get(startColumNum++);
                        String genoType = null;
                        if (!cellContent.equals("./.") && cellContent != null)
                        {
                            String[] cellString = cellContent.split(":");
                            String[] ADField = cellString[ADFIELD-1].split(",");
                            String GTField = cellString[0];

                            int numRefAlleles = isInteger(ADField[0]) ? Integer.parseInt(ADField[0]) : 0;
                            int numAltAlleles = isInteger(ADField[0]) ? Integer.parseInt(ADField[1]) : 0;

                            comboObject.setNumRefReads(numRefAlleles);
                            comboObject.setNumVariantReads(numAltAlleles);

                            if (GTField.matches("0/[1-9]{1}"))  // het ALT
                            {
                                genoType = HETMUTANT;
                            } else if (GTField.matches("[1-9]/[1-9]")) // homo ALT
                            {
                                genoType = HOMOMUTANT;
                            } else if (GTField.equals("0/0")) // homo REF
                            {
                                genoType = WILDTYPE;
                            }
                        }
                        else
                        {
                            genoType = "NA";  /* in this case, numRefalleles and numAltAlleles are 0 */
                        }

                        comboObject.setGenotype(genoType);
                        result.getDnaSeqResultSamples().add(comboObject);


                        System.out.println("persist the result!");
                    }
                    dnaSeqResultDao.saveResult(result);
                }

            } finally {
                if (listReader != null) {
                    lineReader.close();
                    System.out.println("success! number of samples: " + samplesInThisStudy.size());
                }
            }
        }
    }

    /* check if a String is an integer */
    public static boolean isInteger(String str)
    {
        try
        {
            int num = Integer.parseInt(str);
        } catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }
}
