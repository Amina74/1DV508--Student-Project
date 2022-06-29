package timeLineDisplay;


public class TimelineCreator {
    /**
     * verifyADDate method checks if the date matches (YYYY-MM-DD).
     * @param date
     * @return true or false
     * @author Oliver Rimmi
     */
    public static boolean verifyADDate(String date) {
        if (date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return validateMonthAndDay(date);
        } else if (date.matches("^\\d{5}-\\d{2}-\\d{2}$")) {
            return validateMonthAndDay(date);
        } else if (date.matches("^\\d{6}-\\d{2}-\\d{2}$")) {
            return validateMonthAndDay(date);
        } else if (date.matches("^\\d{7}-\\d{2}-\\d{2}$")) {
            return validateMonthAndDay(date);
        } else if (date.matches("^\\d{8}-\\d{2}-\\d{2}$")) {
            return validateMonthAndDay(date);
        } else if (date.matches("^\\d{9}-\\d{2}-\\d{2}$")) {
            return validateMonthAndDay(date);
        } else if (date.matches("^\\d{10}-\\d{2}-\\d{2}$")) {
            return validateBigDate(date);
        } else if (date.matches("^\\d{11}-\\d{2}-\\d{2}$")) {
            return validateBigDate(date);
        }
        return false;
    }

    /**
     * verifyBCDate method checks if the given date matches 4-11 digits.
     * @param date
     * @return true of false
     * @author Oliver Rimmi
     */
    public static boolean verifyBCDate(String date) {
        if (date.matches("^\\d{4}$")) {
            return true;
        } else if (date.matches("^\\d{5}$")) {
            return true;
        } else if (date.matches("^\\d{6}$")) {
            return true;
        } else if (date.matches("^\\d{7}$")) {
            return true;
        } else if (date.matches("^\\d{8}$")) {
            return true;
        } else if (date.matches("^\\d{9}$")) {
            return true;
        } else if (date.matches("^\\d{10}$")) {
            return true;
        } else return date.matches("^\\d{11}$");
    }

    /**
     * verifyTitle method checks if the title given when creating a timeline is valid or not.
     * @param title The given title.
     * @return true or false
     * @author Oliver Rimmi
     */
    public static boolean verifyTitle(String title) {
        return !title.isEmpty() && title.length() < 20;
    }


    /**
     * validateMonthAndDay method checks if the date is valid. i.e if the month is within the range 01-12 and if
     * the date for a day is valid within the given month.
     * @param date The given date.
     * @return true or false
     * @author Oliver Rimmi
     */
    public static boolean validateMonthAndDay(String date) {
        StringBuilder buf = new StringBuilder();
        int year = 0, month = 0;
        int counter = 0;
        for (int i = 0; i <date.length(); i++) {
            if (date.charAt(i) == '-') {
                if (counter == 0) {
                    year = Integer.parseInt(String.valueOf(buf));
                    buf.delete(0, buf.length());
                    counter++;
                } else if(counter == 1) {
                    month = Integer.parseInt(String.valueOf(buf));
                    buf.delete(0, buf.length());
                }
            } else {
                buf.append(date.charAt(i));
            }
        }
        int day = Integer.parseInt(String.valueOf(buf));


        if (isLeapYear(year)) {
            if (month == 1) {
                return day > 0 && day < 32;

            } else if (month == 2) {
                return day > 0 && day < 30;

            } else if (month == 3) {
                return day > 0 && day < 32;

            } else if (month == 4) {
                return day > 0 && day < 31;

            } else if (month == 5) {
                return day > 0 && day < 32;

            } else if (month == 6) {
                return day > 0 && day < 31;

            } else if (month == 7) {
                return day > 0 && day < 32;

            } else if (month == 8) {
                return day > 0 && day < 32;

            } else if (month == 9) {
                return day > 0 && day < 31;

            } else if (month == 10) {
                return day > 0 && day < 32;

            } else if (month == 11) {
                return day > 0 && day < 31;

            } else if (month == 12) {
                return day > 0 && day < 32;

            }
        } else {
            if (month == 1) {
                return day > 0 && day < 32;

            } else if (month == 2) {
                return day > 0 && day < 29;

            } else if (month == 3) {
                return day > 0 && day < 32;

            } else if (month == 4) {
                return day > 0 && day < 31;

            } else if (month == 5) {
                return day > 0 && day < 32;

            } else if (month == 6) {
                return day > 0 && day < 31;

            } else if (month == 7) {
                return day > 0 && day < 32;

            } else if (month == 8) {
                return day > 0 && day < 32;

            } else if (month == 9) {
                return day > 0 && day < 31;

            } else if (month == 10) {
                return day > 0 && day < 32;

            } else if (month == 11) {
                return day > 0 && day < 31;

            } else if (month == 12) {
                return day > 0 && day < 32;

            }
        }
        return false;
    }

    /**
     * isLeapYear method checks if the given year is a leap year or not.
     * @param year
     * @return true or false
     * @author Oliver Rimmi
     */
    public static boolean isLeapYear(int year) {
        return java.time.Year.of(year).isLeap();
    }

    /**
     * validateBigDate method checks if the AD dates that has a year of 10-11 digits is valid.
     * It doesn't check for leap years.
     *
     * @param date
     * @return true or false
     * @author Oliver Rimmi
     */
    public static boolean validateBigDate(String date) {
        StringBuilder buf = new StringBuilder();
        int day = 0;
        int month = 0;
        int counter = 0;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) == '-') {
                if (counter == 0) {
                    buf.delete(0, buf.length());
                    counter++;
                } else if (counter == 1) {
                    month = Integer.parseInt(String.valueOf(buf));
                    buf.delete(0, buf.length());
                }
            } else {
                buf.append(date.charAt(i));
            }
        }
        day = Integer.parseInt(String.valueOf(buf));


        if (month == 1) {
            return day > 0 && day < 32;

        } else if (month == 2) {
            return day > 0 && day < 29;

        } else if (month == 3) {
            return day > 0 && day < 32;

        } else if (month == 4) {
            return day > 0 && day < 31;

        } else if (month == 5) {
            return day > 0 && day < 32;

        } else if (month == 6) {
            return day > 0 && day < 31;

        } else if (month == 7) {
            return day > 0 && day < 32;

        } else if (month == 8) {
            return day > 0 && day < 32;

        } else if (month == 9) {
            return day > 0 && day < 31;

        } else if (month == 10) {
            return day > 0 && day < 32;

        } else if (month == 11) {
            return day > 0 && day < 31;

        } else if (month == 12) {
            return day > 0 && day < 32;

        }
        return false;
    }
}
