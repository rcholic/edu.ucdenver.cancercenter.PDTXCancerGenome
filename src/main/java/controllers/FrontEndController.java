package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.*;

import models.HumanGene;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.results.DNASeqResult;
import models.results.DNASeqResultSample;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import java.util.*;

/**
 * Created by tonywang on 6/26/14.
 */
@Singleton
public class FrontEndController
{
    @Inject
    HumanGeneDao humanGeneDao;

    @Inject
    SampleDao sampleDao;

    @Inject
    DNASeqResultDao dnaSeqResultDao;

    @Inject
    DNASeqResultSampleComboDao dnaSeqResultSampleComboDao;

    @Inject
    StudyCaseDao studyCaseDao;

    @Inject
    CancerTypeDao cancerTypeDao;

    public Result index()
    {
        return Results.html().render("cancerTypes", cancerTypeDao.findAllCancerTypes()).render("samples", sampleDao.findAllSamples());
    }

    public Result searchResults(Context context)
    {
        String ipAddress = context.getRemoteAddr();   // get remote IP Address

        String keywords = context.getParameter("keywords");
        Set<String> keywordSet = new HashSet<>();
        Set<HumanGene> genes = new HashSet<>();

        String[] keywordArray = null;
        if (keywords.contains(","))
        {
            keywordArray = keywords.split(",");

            keywords = keywords.replace(",", " | ");

            for (String key : keywordArray)
                keywordSet.add(key);

            for (String str : keywordSet)
            {
                HumanGene queryGene = humanGeneDao.getGeneBySymbol(str.trim());
                if (queryGene != null)
                {
                    genes.add(queryGene);
                }
            }
        }
        else
        {
            HumanGene queryGene = humanGeneDao.getGeneBySymbol(keywords.trim());
            if (queryGene != null)
            {
                genes.add(queryGene);
            }
        }


        /* start the query process */
        TreeSet<Sample> samples = null;
        List<StudyCase> studyCases = null;
        List<DNASeqResultSample> resultSampleCombos = null;
        int numCases = 0;

        if (genes.size() > 0)
        {
            samples = new TreeSet<>(Sample.groupSampleByStudyCase);  // sort the samples by study case first, then by ID
            resultSampleCombos = new ArrayList<>();
            studyCases = new ArrayList<StudyCase>();
            StudyCase oldStudyCase = null;


            for (HumanGene gene : genes)
            {
                List<DNASeqResultSample> resultSampleCombo = dnaSeqResultSampleComboDao.getDnaSeqResultSampleComboByGene(gene);

                if (resultSampleCombo != null)
                {
                    for (DNASeqResultSample ds : resultSampleCombo)
                    {
                        Sample sample = ds.getSample();
                        samples.add(ds.getSample());
                        resultSampleCombos.add(ds);

                        if (oldStudyCase == null || !oldStudyCase.equals(sample.getStudyCase()))
                        {
                            oldStudyCase = sample.getStudyCase();
                            numCases++;            /* increment the number of study cases */

                            studyCases.add(oldStudyCase);
                        }
                    }
                }

            }
        }

        Collections.sort(resultSampleCombos, DNASeqResultSample.groupComboByGeneSymbol);  /* sort the results by gene symbols */

        Result result = Results.html();
        result.render("keywords", keywords);

        if (samples !=null && samples.size() > 0) {
            result.render("samples", samples).render("num_samples", samples.size())
                    .render("studyCases", studyCases).render("num_cases", numCases)
                    .render("resultSamples", resultSampleCombos);
        }

        else
            result.render("nosamples", "has no samples found for the keyword(s): " + keywords);

        return result;

//        return Results.html().render("keywords", keywords).render("samples", samples);
    }

    public Result studyDetails(Context context, @PathParam("studyId") Long studyId)
    {
        Result result = Results.html();
        StudyCase studyCase = studyCaseDao.getStudyCaseById(studyId);
        if (studyCase != null)
        {
            Set<Sample> samples = studyCase.getSamples();
            result.render("studycase", studyCase).render("samples", samples);
        }
        else
        {
            result.render("noStudyCase", "Sorry we did not find any study case with id of " + studyId).render("cancerTypes", cancerTypeDao.findAllCancerTypes());
        }

        return result;
    }

    public Result sampleDetails(Context context, @PathParam("sampleId") Long sampleId)
    {
        Result result = Results.html();
        Sample sample = sampleDao.getSampleById(sampleId);

        if (sample != null)
        {
            List<DNASeqResultSample> combos = dnaSeqResultSampleComboDao.getDnaSeqResultsSampleComboBySample(sample);
            result.render("sample", sample).render("combos", combos).render("combosSize", combos.size());
        }
        else
        {
            result.render("noSample", "Sorry we did not find any sample with id of " + sampleId);
        }

        return result;
    }

    public Result sampleList()
    {
        List<Sample> samples = null;
        samples = sampleDao.findAllSamples();

        Result result = Results.html();

        if (samples.size() == 0)
            result.render("hasNoSample", "0 samples found");
        else
            result.render("samples", samples);

        return result;
    }

    public Result studyCaseList()
    {
        List<StudyCase> studyCases = null;
        studyCases = studyCaseDao.findAllStudyCases();

        Result result = Results.html();
        if (studyCases.size() == 0)
            result.render("hasNoStudy", "0 study case found");
        else
            result.render("studyCases", studyCases);

        return result;
    }

    public Result getIP(Context context)
    {
        String ipAddress = context.getRemoteAddr();

        return Results.html().render("ipAddress", ipAddress);
    }


}
