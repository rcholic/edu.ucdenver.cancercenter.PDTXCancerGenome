package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonywang on 6/23/14.
 */
public class GenomicMethods
{
    private enum method {
        RNA_SEQUENCING,
        EXOME_SEQUENCING,
        WHOLE_GENOME_SEQUENCING,
        MICRORNA_SEQUENCING,
        DNA_MICROARRAY,
        RNA_MICROARRAY,
        SANGER_SEQUENCING;
    }

    private static Map<Integer, String> integerStringMap;
    private static Map<String, Integer> stringIntegerMap;
    private static List<String> allMethods;

    public GenomicMethods() {
        integerStringMap = new HashMap<>();
        stringIntegerMap = new HashMap<>();
        allMethods = new ArrayList<>();

        int index = 0;

        for (method m : method.values())
        {
            integerStringMap.put(index, m.toString());
            stringIntegerMap.put(m.toString(), index++);
            allMethods.add(m.toString());
        }
    }

    public String IdToMethodName(int id)
    {
        if (integerStringMap.containsKey(id))
        {
            return integerStringMap.get(id);
        }
        return null;
    }

    public int MethodNameToId(String key)
    {
        if (stringIntegerMap.containsKey(key))
            return stringIntegerMap.get(key);

        return -1;
    }

    public List<String> getAllMethods()
    {
        return allMethods;
    }
}
