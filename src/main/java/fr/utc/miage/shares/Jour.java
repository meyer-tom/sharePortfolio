/*
 * Copyright 2025 David Navarre <David.Navarre@irit.fr>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.utc.miage.shares;

/**
 * This class aims at describing a day based on a year and the day in this year.
 *
 * @author David Navarre &lt;David.Navarre@ut-capitole.fr&gt;
 */
public class Jour {

    /**
     * Year attribute.
     */
    private final int year;

    /**
     * Month attribute.
     */
    private final int month;

    /**
     * Day attribute.
     */
    private final int day;

    /**
     * Builds a Jour object from one day, month, and year.
     *
     * @param aYear the year of the jour &gt; 0
     * @param aMonth the month of the jour &gt; 0
     * @param aDay  theday of the jour &gt; 0
     */
    public Jour(final int aDay, final int aMonth, final int aYear) {
        if (0 >= aDay) {
            throw new IllegalArgumentException("Day must be strictly more than 0");
        }

        if (0 >= aMonth) {
            throw new IllegalArgumentException("Month must be strictly more than 0");
        }

        if (0 >= aYear) {
            throw new IllegalArgumentException("Year must be strictly more than 0");
        }
        this.day = aDay;
        this.month = aMonth;
        this.year = aYear;
    }

    /**
     * Returns the day of the jour.
     *
     * @return the day property
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the month of the jour.
     *
     * @return the month property
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the year of the jour.
     *
     * @return the year property
     */
    public int getYear() {
        return year;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + month;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Jour other = (Jour) obj;
        return (day == other.day) && (month == other.month) && (year == other.year);
    }

    @Override
    public String toString() {
        return "" + day + "/" + month + "/" + year;
    }
}
