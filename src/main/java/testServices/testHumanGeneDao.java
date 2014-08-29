package testServices;

import dao.HumanGeneDao;
import models.HumanGene;

import java.util.List;

/**
 * Created by tonywang on 6/25/14.
 */
public class testHumanGeneDao
{
    private final static HumanGeneDao humanGeneDao = new HumanGeneDao();

    public static void main(String[] args)
    {
        HumanGene curGene = humanGeneDao.getGeneByEntrezId("32");

        if (curGene != null)
        {
            System.out.println("found gene name:  " + curGene.getGeneSymbol() + ", " + curGene.getEntrezGeneId());
        }
        else
        {
            System.out.println("didn't found");
        }

        /*

        List<HumanGene> allGenes = humanGeneDao.findAllHumanGenes();

        for (HumanGene gene : allGenes)
        {
            if (gene.getGeneSymbol().equals("STPG2"))
            {
                System.out.println("found a match for A2M, ID is: " + gene.getGeneId() + ", name: " + gene.getGeneSymbol() + ", entrezID: " + gene.getEntrezGeneId());
            }
        }
        */

    }
}
