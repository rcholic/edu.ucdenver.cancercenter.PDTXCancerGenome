package testServices;

import java.io.IOException;

/**
 * Created by tonywang on 7/8/14.
 */
public class systemCmd
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        String cmd = "/bin/bash ls -lrah";
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();

        process.getOutputStream().close();
        System.out.println();
    }
}
