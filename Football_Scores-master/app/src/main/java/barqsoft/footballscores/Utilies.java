package barqsoft.footballscores;

import android.content.Context;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies {
    public static final int SERIE_A = 401;
    public static final int PREMIER_LEGAUE = 398;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 399;
    public static final int BUNDESLIGA = 395;

    public static String getLeague(int league_num) {
        switch (league_num) {
            case SERIE_A:
                return "Seria A";
            case PREMIER_LEGAUE:
                return "Premier League";
            case CHAMPIONS_LEAGUE:
                return "UEFA Champions League";
            case PRIMERA_DIVISION:
                return "Primera Division";
            case BUNDESLIGA:
                return "Bundesliga";
            default:
                return "Not known League Please report";
        }
    }

    public static String getMatchDay(int match_day, int league_num) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return "Group Stages, Matchday : 6";
            } else if (match_day == 7 || match_day == 8) {
                return "First Knockout round";
            } else if (match_day == 9 || match_day == 10) {
                return "QuarterFinal";
            } else if (match_day == 11 || match_day == 12) {
                return "SemiFinal";
            } else {
                return "Final";
            }
        } else {
            return "Matchday : " + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        switch (teamname) {
            case "Arsenal London FC":
                return R.drawable.arsenal;
            case "Manchester United FC":
                return R.drawable.manchester_united;
            case "Swansea City":
                return R.drawable.swansea_city_afc;
            case "Leicester City":
                return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC":
                return R.drawable.everton_fc_logo1;
            case "West Ham United FC":
                return R.drawable.west_ham;
            case "Tottenham Hotspur FC":
                return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion":
                return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC":
                return R.drawable.sunderland;
            case "Stoke City FC":
                return R.drawable.stoke_city;
            default:
                return R.drawable.no_icon;
        }
    }

    public static final long SECONDS_IN_A_DAY = 86400000;

    // Returns a LONG type date
    public static long normalizeDate(long date) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(date);
        int julianDay = Time.getJulianDay(date, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    // Returns a STRING "2015-07-18"
    public static String getFragmentDate(int offSet) {
        Date fragmentdate = new Date(System.currentTimeMillis() + (offSet * SECONDS_IN_A_DAY));
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        return mformat.format(fragmentdate);
    }

    // Returns a Long value from current date
    public static long getLongDateWithOffset(int offSet) {
        return System.currentTimeMillis() + (offSet * SECONDS_IN_A_DAY);
    }

    public static String getDayName(Context context, long dateInMillis) {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.

        Time t = new Time();
        t.setToNow();

        int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
        int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);

        if (julianDay == currentJulianDay) {
            return context.getString(R.string.today);
        } else if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        } else if (julianDay == currentJulianDay - 1) {
            return context.getString(R.string.yesterday);
        } else {
            Time time = new Time();
            time.setToNow();
            // Otherwise, the format is just the day of the week (e.g "Wednesday".
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }
    }

    public static String getMatchDate(String dateString) {

        String matchDate = dateString.substring(0, dateString.indexOf("T"));
        String matchTime = dateString.substring(dateString.indexOf("T") + 1, dateString.indexOf("Z"));

        SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date parseddate = match_date.parse(matchDate + matchTime);
            SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
            new_date.setTimeZone(TimeZone.getDefault());
            matchDate = new_date.format(parseddate);
            matchDate = matchDate.substring(0, matchDate.indexOf(":"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return matchDate;
    }

    public static String getMatchTime(String dateString) {

        String matchTime  = dateString.substring(dateString.indexOf("T") + 1, dateString.indexOf("Z"));
        String matchDate = dateString.substring(0, dateString.indexOf("T"));
        SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date parseddate = match_date.parse(matchDate + matchTime);
            SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
            new_date.setTimeZone(TimeZone.getDefault());
            matchDate = new_date.format(parseddate);
            matchTime = matchDate.substring(matchDate.indexOf(":") + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return matchTime;
    }
}
