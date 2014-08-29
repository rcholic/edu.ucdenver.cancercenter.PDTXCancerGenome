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
    public static enum instituteType {
        UNIVERSITY,
        RESEARCH_INSTITUTE,
        HOSPITAL,
        COMMERCIAL_CORPORATE;
    }

    private static Map<Integer, instituteType> integerStringMap;
    private static Map<instituteType, Integer> stringIntegerMap;
    private static List<instituteType> types;

    public InstitutionType() {

        integerStringMap = new HashMap<>();
        stringIntegerMap = new HashMap<>();
        types = new ArrayList<instituteType>();
        int index = 0;

        for (instituteType type : instituteType.values())
        {
            integerStringMap.put(index, type);
            stringIntegerMap.put(type, index++);
            types.add(type);
        }
    }

    public static instituteType convertIdToInstituteTypeName(int id)
    {
        if (integerStringMap.containsKey(id))
        {
            return integerStringMap.get(id);
        }

        return null;
    }

    public static int convertTypeNameToId(instituteType type)
    {
        if (stringIntegerMap.containsKey(type))
            return stringIntegerMap.get(type);

        return -1;  /* not found */

    }

    public static List getAllTypes()
    {
         return types;
    }
}
