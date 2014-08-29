package testServices;

import dao.HumanGeneDao;
import dao.KeggPathwayDao;
import models.HumanGene;
import models.KeggPathway;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by tonywang on 6/23/14.
 */
public class getHumanGenes2
{
    private static final int ENTREZGENECOLUMN = 8;   // counting started from 0
    private static HumanGeneDao humanGeneDao = new HumanGeneDao();


    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        String geneFileName = "/Users/tonywang/Dropbox/Bioinfo_Dev/HugoGenes2.txt";
        String keggFilename = "/Users/tonywang/Desktop/Bioinfo_Dev/Pathways/ParsedKeggPathwaywithEntrez.txt";
   //     String keggFilename = "/Users/guoliangwang/Dropbox/Bioinfo_Dev/KeggPathwayWithEntrezID.txt";

        ICsvListReader geneListReader = null;
        ICsvListReader pathListReader = null;
        Map<String, List<String>> genesMap = new HashMap<String, List<String>>();
        KeggPathwayDao keggPathwayDao = new KeggPathwayDao();

        try {
            geneListReader= new CsvListReader(new FileReader(geneFileName), CsvPreference.TAB_PREFERENCE);
            pathListReader = new CsvListReader(new FileReader(keggFilename), CsvPreference.TAB_PREFERENCE);

            List<String> geneListContent = null;
            List<String> pathListContent = null;

            /*

            while ((geneListContent = geneListReader.read()) != null)
            {
                String entrezGeneId = geneListContent.get(ENTREZGENECOLUMN);

                if (entrezGeneId == null) {
                    System.out.println("entrez Gene id is null! continue!");
                    continue;

                }
                System.out.println("Entrez gene id is: " + entrezGeneId);

                genesMap.put(entrezGeneId, geneListContent);
            }

            // persist all the genes
            for (Map.Entry<String, List<String>> entry : genesMap.entrySet())
            {
                String entrezGeneId = entry.getKey();
                List<String> hugoGeneEntry = entry.getValue();

                String geneSymbol = (String) hugoGeneEntry.get(1);
                String geneFullName = (String) hugoGeneEntry.get(2);
                String chromosome = (String) hugoGeneEntry.get(6) == null ? "NA" : (String) hugoGeneEntry.get(6);
                String ensembleGeneId = (String) hugoGeneEntry.get(9) == null ? "NA" : (String) hugoGeneEntry.get(9);
                String refSeqId = (String) hugoGeneEntry.get(11) == null ? "NA" : (String) hugoGeneEntry.get(11);
                String geneFamilyDescription = (String) hugoGeneEntry.get(12) == null ? "NA" : (String) hugoGeneEntry.get(12);
                String unitProtId = (String) hugoGeneEntry.get(14) == null ? "NA" : (String) hugoGeneEntry.get(14);
                String geneSynonyms = (String) hugoGeneEntry.get(5) == null ? "NA" : (String) hugoGeneEntry.get(5);

                if (geneSymbol == null || geneFullName == null || entrezGeneId == null)
                    continue;

                HumanGene gene = humanGeneDao.getGeneByEntrezId(entrezGeneId);
                if (gene == null)
                    humanGeneDao.saveGene(new HumanGene(geneSymbol, geneFullName, chromosome, entrezGeneId, ensembleGeneId, unitProtId, refSeqId, geneFamilyDescription, geneSynonyms));
            }
            */





            while ((pathListContent = pathListReader.read()) != null)
            {
                System.out.println("now at the kegg pathway of: " + pathListContent.get(0));

                KeggPathway currentPathway = new KeggPathway();
                currentPathway.setKeggPathwayFullName(pathListContent.get(0));
                currentPathway.setKeggPathwayUrl(pathListContent.get(1));
                currentPathway.setKeggPathHSASymbol(pathListContent.get(2));
                currentPathway.setNumGene(Integer.parseInt(pathListContent.get(pathListContent.size()-1)));

                Set<HumanGene> genes = new HashSet<>();

                for (int i = 3; i < pathListContent.size()-1; i++)
                {
                    String entrezGeneId = pathListContent.get(i);
                    HumanGene curGene = humanGeneDao.getGeneByEntrezId(entrezGeneId);

                    if (curGene != null)
                        genes.add(curGene);
                }

                currentPathway.setHumanGenes(genes);

                keggPathwayDao.savePathwayWithHumanGenes(currentPathway);
            }




        } finally {
            if (geneListReader != null)
                geneListReader.close();

            if (pathListReader != null)
                pathListReader.close();

            System.out.println("success, all done!");
        }
    }
}
