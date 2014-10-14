import models.cancer.CancerType;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.institutions.Institution;
import models.institutions.Laboratory;

import java.util.List;

/**
 * Created by tonywang on 10/14/14.
 * Interface for client interaction
 * can add/list the following:
 * @param institutes
 * @param labs
 * @param studyCases
 * @param samples associated with the studyCase
 * @param cancerTypes
 *
 */
public class Main
{
    private static List<Institution> institutes;
    private static List<Laboratory> labs;
    private static List<StudyCase> studyCases;
    private static List<Sample> samples;
    private static List<CancerType> cancerTypes;

    public static void main(String[] args) {

        if (!args[0].equals("-h") && args.length < 2) {
            System.err.println("Command line must contain at least 2 arguments!, type java Main -h for help");
        }

        if (args[0].equals("-h")) {
            System.out.println("\n Available Commands:\n -List \n -Add \n -Remove");
            System.out.println("\n Available Entities: \n Institution \n Lab \n Sample \n CancerType");
            System.out.println("Example - add a study case with its samples\n -Add -Study \"studyName\" -Sample \"sampleName1\" -Sample \"sampleName2\" ");

            // -Add -Study " studyName " -Sample " sampleName1 " -Sample "sampleName2"
        }
    }
}
