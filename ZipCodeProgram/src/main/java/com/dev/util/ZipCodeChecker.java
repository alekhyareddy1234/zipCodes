
package com.dev.util;

import com.dev.util.ZipCodeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 * @author reddy_a
 *
 */
@Service("ZipCodeChecker")
public class ZipCodeChecker {
    
    
    /*
     * Private constructor.
     */
    private ZipCodeChecker() {
        // prevent instantiation
    }

    private static Pattern zipCodePattern = Pattern.compile("(\\d{5})");

    /**
     * Consolidates a list of ZipCodeUtil objects into the shortest possible grouping of ranges.
     * @param ranges The list of ZipCodeUtil objects to be processed
     * @return A List of sorted (ascending) ZipCodeUtil objects
     */
    public static List<ZipCodeUtil> consolidate(List<ZipCodeUtil> ranges) {
        Set<ZipCodeUtil> sortedRanges = new TreeSet<>(ZipCodeUtil.COMPARATOR);
        if (ranges != null) {
            ranges.sort(ZipCodeUtil.COMPARATOR);
            for (ZipCodeUtil zcr : ranges) {
                // create a copy, so the original object is not change by a future merge
                ZipCodeUtil merge = ZipCodeUtil.copy(zcr);
                boolean didOverlap = false;
                for (ZipCodeUtil existingRange : sortedRanges) {
                    if (existingRange.isMergeable(merge)) {
                        existingRange.merge(merge);
                        didOverlap = true;
                        break;
                    }
                }
                if (!didOverlap) {
                    sortedRanges.add(merge);
                }
            }
        }
        return new ArrayList<>(sortedRanges);
    }

    /**
     * Checks if the specified ZIP code should be excluded (contained) by any of the known ZIP code ranges.
     * @param zipCode The ZIP code to check
     * @param excludeRange The List of ZipCodeUtil object to use for exclusion
     * @return true if the ZIP code is contained by the exclusion range; otherwise false
     */
    public static boolean isExcluded(String zipCode, List<ZipCodeUtil> excludeRange) {
        boolean result = false;
        if (zipCode != null) {
            Matcher matcher = zipCodePattern.matcher(zipCode);
            if (matcher.matches()) {
                result = isExcluded(Integer.valueOf(matcher.group(1)), excludeRange);
            }
            else {
                throw new IllegalArgumentException("Invalid ZIP code: " + zipCode);
            }
        }
        return result;
    }

    /**
     * Checks if the specified ZIP code should be excluded (contained) by any of the known ZIP code ranges.
     * @param zipCode The ZIP code to check
     * @param excludeRange The List of ZipCodeUtil object to use for exclusion
     * @return true if the specified ZIP code is contained by the exclusion range; otherwise false
     */
    public static boolean isExcluded(int zipCode, List<ZipCodeUtil> excludeRange) {
        boolean result = false;
        if (zipCode < 0 || zipCode > 99999) {
            throw new IllegalArgumentException("Invalid ZIP code: " + zipCode);
        }
        if (excludeRange != null) {
            for (ZipCodeUtil range : excludeRange) {
                if (zipCode >= range.getStart() && zipCode <= range.getEnd()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Checks of the specified ZIP code is in the specific range. Both start and end ranges values are considered
     * to be inclusive, so:
     * <pre>
     * <code>10 in [1,10]  = true</code>
     * <code>10 in [10,20] = true</code>
     * <code>10 in [1,20]  = true</code>
     * <code>10 in [11,20] = false</code>
     * </pre>
     * @param zipCode The ZIP code to test against the range
     * @param range The range to be tested for the ZIP code
     * @return true if the specified ZIP code is in the specified range; otherwise false
     */
    public static boolean isInRange(int zipCode, ZipCodeUtil range) {
        boolean result = false;
        if (range != null) {
            result = (range.getStart() <= zipCode && zipCode <= range.getEnd());
        }
        return result;
    }
    
}