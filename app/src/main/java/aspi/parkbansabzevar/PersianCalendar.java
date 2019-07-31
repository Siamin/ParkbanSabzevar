package aspi.parkbansabzevar;


import android.util.Log;

public class PersianCalendar {

    String TAG = "TAG_PersianCalendar";
    String[] weekDayNames = {
            "شنبه", "یکشنبه", "دوشنبه",
            "سه شنبه", "چهارشنبه",
            "پنج شنبه", "جمعه"
    };

    String[] monthNames = {
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    };


    public String MiladiToJalali(int MiladiYears, int MiladiMonth, int MiladiDay) {

        Log.i(TAG, MiladiYears+"/"+MiladiMonth+"/"+MiladiDay);
        int shamsiDay, shamsiMonth, shamsiYear;
        int dayCount, farvardinDayDiff, deyDayDiff;
        int sumDayMiladiMonth[] = {0,31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int sumDayMiladiMonthLeap[] = {0,31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
        farvardinDayDiff = 79;


        if (MiladiIsLeap(MiladiYears)) {
            dayCount = sumDayMiladiMonthLeap[MiladiMonth - 1] + MiladiDay;
        } else {
            dayCount = sumDayMiladiMonth[MiladiMonth - 1] + MiladiDay;
        }

        if ((MiladiIsLeap(MiladiYears - 1))) {
            deyDayDiff = 11;
        } else {
            deyDayDiff = 10;
        }

        if (dayCount > farvardinDayDiff) {
            dayCount = dayCount - farvardinDayDiff;
            if (dayCount <= 186) {
                switch (dayCount % 31) {
                    case 0:
                        shamsiMonth = dayCount / 31;
                        shamsiDay = 31;
                        break;
                    default:
                        shamsiMonth = (dayCount / 31) + 1;
                        shamsiDay = (dayCount % 31);
                        break;
                }
                shamsiYear = MiladiYears - 621;
            } else {
                dayCount = dayCount - 186;
                switch (dayCount % 30) {
                    case 0:
                        shamsiMonth = (dayCount / 30) + 6;
                        shamsiDay = 30;
                        break;
                    default:
                        shamsiMonth = (dayCount / 30) + 7;
                        shamsiDay = (dayCount % 30);
                        break;
                }
                shamsiYear = MiladiYears - 621;
            }
        } else {
            dayCount = dayCount + deyDayDiff;

            switch (dayCount % 30) {
                case 0:
                    shamsiMonth = (dayCount / 30) + 9;
                    shamsiDay = 30;
                    break;
                default:
                    shamsiMonth = (dayCount / 30) + 10;
                    shamsiDay = (dayCount % 30);
                    break;
            }
            shamsiYear = MiladiYears - 622;

        }

        Log.i(TAG, shamsiYear+"/"+(shamsiMonth)+"/"+(shamsiDay));
        return shamsiYear+"/"+(shamsiMonth)+"/"+(shamsiDay);
    }

    public String MiladiToJalaliByMonthNames(int MiladiYears, int MiladiMonth, int MiladiDay) {

        Log.i(TAG, MiladiYears+"/"+MiladiMonth+"/"+MiladiDay);
        int shamsiDay, shamsiMonth, shamsiYear;
        int dayCount, farvardinDayDiff, deyDayDiff;
        int sumDayMiladiMonth[] = {0,31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int sumDayMiladiMonthLeap[] = {0,31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
        farvardinDayDiff = 79;


        if (MiladiIsLeap(MiladiYears)) {
            dayCount = sumDayMiladiMonthLeap[MiladiMonth - 1] + MiladiDay;
        } else {
            dayCount = sumDayMiladiMonth[MiladiMonth - 1] + MiladiDay;
        }

        if ((MiladiIsLeap(MiladiYears - 1))) {
            deyDayDiff = 11;
        } else {
            deyDayDiff = 10;
        }

        if (dayCount > farvardinDayDiff) {
            dayCount = dayCount - farvardinDayDiff;
            if (dayCount <= 186) {
                switch (dayCount % 31) {
                    case 0:
                        shamsiMonth = dayCount / 31;
                        shamsiDay = 31;
                        break;
                    default:
                        shamsiMonth = (dayCount / 31) + 1;
                        shamsiDay = (dayCount % 31);
                        break;
                }
                shamsiYear = MiladiYears - 621;
            } else {
                dayCount = dayCount - 186;
                switch (dayCount % 30) {
                    case 0:
                        shamsiMonth = (dayCount / 30) + 6;
                        shamsiDay = 30;
                        break;
                    default:
                        shamsiMonth = (dayCount / 30) + 7;
                        shamsiDay = (dayCount % 30);
                        break;
                }
                shamsiYear = MiladiYears - 621;
            }
        } else {
            dayCount = dayCount + deyDayDiff;

            switch (dayCount % 30) {
                case 0:
                    shamsiMonth = (dayCount / 30) + 9;
                    shamsiDay = 30;
                    break;
                default:
                    shamsiMonth = (dayCount / 30) + 10;
                    shamsiDay = (dayCount % 30);
                    break;
            }
            shamsiYear = MiladiYears - 622;

        }

        Log.i(TAG, shamsiDay+"  "+monthNames[shamsiMonth-1]+"  "+shamsiYear);
        return shamsiDay+"  "+monthNames[shamsiMonth-1]+"  "+shamsiYear;
    }


    // the function check a miladiyear is leap or not.
    boolean MiladiIsLeap(int miladiYear) {
        if (((miladiYear % 100) != 0 && (miladiYear % 4) == 0) || ((miladiYear % 100) == 0 && (miladiYear % 400) == 0))
            return true;
        else
            return false;

    }


}
