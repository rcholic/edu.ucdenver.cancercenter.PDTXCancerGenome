package models.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonywang on 6/24/14.
 */
public final class MutationStatus
{
    public enum mutations
    {
        MISSENSE_MUTATION,
        NONSENSE_MUTATION,
        INSERTION,
        DELETION,
        DUPLICATION,
        FRAMESHIFT_MUTATION,
        NOT_MUTATED,
    }

    private static Map<Integer, String> integerStringMap;
    private static Map<String, Integer> stringIntegerMap;
    private static List<String> allMutationStatuses;

    public MutationStatus() {
        integerStringMap = new HashMap<>();
        stringIntegerMap = new HashMap<>();
        allMutationStatuses = new ArrayList<>();

        int index = 0;

        for (mutations mutation : mutations.values())
        {
            integerStringMap.put(index, mutation.toString());
            stringIntegerMap.put(mutation.toString(), index++);
            allMutationStatuses.add(mutation.toString());
        }
    }

    public static String idToMutationStatus(int id)
    {
        if (integerStringMap.containsKey(id))
        {
            return integerStringMap.get(id);
        }
        return null;
    }

    public static int mutationStatusToId(String key)
    {
        if (stringIntegerMap.containsKey(key))
            return stringIntegerMap.get(key);

        return -1;
    }

    public static List<String> getAllMutationStatuses()
    {
        return allMutationStatuses;
    }
}
