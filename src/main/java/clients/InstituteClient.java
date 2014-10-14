package clients;

import dao.CommonDao;
import models.institutions.Institution;
import models.institutions.Laboratory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 10/14/14.
 * Used for adding institution with its laboratories
 */
public class InstituteClient
{
    final static CommonDao commonDao = new CommonDao();

    public static void main(String[] args)
    {
        Institution ucAMC = new Institution();
        ucAMC.setInstituteName("University of Colorado Anschutz Medical Campus");
        ucAMC.setInstituteCountry("United States of America");
        ucAMC.setInstityteTypeId(1);   //InstitutionType.convertTypeNameToId(InstitutionType.instituteType.valueOf("UNIVERSITY")));
        ucAMC.setInstituteAddress("Aurora, CO 80045");

        Set<Object> laboratories = new HashSet<Object>();

        Laboratory laboratory1 = new Laboratory();
        laboratory1.setInstitution(ucAMC);
        laboratory1.setLabAddress("The Cancer Genome Atlas Program Office, National Cancer Institute, 31 Center Drive, Bldg, 31, Suite 3A20,\" +\n" +
                "                \"Bethesda, MD 20892");
        laboratory1.setLabPIName("NCI Press Officers");
        laboratory1.setLabPhone("301-496-6641");
        laboratory1.setLabWebUrl("http://cancergenome.nih.gov/");



        laboratories.add(laboratory1);

        commonDao.saveObjectWithManyEntities(ucAMC, laboratories);

        System.out.println("ucDenver's institute type ID is: " + ucAMC.getInstityteTypeId());

    }
}
