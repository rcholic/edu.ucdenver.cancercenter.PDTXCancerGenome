package models.institutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonywang on 6/23/14.
 */
public final class InstitutionType
{
    public enum instituteType {
        UNIVERSITY,
        RESEARCH_INSTITUTE,
        HOSPITAL,
        COMMERCIAL_CORPORATE;
    }

    private static Map<Integer, String> integerStringMap;
    private static Map<String, Integer> stringIntegerMap;
    private static List<String> types;

    public InstitutionType() {

        integerStringMap = new HashMap<>();
        stringIntegerMap = new HashMap<>();
        types = new ArrayList<String>();
        int index = 0;

        for (instituteType type : instituteType.values())
        {
            integerStringMap.put(index, type.toString());
            stringIntegerMap.put(type.toString(), index++);
            types.add(type.toString());
        }
    }

    public String convertIdToInstituteTypeName(int id)
    {
        if (integerStringMap.containsKey(id))
        {
            return integerStringMap.get(id);
        }

        return null;
    }

    public int convertTypeNameToId(String type)
    {
        if (stringIntegerMap.containsKey(type))
            return stringIntegerMap.get(type);

        return -1;  /* not found */

    }

    public List<String> getAllTypes()
    {
         return types;
    }
}
