package testServices;

import dao.CommonDao;
import models.institutions.Institution;
import models.institutions.InstitutionType;
import models.institutions.Laboratory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tonywang on 6/24/14.
 */
public class testInstituteAndLab
{
    final static CommonDao commonDao = new CommonDao();

    public static void main(String[] args)
    {
        Institution TCGA = new Institution();
        TCGA.setInstituteName("The Cancer Genome Atlas (TCGA)");
        TCGA.setInstituteCountry("United States of America");
        TCGA.setInstityteTypeId(1);   //InstitutionType.convertTypeNameToId(InstitutionType.instituteType.valueOf("UNIVERSITY")));
        TCGA.setInstituteAddress("The Cancer Genome Atlas Program Office, National Cancer Institute, 31 Center Drive, Bldg, 31, Suite 3A20," +
                "Bethesda, MD 20892");

        Set<Object> laboratories = new HashSet<Object>();

        Laboratory laboratory1 = new Laboratory();
        laboratory1.setInstitution(TCGA);
        laboratory1.setLabAddress("The Cancer Genome Atlas Program Office, National Cancer Institute, 31 Center Drive, Bldg, 31, Suite 3A20,\" +\n" +
                "                \"Bethesda, MD 20892");
        laboratory1.setLabPIName("NCI Press Officers");
        laboratory1.setLabPhone("301-496-6641");
        laboratory1.setLabWebUrl("http://cancergenome.nih.gov/");



        laboratories.add(laboratory1);

        commonDao.saveObjectWithManyEntities(TCGA, laboratories);

        System.out.println("ucDenver's institute type ID is: " + TCGA.getInstityteTypeId());

    }
}
