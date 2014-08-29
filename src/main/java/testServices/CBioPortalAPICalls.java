package testServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tonywang on 6/30/14.
 */
public class CBioPortalAPICalls
{
    private final static String cmd = "getCaseLists"; //"getCaseLists";
    private final static String cancerStudyId = "gbm_tcga";
    private final static String genetic_profile_id = "gbm_mutations";

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.cbioportal.org/public-portal/webservice.do?");
        String genes = "TP53,PIK3CA";

        String query = "cmd=" + cmd + "&cancer_study_id=hnsc_tcga";

        //make connection
        URLConnection urlConnection = url.openConnection();

        //use post
        urlConnection.setDoOutput(true);
        urlConnection.setAllowUserInteraction(false);

        //send query call
        PrintStream ps = new PrintStream(urlConnection.getOutputStream());
        ps.print(query);
        ps.close();

        //read results
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line = null;

        while ((line = br.readLine()) != null)
        {
            System.out.println(line);
        }
        br.close();


    }
}
