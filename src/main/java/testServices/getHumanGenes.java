package testServices;

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
public class getHumanGenes
{
    private static final int ENTREZGENECOLUMN = 8;   // counting started from 0


    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        String geneFileName = "/Users/tonywang/Desktop/Bioinfo_Dev/Pathways/HugoGenes2.txt";
        String keggFilename = "/Users/tonywang/Desktop/Bioinfo_Dev/Pathways/ParsedKeggPathwaywithEntrez.txt";

        ICsvListReader geneListReader = null;
        ICsvListReader pathListReader = null;
        Map<String, List> genesMap = new HashMap<String, List>();
        KeggPathwayDao keggPathwayDao = new KeggPathwayDao();

        try {
            geneListReader= new CsvListReader(new FileReader(geneFileName), CsvPreference.TAB_PREFERENCE);
            pathListReader = new CsvListReader(new FileReader(keggFilename), CsvPreference.TAB_PREFERENCE);

            List<String> geneListContent = null;
            List<String> pathListContent = null;

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
                    System.out.println(genesMap.get(pathListContent.get(i)));
                    List hugoGeneEntry = genesMap.get(pathListContent.get(i));

                    if (hugoGeneEntry != null) {

                        String geneSymbol = (String) hugoGeneEntry.get(1);
                        String geneFullName = (String) hugoGeneEntry.get(2);
                        String chromosome = (String) hugoGeneEntry.get(6) == null ? "NA" : (String) hugoGeneEntry.get(6);
                        String entrezGeneId = (String) hugoGeneEntry.get(8);
                        String ensembleGeneId = (String) hugoGeneEntry.get(9) == null ? "NA" : (String) hugoGeneEntry.get(9);
                        String refSeqId = (String) hugoGeneEntry.get(11) == null ? "NA" : (String) hugoGeneEntry.get(11);
                        String geneFamilyDescription = (String) hugoGeneEntry.get(12) == null ? "NA" : (String) hugoGeneEntry.get(12);
                        String unitProtId = (String) hugoGeneEntry.get(14) == null ? "NA" : (String) hugoGeneEntry.get(14);
                        String geneSynonyms = (String) hugoGeneEntry.get(5) == null ? "NA" : (String) hugoGeneEntry.get(5);

                        if (geneSymbol == null || geneFullName == null || entrezGeneId == null)
                            continue;

                        genes.add(new HumanGene(geneSymbol, geneFullName, chromosome, entrezGeneId, ensembleGeneId, unitProtId, refSeqId, geneFamilyDescription, geneSynonyms));

                        System.out.println("this pathway has the following genes " + hugoGeneEntry.get(1));
                    }
                }

                currentPathway.setHumanGenes(genes);

                keggPathwayDao.savePathwayWithHumanGenes(currentPathway, genes);
            }

        } finally {
            if (geneListReader != null)
                geneListReader.close();

            if (pathListReader != null)
                pathListReader.close();
        }
    }
}
